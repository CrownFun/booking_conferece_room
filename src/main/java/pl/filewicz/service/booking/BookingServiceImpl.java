package pl.filewicz.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filewicz.dto.BookingDto;
import pl.filewicz.exceptions.RoomAvailabilityException;
import pl.filewicz.exceptions.RoomNotFoundException;
import pl.filewicz.exceptions.UserNotFoundException;
import pl.filewicz.mapper.BookingMapper;
import pl.filewicz.mapper.DateMapper;
import pl.filewicz.model.Booking;
import pl.filewicz.model.Room;
import pl.filewicz.model.User;
import pl.filewicz.repository.BookingRepository;
import pl.filewicz.repository.RoomRepository;
import pl.filewicz.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto createNewBooking(BookingDto bookingDto) {

        Optional<User> user = userRepository.findByLogin(bookingDto.getUserLogin());
        Optional<Room> room = roomRepository.findByName(bookingDto.getRoomName());
        LocalDateTime startBooking = DateMapper.toLocalDateTime(bookingDto.getStartBooking());
        LocalDateTime endBooking = DateMapper.toLocalDateTime(bookingDto.getEndBooking());

        Booking booking = new Booking();

        user.ifPresentOrElse(booking::setUser, () -> {
            throw new UserNotFoundException(bookingDto.getUserLogin());
        });
        room.ifPresentOrElse(booking::setRoom, () -> {
            throw new RoomNotFoundException(bookingDto.getRoomName());
        });

        booking.setStart(startBooking);
        booking.setEnd(endBooking);

        if (checkRoomAvailability(booking)) {
            bookingRepository.save(booking);
            room.ifPresent(room1 -> room1.addBooking(booking));
        } else {
            throw new RoomAvailabilityException(booking.getRoom().getName());
        }
        return BookingMapper.toDto(booking);
    }

    private boolean checkRoomAvailability(Booking booking) {
        boolean isAvailable = true;
        List<Booking> bookingFound = findBookingByRoom(booking.getRoom());
        if (!bookingFound.isEmpty()) {
            for (Booking booking1 : bookingFound) {
                if (booking.getStart().isAfter(booking1.getStart()) && booking.getStart().isBefore(booking1.getEnd())) {
                    isAvailable = false;
                } else if (booking.getStart().isBefore(booking1.getStart()) && booking.getEnd().isAfter(booking1.getStart())) {
                    isAvailable = false;
                } else if (booking.getStart().isEqual(booking1.getStart()) && booking.getEnd().isEqual(booking1.getEnd())) {
                    isAvailable = false;
                }
            }
        }
        return isAvailable;
    }


    @Override
    public Optional<Booking> findBookingByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    private List<Booking> findBookingByRoom(Room room) {
        return bookingRepository.findByRoom(room);
    }

    @Override
    public List<BookingDto> gettingBookingSchduleGreatherThanOrEqual(LocalDateTime start) {
        return bookingRepository.findByStartGreaterThanEqual(start).stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> gettingBookingScheduleForAllRooms(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findByStartBetween(start, end).stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> gettingBookingScheduleForSingleRoom(String roomName, LocalDateTime start, LocalDateTime end) {

        Optional<Room> roomByName = roomRepository.findByName(roomName);
        List<Booking> bookings;

        if (roomByName.isPresent()) {
            bookings = bookingRepository.findByRoomAndStartBetween(roomByName.get(), start, end);
        } else {
            throw new RoomNotFoundException(roomName);
        }
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> gettingBookingSchduleForUser(String userLogin, LocalDateTime start, LocalDateTime end) {

        Optional<User> userByLogin = userRepository.findByLogin(userLogin);
        List<Booking> bookings;
        if (userByLogin.isPresent()) {
            bookings = bookingRepository.findByUserAndStartBetween(userByLogin.get(), start, end);
        } else {
            throw new UserNotFoundException(userLogin);
        }

        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }
}

package pl.filewicz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filewicz.api.BookingDto;
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

//delete booking ?

@Service
public class BookingController {

    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public BookingDto createNewBooking(BookingDto bookingDto) {

        Optional<User> user = userRepository.findByLogin(bookingDto.getUserLogin());
        Optional<Room> room = roomRepository.findByName(bookingDto.getRoomName());
        LocalDateTime startBooking = DateMapper.toLocalDateTime(bookingDto.getStartBooking());
        LocalDateTime endBooking = DateMapper.toLocalDateTime(bookingDto.getEndBooking());

        Booking booking = new Booking();

        user.ifPresentOrElse(booking::setUser, () -> {
            throw new UserNotFoundException();
        });
        room.ifPresentOrElse(booking::setRoom, () -> {
            throw new RoomNotFoundException();
        });

        booking.setStart(startBooking);
        booking.setEnd(endBooking);

        if (checkRoomAvailability(booking)) {
            bookingRepository.save(booking);
            room.ifPresent(room1 -> room1.addBooking(booking));
        } else {
            throw new RoomAvailabilityException();
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

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public Optional<Booking> findBookingByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    private List<Booking> findBookingByRoom(Room room) {
        return bookingRepository.findByRoom(room);
    }


    public List<BookingDto> gettingBookingSchduleGreatherThanOrEqual(LocalDateTime start) {
        return bookingRepository.findByStartGreaterThanEqual(start).stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    public List<BookingDto> gettingBookingScheduleForAllRooms(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findByStartBetween(start, end).stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    public List<BookingDto> gettingBookingScheduleForSingleRoom(String roomName, LocalDateTime start, LocalDateTime end) {

        Optional<Room> roomByName = roomRepository.findByName(roomName);
        List<Booking> bookings;

        if (roomByName.isPresent()) {
            bookings = bookingRepository.findByRoomAndStartBetween(roomByName.get(), start, end);
        } else {
            throw new RoomNotFoundException();
        }
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    public List<BookingDto> gettingBookingSchduleForUser(String userLogin, LocalDateTime start, LocalDateTime end) {

        Optional<User> userByLogin = userRepository.findByLogin(userLogin);
        List<Booking> bookings;
        if (userByLogin.isPresent()) {
            bookings = bookingRepository.findByUserAndStartBetween(userByLogin.get(), start, end);
        } else {
            throw new UserNotFoundException();
        }

        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

}

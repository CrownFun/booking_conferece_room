package pl.filewicz.service.booking;

import pl.filewicz.dto.BookingDto;
import pl.filewicz.model.Booking;
import pl.filewicz.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    BookingDto createNewBooking(BookingDto bookingDto);

    Optional<Booking> findBookingByUser(User user);

    List<BookingDto> gettingBookingSchduleGreatherThanOrEqual(LocalDateTime start);

    List<BookingDto> gettingBookingScheduleForAllRooms(LocalDateTime start, LocalDateTime end);

    List<BookingDto> gettingBookingScheduleForSingleRoom(String roomName, LocalDateTime start, LocalDateTime end);

    List<BookingDto> gettingBookingSchduleForUser(String userLogin, LocalDateTime start, LocalDateTime end);


}

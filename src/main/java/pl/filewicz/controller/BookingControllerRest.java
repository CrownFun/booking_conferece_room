package pl.filewicz.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filewicz.api.BookingDto;
import pl.filewicz.mapper.DateMapper;
import pl.filewicz.service.BookingController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingControllerRest {

    private final BookingController bookingController;

    @PostMapping
    public ResponseEntity<BookingDto> bookingRoom(@RequestBody BookingDto bookingDto) {

        BookingDto bookingSaved = bookingController.createNewBooking(bookingDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(bookingSaved.getRoomName())
                .toUri();
        return ResponseEntity.created(location).body(bookingSaved);
    }

    @GetMapping
    public List<BookingDto> getAllBookings(@RequestBody BookingDto bookingDto) {

        List<BookingDto> bookings = null;
        if (bookingDto.getEndBooking() == null) {
            bookings = bookingController.gettingBookingSchduleGreatherThanOrEqual(DateMapper.toLocalDateTime(bookingDto.getStartBooking()));
        } else {
            bookings = bookingController.gettingBookingScheduleForAllRooms(DateMapper.toLocalDateTime(bookingDto.getStartBooking()), DateMapper.toLocalDateTime(bookingDto.getEndBooking()));
        }
        return bookings;
    }

    @GetMapping("/room")
    public List<BookingDto> getBokingBySingleRoom(@RequestBody BookingDto bookingDto) {
        return bookingController.gettingBookingScheduleForSingleRoom(bookingDto.getRoomName(), DateMapper.toLocalDateTime(bookingDto.getStartBooking()), DateMapper.toLocalDateTime(bookingDto.getEndBooking()));
    }

    @GetMapping("/user")
    public List<BookingDto> getBookingByUser(@RequestBody BookingDto bookingDto) {
        return bookingController.gettingBookingSchduleForUser(bookingDto.getUserLogin(), DateMapper.toLocalDateTime(bookingDto.getStartBooking()), DateMapper.toLocalDateTime(bookingDto.getEndBooking()));
    }
}


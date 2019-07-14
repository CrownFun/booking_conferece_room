package pl.filewicz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filewicz.api.BookingDto;
import pl.filewicz.mapper.DateMapper;
import pl.filewicz.service.BookingController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/booking")

public class BookingControllerRest {

    private BookingController bookingController;

    @Autowired
    public BookingControllerRest(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDto> bookingRoom(@RequestBody BookingDto bookingDto) {

        BookingDto bookingSaved = bookingController.createNewBooking(bookingDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(bookingSaved.getRoomName())
                .toUri();
        return ResponseEntity.created(location).body(bookingSaved);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookingDto> getAllBookings(@RequestBody BookingDto bookingDto) {

        List<BookingDto> bookings = null;
        if (bookingDto.getEndBooking() == null) {
            bookings = bookingController.gettingBookingSchduleGreatherThanOrEqual(DateMapper.toLocalDateTime(bookingDto.getStartBooking()));
        } else {
            bookings = bookingController.gettingBookingScheduleForAllRooms(DateMapper.toLocalDateTime(bookingDto.getStartBooking()), DateMapper.toLocalDateTime(bookingDto.getEndBooking()));
        }
        return bookings;
    }

    @GetMapping(value = "/room", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookingDto> getBokingBySingleRoom(@RequestBody BookingDto bookingDto) {
        return bookingController.gettingBookingScheduleForSingleRoom(bookingDto.getRoomName(), DateMapper.toLocalDateTime(bookingDto.getStartBooking()), DateMapper.toLocalDateTime(bookingDto.getEndBooking()));
    }

    @GetMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookingDto> getBookingByUser(@RequestBody BookingDto bookingDto) {
        return bookingController.gettingBookingSchduleForUser(bookingDto.getUserLogin(), DateMapper.toLocalDateTime(bookingDto.getStartBooking()), DateMapper.toLocalDateTime(bookingDto.getEndBooking()));
    }

}


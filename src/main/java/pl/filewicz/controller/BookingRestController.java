package pl.filewicz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pl.filewicz.dto.BookingDto;

import java.util.List;

@Api(tags = "Booking Room Controller")
public interface BookingRestController {

    @ApiOperation(value = "add new booking", notes = "save new booking into database")
    @ApiResponses({
            @ApiResponse(code = 201, message = "booking added"),
            @ApiResponse(code = 500, message = "Internal Server Erorrr distatser"),
            @ApiResponse(code = 400, message = "Something went wrong")})
    ResponseEntity<BookingDto> bookingRoom(@RequestBody BookingDto bookingDto);

    @ApiOperation(value = "get all bookings", notes = "get all bookings")
    List<BookingDto> getAllBookings(@RequestBody BookingDto bookingDto);

    @ApiOperation(value = "get booking by single room", notes = "get booking by single room")
    List<BookingDto> getBokingBySingleRoom(@RequestBody BookingDto bookingDto);

    @ApiOperation(value = "get booking by user", notes = "get booking by user")
    List<BookingDto> getBookingByUser(@RequestBody BookingDto bookingDto);

}

package pl.filewicz.mapper;


import pl.filewicz.api.BookingDto;
import pl.filewicz.model.Booking;


public class BookingMapper {


    public static BookingDto toDto(Booking booking) {
        return new BookingDto(booking.getUser().getLogin(), booking.getUser().getName(), booking.getUser().getSurname(), booking.getRoom().getName(), booking.getStart().toString(), booking.getEnd().toString());
    }


}



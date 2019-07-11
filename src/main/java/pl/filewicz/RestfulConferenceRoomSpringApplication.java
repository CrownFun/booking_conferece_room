package pl.filewicz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.filewicz.api.BookingDto;
import pl.filewicz.mapper.DateMapper;
import pl.filewicz.model.User;
import pl.filewicz.service.BookingController;
import pl.filewicz.service.RoomController;
import pl.filewicz.service.UserController;
import pl.filewicz.model.Booking;

import java.util.Date;
import java.util.List;

// testy do resta
// szyfrowanie hasła
// refactoring, nazwy zmiennych, metod, klas
// private/public
// unusedimport

@SpringBootApplication
public class RestfulConferenceRoomSpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RestfulConferenceRoomSpringApplication.class, args);
        UserController userController = ctx.getBean(UserController.class);
        RoomController roomController = ctx.getBean(RoomController.class);
        BookingController bookingController = ctx.getBean(BookingController.class);

        BookingDto bookingDto = new BookingDto("jdoe", "Small", "2016-11-09 10:30", "2016-11-11 10:30");
        BookingDto bookingDto2 = new BookingDto("jdoe", "Medium", "2016-11-15 10:30", "2016-11-20 10:30");
        BookingDto bookingDto3 = new BookingDto("wblack", "Small", "2016-12-01 10:30", "2016-12-04 10:30");

        bookingController.createNewBooking(bookingDto);
        bookingController.createNewBooking(bookingDto2);
        bookingController.createNewBooking(bookingDto3);

        List<BookingDto> aaa = bookingController.gettingBookingScheduleForAllRooms(DateMapper.toLocalDateTime("2016-11-09 10:30"), DateMapper.toLocalDateTime("2017-11-09 10:30"));

        List<BookingDto> bbb = bookingController.gettingBookingScheduleForSingleRoom("Small", DateMapper.toLocalDateTime("2016-11-08 10:30"), DateMapper.toLocalDateTime("2016-12-05 10:30"));

        List<BookingDto> ccc = bookingController.gettingBookingSchduleForUser("jdoe", DateMapper.toLocalDateTime("2016-11-09 10:30"), DateMapper.toLocalDateTime("2017-11-09 10:30"));


        // otwarta data - dla ułatwienia tylko bez koncowej, - zaimplementowac!


//        BookingDto bookingDto4 = new BookingDto("jsmith", "Small", "2016-12-01 10:30", "2016-12-04 10:30");
//        bookingController.createNewBooking(bookingDto4);


    }
}

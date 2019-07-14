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
// store pass i secure way
// save start data in secure way!
@SpringBootApplication
public class RestfulConferenceRoomSpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RestfulConferenceRoomSpringApplication.class, args);
//        UserController userController = ctx.getBean(UserController.class);
//        RoomController roomController = ctx.getBean(RoomController.class);
//        BookingController bookingController = ctx.getBean(BookingController.class);



    }
}

package pl.filewicz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.filewicz.api.BookingDto;
import pl.filewicz.api.RoomDto;
import pl.filewicz.api.UserDto;
import pl.filewicz.mapper.DateMapper;
import pl.filewicz.model.Booking;
import pl.filewicz.model.Room;
import pl.filewicz.model.User;
import pl.filewicz.service.BookingController;
import pl.filewicz.service.RoomController;
import pl.filewicz.service.UserController;


import java.util.List;

import static org.junit.Assert.*;

// testowanie REST!!

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestfulConferenceRoomSpringApplicationTests {


    @Autowired
    private UserController userController;

    @Autowired
    private RoomController roomController;

    @Autowired
    private BookingController bookingController;

    @Test
    public void createNewUser() {
        User user2 = new User();
        user2.setName("John");
        user2.setSurname("Rambo");
        user2.setLogin("jrambo");
        user2.setPassword("strongPass2");

        userController.createNewUser(user2);

        User userFound = userController.getUser("jrambo").get();

        assertEquals(userFound.getSurname(), "Rambo");
    }

    @Test
    public void getAllUsers() {
        List<UserDto> users = userController.getAllUsers();
        System.out.println(users);
        assertTrue(users.size() > 1);

    }

    @Test
    public void updateUser() {
        User userBefore = userController.getUser("jdoe").get();
        userBefore.setName("Robert");
        userController.createNewUser(userBefore);
        User userAfter = userController.getUser("jdoe").get();
        assertEquals(userAfter.getName(), "Robert");
    }


    @Test
    public void deleteUserWithoutAnyRelations() {
        userController.deleteUser(userController.getUser("jrambo").get());
        User userFound = userController.getUser("jrambo").get();
        assertNull(userFound);
    }

    @Test
    public void createNewRoom() {

        final String roomName = "jedynka";

        Room room = new Room();
        room.setName(roomName);
        room.setLocation_description("pierwsze piętro, sala nr 5");
        room.setNumber_of_seats(34);
        room.setProjector(true);
        room.setPhone_number("222-222-333");
        roomController.createNewRoom(room);

        Room roomFound = roomController.getRoom(roomName).get();

        assertEquals(roomName, roomFound.getName());
    }

    @Test
    public void getAllRooms() {
        List<RoomDto> rooms = roomController.getRooms();
        assertTrue(rooms.size() > 1);
    }

    @Test
    public void updateRoom() {
        Room roomBefore = roomController.getRoom("Large").get();
        roomBefore.setPhone_number("999-999-999");
        roomController.createNewRoom(roomBefore);
        Room roomAfter = roomController.getRoom("Large").get();
        assertEquals(roomAfter.getPhone_number(), "999-999-999");
    }

    @Test
    public void deleteRoomWithOutAnyRelations() {
        roomController.deleteRoom(roomController.getRoom("jedynka").get());
        Room roomFound = roomController.getRoom("jedynka").get();
        assertNull(roomFound);
    }

    @Test
    public void bookingConferenceRoom() {

        BookingDto bookingDto = new BookingDto("jdoe", "Small", "2016-11-09 10:30", "2016-11-11 10:30");

        bookingController.createNewBooking(bookingDto);

        Booking bookingFound = bookingController.findBookingByUser(userController.getUser("jdoe").get()).get();

        assertEquals(bookingFound.getUser().getLogin(), "jdoe");
        assertEquals(bookingFound.getRoom().getName(), "Small");
    }

    @Test
    public void getListOfAllBookingsForAParticularTimeFrame() {

        BookingDto bookingDto = new BookingDto("jsmith", "Large", "2017-01-01 10:30", "2017-01-30 10:30");
        BookingDto bookingDto2 = new BookingDto("jsmith", "Small", "2017-02-01 10:30", "2017-02-28 10:30");
        bookingController.createNewBooking(bookingDto);
        bookingController.createNewBooking(bookingDto2);

        List<BookingDto> bookings = bookingController.gettingBookingScheduleForAllRooms(DateMapper.toLocalDateTime("2017-01-01 10:30"), DateMapper.toLocalDateTime("2017-02-28 10:30"));

        assertEquals(2, bookings.size());

    }

    @Test
    public void getListOfAllBookingsForSingleRoomAndParticularTimeFrame() {

        BookingDto bookingDto = new BookingDto("jsmith", "Medium", "2017-01-01 10:30", "2017-01-30 10:30");
        BookingDto bookingDto2 = new BookingDto("wblack", "Medium", "2017-02-01 10:30", "2017-02-28 10:30");
        BookingDto bookingDto3 = new BookingDto("wblack", "Small", "2017-02-01 10:30", "2017-02-28 10:30");


        bookingController.createNewBooking(bookingDto);
        bookingController.createNewBooking(bookingDto2);
        bookingController.createNewBooking(bookingDto3);

        List<BookingDto> bookings = bookingController.gettingBookingScheduleForSingleRoom("Medium", DateMapper.toLocalDateTime("2017-01-01 09:30"), DateMapper.toLocalDateTime("2017-02-28 11:30"));

        assertEquals(2, bookings.size());
        assertEquals("Medium", bookings.get(0).getRoomName());
    }

    @Test
    public void getListOfAllBookingsForUserAndParticularTimeFrame() {

        BookingDto bookingDto = new BookingDto("jsmith", "Medium", "2017-01-01 10:30", "2017-01-30 10:30");
        BookingDto bookingDto2 = new BookingDto("wblack", "Large", "2019-08-01 10:30", "2019-08-30 10:30");
        BookingDto bookingDto3 = new BookingDto("wblack", "Small", "2019-09-01 10:30", "2019-09-30 10:30");

        bookingController.createNewBooking(bookingDto);
        bookingController.createNewBooking(bookingDto2);
        bookingController.createNewBooking(bookingDto3);

        List<BookingDto> bookings = bookingController.gettingBookingSchduleForUser("wblack", DateMapper.toLocalDateTime("2019-08-01 10:30"), DateMapper.toLocalDateTime("2019-10-01 10:30"));

        assertEquals(2, bookings.size());
        assertEquals("wblack", bookings.get(0).getUserLogin());
    }


}

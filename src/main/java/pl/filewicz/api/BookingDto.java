package pl.filewicz.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto {

    private String userLogin;
    private String userName;
    private String userSurname;
    private String roomName;
    private String startBooking;
    private String endBooking;

    public BookingDto(String userLogin, String roomName, String startBooking, String endBooking) {
        this.userLogin = userLogin;
        this.roomName = roomName;
        this.startBooking = startBooking;
        this.endBooking = endBooking;
    }

    public BookingDto(String userLogin, String userName, String userSurname, String roomName, String startBooking, String endBooking) {
        this.userLogin = userLogin;
        this.userName = userName;
        this.userSurname = userSurname;
        this.roomName = roomName;
        this.startBooking = startBooking;
        this.endBooking = endBooking;
    }
}

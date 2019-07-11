package pl.filewicz.api;

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

    public BookingDto() {
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStartBooking() {
        return startBooking;
    }

    public void setStartBooking(String startBooking) {
        this.startBooking = startBooking;
    }

    public String getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(String endBooking) {
        this.endBooking = endBooking;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}

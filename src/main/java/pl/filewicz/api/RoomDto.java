package pl.filewicz.api;

public class RoomDto {

    private String name;
    private String location_description;
    private int number_of_seats;
    private boolean projector;
    private String phone_number;


    public RoomDto(String name, String location_description, int number_of_seats, boolean projector, String phone_number) {
        this.name = name;
        this.location_description = location_description;
        this.number_of_seats = number_of_seats;
        this.projector = projector;
        this.phone_number = phone_number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation_description() {
        return location_description;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    public int getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(int number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}

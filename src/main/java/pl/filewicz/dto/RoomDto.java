package pl.filewicz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}

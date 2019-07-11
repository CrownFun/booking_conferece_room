package pl.filewicz.mapper;

import pl.filewicz.api.RoomDto;
import pl.filewicz.model.Room;

public class RoomMapper {

    public static RoomDto toDto(Room room) {
        return new RoomDto(room.getName(), room.getLocation_description(), room.getNumber_of_seats(), room.isProjector(), room.getPhone_number());
    }
}

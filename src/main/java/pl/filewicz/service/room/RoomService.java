package pl.filewicz.service.room;

import pl.filewicz.dto.RoomDto;
import pl.filewicz.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomDto createNewRoom(Room room);

    List<RoomDto> getRooms();

    Optional<Room> getRoom(String name);

    void deleteRoom(String name, Room room);

    void updateRoom(String roomName, Room room);

}

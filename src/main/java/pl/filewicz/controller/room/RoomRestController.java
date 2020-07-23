package pl.filewicz.controller.room;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.filewicz.dto.RoomDto;
import pl.filewicz.model.Room;

import java.util.List;

@Api(tags = "Room Controller")
public interface RoomRestController {

    @ApiOperation(value = "add new room", notes = "save new room into database")
    ResponseEntity<RoomDto> saveRoom(@RequestBody Room room);

    @ApiOperation(value = "get all rooms", notes = "get all rooms")
    List<RoomDto> getRooms();

    @ApiOperation(value = "get room by name", notes = "get room by name")
    ResponseEntity<RoomDto> getRoomByName(@PathVariable String name);

    @ApiOperation(value = "delete room", notes = "get room by name")
    void deleteRoom(@PathVariable String name, @RequestBody Room room);

    @ApiOperation(value = "update room", notes = "update room")
    void updateRoom(@PathVariable String name, @RequestBody Room room);
}

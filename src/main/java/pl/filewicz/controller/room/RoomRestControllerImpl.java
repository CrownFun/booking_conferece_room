package pl.filewicz.controller.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filewicz.dto.RoomDto;
import pl.filewicz.exceptions.RoomNotFoundException;
import pl.filewicz.mapper.RoomMapper;
import pl.filewicz.model.Room;
import pl.filewicz.response.CustomResponse;
import pl.filewicz.service.room.RoomServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomRestControllerImpl implements RoomRestController{

    private final RoomServiceImpl roomController;

    @PostMapping
    public ResponseEntity<RoomDto> saveRoom(@RequestBody Room room) {

        RoomDto roomSaved = roomController.createNewRoom(room);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(roomSaved.getName())
                .toUri();
        return ResponseEntity.created(location).body(roomSaved);
    }

    //localhost:8080/api/rooms
    @GetMapping
    public List<RoomDto> getRooms() {
        return roomController.getRooms();
    }

    //    localhost:8080/api/rooms/Large room
    @GetMapping("/{name}")
    public ResponseEntity<RoomDto> getRoomByName(@PathVariable String name) {
        Optional<Room> roomFound = roomController.getRoom(name.trim());
        return roomFound.map(room -> ResponseEntity.ok(RoomMapper.toDto(room)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
                .orElseThrow(() -> {
                    throw new RoomNotFoundException();
                });
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<CustomResponse>  deleteRoom(@PathVariable String name, @RequestBody Room room) {
        String roomName = name + " Room";
        roomController.deleteRoom(roomName, room);
        return new ResponseEntity<>(new CustomResponse("Room " + name + " successfully deleted"), HttpStatus.OK);
    }

    //zmienic na return, beez wyjątku
    @PutMapping("/{name}")
    public ResponseEntity<CustomResponse>  updateRoom(@PathVariable String name, @RequestBody Room room) {
        String roomName = name + " Room";
        if (!roomName.equals(room.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The updated object must have a name that matches the name in the resource path");
        }
        roomController.updateRoom(roomName, room);
        return new ResponseEntity<>(new CustomResponse("Room  " + name + "successfully updated"), HttpStatus.OK);
    }
}

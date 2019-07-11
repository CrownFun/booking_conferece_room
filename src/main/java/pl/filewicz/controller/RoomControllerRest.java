package pl.filewicz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filewicz.api.RoomDto;
import pl.filewicz.model.Room;
import pl.filewicz.service.RoomController;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/rooms")
public class RoomControllerRest {

    private RoomController roomController;

    @Autowired
    public RoomControllerRest(RoomController roomController) {
        this.roomController = roomController;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDto> saveRoom(@RequestBody Room room) {

        RoomDto roomSaved = roomController.createNewRoom(room);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(roomSaved.getName())
                .toUri();
        return ResponseEntity.created(location).body(roomSaved);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoomDto> getRooms() {
        return roomController.getRooms();
    }

    @DeleteMapping("/{name}")
    public void deleteRoom(@PathVariable String name) {

        roomController.getRoom(name).ifPresent(room -> roomController.deleteRoom(room));
    }

    @PutMapping("/{name}")
    public void updateRoom(@PathVariable String name, @RequestBody Room room) {

        if (name.equals(room.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiket musi mieć nazwę zgodną z nazwą w ścieżce zasobu");
        }

        roomController.updateRoom(name, room);
    }

}

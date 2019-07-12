package pl.filewicz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filewicz.api.RoomDto;
import pl.filewicz.exceptions.CreateFormFormatException;
import pl.filewicz.exceptions.DuplicateRoomException;
import pl.filewicz.mapper.RoomMapper;
import pl.filewicz.model.Room;
import pl.filewicz.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoomController {

    private RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomDto createNewRoom(Room room) {

        Optional<Room> roomByName = roomRepository.findByName(room.getName());
        roomByName.ifPresent(room1 -> {
            throw new DuplicateRoomException();
        });

        try {
            roomRepository.save(room);
        } catch (Exception e) {
            throw new CreateFormFormatException();
        }

        return RoomMapper.toDto(room);
    }

    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(RoomMapper::toDto).collect(Collectors.toList());
    }

    public Optional<Room> getRoom(String name) {
        return roomRepository.findByName(name);
    }

    public void deleteRoom(Room room) {
        roomRepository.deleteById(room.getId());
    }

    public void updateRoom(String roomName, Room room) {
        Optional<Room> roomByName = roomRepository.findByName(roomName);
        roomByName.ifPresent(room1 -> {
            room1.setName(room.getName());
            room1.setLocation_description(room.getLocation_description());
            room1.setProjector(room.isProjector());
            room1.setNumber_of_seats(room.getNumber_of_seats());
            room1.setPhone_number(room.getPhone_number());
            roomRepository.save(room1);
        });

    }

}

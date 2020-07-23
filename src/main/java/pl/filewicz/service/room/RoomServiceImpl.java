package pl.filewicz.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.filewicz.dto.RoomDto;
import pl.filewicz.exceptions.AdministratorSecurityException;
import pl.filewicz.exceptions.CreateFormFormatException;
import pl.filewicz.exceptions.DuplicateRoomException;
import pl.filewicz.exceptions.RoomNotFoundException;
import pl.filewicz.mapper.RoomMapper;
import pl.filewicz.model.Room;
import pl.filewicz.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final Environment environment;

    @Override
    public RoomDto createNewRoom(Room room) {
        checkAdminPassword(room);
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

    @Override
    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(RoomMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<Room> getRoom(String name) {
        return roomRepository.findByName(name);
    }

    @Override
    public void deleteRoom(String name, Room room) {
        checkAdminPassword(room);
        Optional<Room> roomByName = roomRepository.findByName(name);
        roomByName.ifPresent(room1 -> roomRepository.deleteById(room1.getId()));
    }

    @Override
    public void updateRoom(String roomName, Room room) {
        Optional<Room> roomByName = roomRepository.findByName(roomName);
        roomByName.ifPresentOrElse(room1 -> {
            room1.setName(room.getName());
            room1.setLocation_description(room.getLocation_description());
            room1.setProjector(room.isProjector());
            room1.setNumber_of_seats(room.getNumber_of_seats());
            room1.setPhone_number(room.getPhone_number());
            roomRepository.save(room1);
        }, () -> {
            throw new RoomNotFoundException();
        });
    }

    private boolean adminPasswordValidate(Room room) {
        boolean isValid = false;
        String adminPass = environment.getProperty("messageFileProperty");
        if (room.getAdminPassword() == null || !room.getAdminPassword().equals(adminPass)) {
            isValid = true;
        }
        return isValid;
    }

    private void checkAdminPassword(Room room) {
        if (adminPasswordValidate(room)) {
            throw new AdministratorSecurityException();
        }
    }
}

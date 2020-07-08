package pl.filewicz.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.filewicz.model.Room;
import pl.filewicz.repository.RoomRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class RoomCreator {

    private final RoomRepository roomRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        roomRepository.save(new Room("Large Room", "1st floor", 10, true, "22-22-22-22"));
        roomRepository.save(new Room("Medium Room", "1st floor", 6, true, null));
        roomRepository.save(new Room("Small Room", "2nd floor", 4, false, null));
    }
}

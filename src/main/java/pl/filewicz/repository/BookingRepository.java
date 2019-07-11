package pl.filewicz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filewicz.model.Booking;
import pl.filewicz.model.Room;
import pl.filewicz.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByUser(User user);

    List<Booking> findByRoom(Room room);

    List<Booking> findByStartGreaterThanEqual(LocalDateTime start);

    List<Booking> findByStartBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByRoomAndStartBetween(Room room, LocalDateTime start, LocalDateTime end);

    List<Booking> findByUserAndStartBetween(User user, LocalDateTime start, LocalDateTime end);

}

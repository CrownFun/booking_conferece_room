package pl.filewicz.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "id_room", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    public Booking(User user, Room room, LocalDateTime start, LocalDateTime end) {
        this.user = user;
        this.room = room;
        this.start = start;
        this.end = end;
    }

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", start_date=" + start +
                ", end_date=" + end +
                " " + user.getLogin() + " " + room.getName();
    }
}

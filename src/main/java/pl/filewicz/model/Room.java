package pl.filewicz.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @Column(length = 256)
    private String location_description;
    @Column(length = 100, nullable = false)
    private int number_of_seats;
    private boolean projector;
    @Column(length = 100)
    private String phone_number;
    @Transient
    private String adminPassword;
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Booking> bookings;

    public Room(String name, String location_description, int number_of_seats, boolean projector, String phone_number) {
        this.name = name;
        this.location_description = location_description;
        this.number_of_seats = number_of_seats;
        this.projector = projector;
        this.phone_number = phone_number;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}

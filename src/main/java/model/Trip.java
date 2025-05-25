package model;

import jakarta.persistence.*;
import model.Enums.TravelType;
import model.Tickets.Ticket;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String origin;


    private String destination;


    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    private int availableSeats;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    public Trip() {
    }

    public Trip(String origin, String destination, LocalDateTime departureTime, TravelType travelType, int availableSeats) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.travelType = travelType;
        this.availableSeats = availableSeats;
    }


    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getId() {
        return id;
    }
}

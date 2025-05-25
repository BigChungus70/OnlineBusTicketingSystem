package model.Tickets;

import jakarta.persistence.*;
import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Trip;
import model.User;

import java.time.LocalDateTime;


@Entity
@Table(name = "tickets")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ticket_class", discriminatorType = DiscriminatorType.STRING)
abstract public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Trip trip;


    @Enumerated(EnumType.STRING)
    private TicketType ticketType;


    @Enumerated(EnumType.STRING)
    private TravelType travelType;


    private double price;

    private LocalDateTime purchaseDate;

    public Ticket() {
    }

    public Ticket(User user, Trip trip, double price, LocalDateTime purchaseDate, TicketType ticketType, TravelType travelType) {
        this.user = user;
        this.trip = trip;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.ticketType = ticketType;
        this.travelType = travelType;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }



}

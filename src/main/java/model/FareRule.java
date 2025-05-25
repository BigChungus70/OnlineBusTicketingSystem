package model;

import jakarta.persistence.*;
import model.Enums.TicketType;
import model.Enums.TravelType;

@Entity
public class FareRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Column
    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    @Column
    private double baseFare;

    public TicketType getTicketType() {
        return ticketType;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public int getId() {
        return id;
    }
    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }
    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

}

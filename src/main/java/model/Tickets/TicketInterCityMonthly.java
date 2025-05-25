package model.Tickets;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Trip;
import model.User;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("INTERCITY_Monthly")
public class TicketInterCityMonthly extends Ticket {

    public TicketInterCityMonthly() {
        super();
    }

    public TicketInterCityMonthly(User user, Trip trip, double price, LocalDateTime purchaseDate,
                                TicketType ticketType, TravelType travelType) {
        super(user, trip, price, purchaseDate, ticketType, travelType);

    }
}
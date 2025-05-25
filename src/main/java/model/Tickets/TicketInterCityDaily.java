package model.Tickets;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Trip;
import model.User;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("INTERCITY_DAILY")
public class TicketInterCityDaily extends Ticket {

    public TicketInterCityDaily() {
        super();
    }

    public TicketInterCityDaily(User user, Trip trip, double price, LocalDateTime purchaseDate,
                           TicketType ticketType, TravelType travelType) {
        super(user, trip, price, purchaseDate, ticketType, travelType);

    }
}
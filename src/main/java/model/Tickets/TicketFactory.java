package model.Tickets;

import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Trip;
import model.User;
import java.time.LocalDateTime;

public class TicketFactory {

    public static Ticket createTicket(User user, Trip trip, double price,LocalDateTime purchaseDate, TicketType ticketType, TravelType travelType) {

        if (travelType == TravelType.City) {
            switch (ticketType) {
                case OneTrip:
                    return new TicketCityOne(user, trip, price, purchaseDate, ticketType, travelType);
                case Daily:
                    return new TicketCityDaily(user, trip, price, purchaseDate, ticketType, travelType);
                case Weekly:
                    return new TicketCityWeekly(user, trip, price, purchaseDate, ticketType, travelType);
                case Monthly:
                    return new TicketCityMonthly(user, trip, price, purchaseDate, ticketType, travelType);
                default:
                    throw new IllegalArgumentException("Invalid ticket type: " + ticketType);
            }
        } else if (travelType == TravelType.InterCity) {
            switch (ticketType) {
                case OneTrip:
                    return new TicketInterCityOne(user, trip, price, purchaseDate, ticketType, travelType);
                case Daily:
                    return new TicketInterCityDaily(user, trip, price, purchaseDate, ticketType, travelType);
                case Weekly:
                    return new TicketInterCityWeekly(user, trip, price, purchaseDate, ticketType, travelType);
                case Monthly:
                    return new TicketInterCityMonthly(user, trip, price, purchaseDate, ticketType, travelType);
                default:
                    throw new IllegalArgumentException("Invalid ticket type: " + ticketType);
            }
        } else {
            throw new IllegalArgumentException("Invalid travel type: " + travelType);
        }
    }
}
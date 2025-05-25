package service;

import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Tickets.Ticket;
import model.User;
import service.FareStrategy.FareCalculator;
import service.FareStrategy.StrategyRegistry;

import java.time.LocalDateTime;

public class FareService {

    StrategyRegistry strategyRegistry;
    public FareService() {
        strategyRegistry = new StrategyRegistry();
    }

    public double estimateFare(User user, String sTicketType, String sTravelType, LocalDateTime time) {

        TicketType ticketType = parseTicketType(sTicketType);
        TravelType travelType = parseTravelType(sTravelType);

        FareCalculator calculator = strategyRegistry.getCalculator(travelType, ticketType);
        return calculator.calculateFare(user, time);
    }

    public double estimateFareTicket(Ticket ticket, User user, LocalDateTime time) {
        FareCalculator calculator = strategyRegistry.getCalculator(ticket.getTrip().getTravelType(), ticket.getTicketType());
        return calculator.calculateFare(user, time);
    }


    private TicketType parseTicketType(String ticketType) {
        try {
            return TicketType.valueOf(ticketType);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private TravelType parseTravelType(String travelType) {
        try {
            return TravelType.valueOf(travelType);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
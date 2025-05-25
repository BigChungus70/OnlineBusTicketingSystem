package service.FareStrategy;

import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Tickets.Ticket;
import model.User;
import repository.FareConfig;

import java.time.LocalDateTime;

public class CityDaily implements FareCalculator {
    @Override
    public double calculateFare(User user, LocalDateTime time) {
        FareConfig fareConfig = FareConfig.getInstance();

        double baseFare = fareConfig.getBaseFare(TravelType.City, TicketType.Daily);

        double discount = 0.0;

        discount += fareConfig.getDiscountRate(user.getCategory());

        discount = Math.min(discount, 0.50);

        double finalFare = baseFare * (1 - discount);

        return finalFare;
    }
}

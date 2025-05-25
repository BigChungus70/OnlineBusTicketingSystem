package service.FareStrategy;

import model.Enums.TicketType;
import model.Enums.TravelType;

import java.util.HashMap;
import java.util.Map;

public class StrategyRegistry {
    private final Map<TravelType, Map<TicketType, FareCalculator>> strategies = new HashMap<>();

    public StrategyRegistry() {
        Map<TicketType, FareCalculator> cityStrategies = new HashMap<>();
        cityStrategies.put(TicketType.OneTrip, new CityOneTrip());
        cityStrategies.put(TicketType.Daily, new CityDaily());
        cityStrategies.put(TicketType.Weekly, new CityWeekly());
        cityStrategies.put(TicketType.Monthly, new CityMonthly());

        Map<TicketType, FareCalculator> interCityStrategies = new HashMap<>();
        interCityStrategies.put(TicketType.OneTrip, new InterCityOneTrip());
        interCityStrategies.put(TicketType.Daily, new InterCityDaily());
        interCityStrategies.put(TicketType.Weekly, new InterCityWeekly());
        interCityStrategies.put(TicketType.Monthly, new InterCityMonthly());

        strategies.put(TravelType.City, cityStrategies);
        strategies.put(TravelType.InterCity, interCityStrategies);
    }

    public FareCalculator getCalculator(TravelType travelType, TicketType ticketType) {
        Map<TicketType, FareCalculator> typeStrategies = strategies.get(travelType);
        if (typeStrategies == null) {
            throw new IllegalArgumentException("Invalid travel type: " + travelType);
        }
        FareCalculator calculator = typeStrategies.get(ticketType);
        if (calculator == null) {
            throw new IllegalArgumentException("Invalid ticket type: " + ticketType);
        }
        return calculator;
    }
}

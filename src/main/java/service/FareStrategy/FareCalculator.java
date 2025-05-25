package service.FareStrategy;

import model.User;

import java.time.LocalDateTime;

public interface FareCalculator {
    double calculateFare(User user, LocalDateTime time);
}

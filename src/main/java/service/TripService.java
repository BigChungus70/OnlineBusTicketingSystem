package service;

import model.Enums.TravelType;
import model.Trip;
import repository.TripDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TripService {

    private final TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public List<Trip> searchTrips(String origin, String destination, TravelType travelType, LocalDate date) {
        return tripDAO.findTrips(origin, destination, travelType, date);
    }
    public List<Trip> getAllTrips() {
        return tripDAO.getAllTrips();
    }
    public Trip getTripById(int id) {
        return tripDAO.findTripById(id);
    }
    public void bookTrip(int id) {
        tripDAO.bookSeat(id);
    }
    public void cancelTrip(int id) {
        tripDAO.cancelSeat(id);
    }
    public void removeTrip(int id) {
        tripDAO.removeTrip(id);
    }
    public void addTrip(String origin, String destination, LocalDateTime departureTime, TravelType travelType, int availableSeats) {
        Trip trip = new Trip(origin, destination, departureTime, travelType, availableSeats);
        tripDAO.registerTrip(trip);
    }
    public void editTrip(int tripId, String origin, String destination, LocalDateTime departure, TravelType travelType, int availableSeats){
        tripDAO.updateTrip(tripId, origin, destination, departure, travelType, availableSeats);
    }
}

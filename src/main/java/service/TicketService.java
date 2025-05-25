package service;

import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Tickets.Ticket;
import model.Tickets.TicketFactory;
import model.Trip;
import model.User;
import repository.TicketDAO;
import repository.TripDAO;

import java.time.LocalDateTime;
import java.util.List;

public class TicketService {

    private final TripService tripService;
    private final TicketDAO ticketDAO;
    private final FareService fareService;
    private final TicketFactory ticketFactory;

    public TicketService(TripDAO tripDAO, TicketDAO ticketDAO) {
        this.tripService = new TripService(tripDAO);
        this.ticketDAO = ticketDAO;
        this.ticketFactory = new TicketFactory();
        this.fareService = new FareService();
    }

    public void purchaseTicket(User user, String ticketTypeStr, int tripId, LocalDateTime time) {
        try {
            Trip trip = tripService.getTripById(tripId);
            double price = fareService.estimateFare(user, ticketTypeStr, trip.getTravelType().toString(), time);

            Ticket ticket = ticketFactory.createTicket(user, trip, price, time, parseTicketType(ticketTypeStr), trip.getTravelType());
            tripService.bookTrip(tripId);
            ticketDAO.persistTicket(ticket);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void deleteTicket(int ticketId) {
        Ticket ticket = ticketDAO.findTicketById(ticketId);
        tripService.cancelTrip(ticket.getTrip().getId());
        ticketDAO.removeTicket(ticket);
    }

    public List<Ticket> getAllUserTickets(int userId) {
        return ticketDAO.findAllUserTickets(userId);
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.findAllTickets();
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

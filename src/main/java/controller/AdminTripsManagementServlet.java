package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Enums.TravelType;
import model.Enums.UserRole;
import model.Trip;
import model.User;
import repository.TripDAO;
import service.TripService;
import utility.SessionExpiredException;
import utility.SessionManager;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AdminTripsManagementServlet extends HttpServlet {
    private TripService tripService;

    @Override
    public void init() {
        tripService = new TripService(new TripDAO());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = SessionManager.getLoggedInUserOrThrow(request);
            if (UserRole.Admin != user.getRole()) {
                throw new AuthenticationException();
            }
            if ("edit".equalsIgnoreCase(request.getParameter("action"))) {
                int tripId = Integer.parseInt(request.getParameter("tripId"));
                String origin = request.getParameter("origin");
                String destination = request.getParameter("destination");
                LocalDateTime departure = LocalDateTime.parse(request.getParameter("departure"));
                TravelType travelType = TravelType.valueOf(request.getParameter("travelType"));
                int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));
                tripService.editTrip(tripId, origin, destination, departure, travelType, availableSeats);
            }
            if ("remove".equals(request.getParameter("action"))) {
                tripService.removeTrip(Integer.parseInt(request.getParameter("tripId")));
            }
            if ("register".equals(request.getParameter("action"))) {
                String origin = request.getParameter("origin");
                String destination = request.getParameter("destination");
                LocalDateTime departure = LocalDateTime.parse(request.getParameter("departure"));
                TravelType travelType = TravelType.valueOf(request.getParameter("travelType"));
                int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));
                if (origin == null || destination == null || availableSeats == 0) {
                    throw new NullPointerException();
                }
                tripService.addTrip(origin, destination, departure, travelType, availableSeats);
            }
            List<Trip> trips = tripService.getAllTrips();
            request.setAttribute("trips", trips);
            request.getRequestDispatcher("adminTripsManagement.jsp").forward(request, response);
        } catch (SessionExpiredException e) {
            request.setAttribute("errorMessage", "Session expired");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (AuthenticationException e) {
            request.setAttribute("errorMessage", "You are not Authorized to access this resource");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getCause() + e.getMessage());
            request.getRequestDispatcher("adminTripsManagement.jsp").forward(request, response);
        }
    }
}

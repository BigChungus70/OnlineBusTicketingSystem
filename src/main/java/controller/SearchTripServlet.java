package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Enums.TravelType;
import model.Trip;
import model.User;
import repository.TripDAO;
import service.TripService;
import utility.SessionExpiredException;
import utility.SessionManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/searchTrips")
public class SearchTripServlet extends HttpServlet {

    private TripService tripService;

    @Override
    public void init() throws ServletException {
        tripService = new TripService(new TripDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            User user = SessionManager.getLoggedInUserOrThrow(req);

            String operation = req.getParameter("operation");

            List<Trip> allTrips = tripService.getAllTrips();
            req.setAttribute("trips", allTrips);

            if ("search".equalsIgnoreCase(operation)) {
                try {
                    String origin = req.getParameter("origin");
                    String destination = req.getParameter("destination");
                    String travelTypeStr = req.getParameter("travelType");
                    String dateStr = req.getParameter("date");

                    if (origin == null || destination == null || travelTypeStr == null || dateStr == null) {
                        req.setAttribute("errorMessage", "All search fields are required");
                        req.getRequestDispatcher("searchTrips.jsp").forward(req, resp);
                        return;
                    }

                    TravelType travelType = TravelType.valueOf(travelTypeStr);
                    LocalDate travelDate = LocalDate.parse(dateStr);

                    List<Trip> searchResults = tripService.searchTrips(origin, destination, travelType, travelDate);
                    req.setAttribute("searchResults", searchResults);

                } catch (Exception e) {
                    req.setAttribute("errorMessage", "Error performing search: " + e.getMessage());
                }
            }

            String estimatedFare = req.getParameter("estimatedFare");
            String estimatedTripId = req.getParameter("estimatedTripId");
            String errorMessage = req.getParameter("error");

            if (estimatedFare != null && estimatedTripId != null) {
                req.setAttribute("estimatedFare", estimatedFare);
                req.setAttribute("estimatedTripId", estimatedTripId);
            }

            if (errorMessage != null) {
                req.setAttribute("errorMessage", errorMessage);
            }

            req.setAttribute("loggedInUser", user);

            req.getRequestDispatcher("searchTrips.jsp").forward(req, resp);

        } catch (SessionExpiredException e) {

            req.setAttribute("errorMessage","Session expired");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }
}
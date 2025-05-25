package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.FareService;
import utility.SessionExpiredException;
import utility.SessionManager;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/estimateFare")
public class FareEstimationServlet extends HttpServlet {

    private FareService fareService;

    @Override
    public void init() {
        this.fareService = new FareService();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            User user = SessionManager.getLoggedInUserOrThrow(request);

            String ticketType = request.getParameter("ticketType");
            String travelType = request.getParameter("travelType");
            String tripId = request.getParameter("tripId");

            String origin = request.getParameter("origin");
            String destination = request.getParameter("destination");
            String searchTravelType = request.getParameter("searchTravelType");
            String date = request.getParameter("date");

            try {

                double fare = fareService.estimateFare(user, ticketType, travelType, LocalDateTime.now());

                String redirectUrl = "searchTrips?operation=search" +
                        "&origin=" + origin +
                        "&destination=" + destination +
                        "&travelType=" + searchTravelType +
                        "&date=" + date +
                        "&estimatedFare=" + String.format("%.2f", fare) +
                        "&estimatedTripId=" + tripId;

                response.sendRedirect(redirectUrl);

            } catch (Exception e) {
                String redirectUrl = "searchTrips?operation=search" +
                        "&origin=" + origin +
                        "&destination=" + destination +
                        "&travelType=" + searchTravelType +
                        "&date=" + date +
                        "&error=" + e.getMessage();

                response.sendRedirect(redirectUrl);
            }

        } catch (SessionExpiredException e) {
            request.setAttribute("errorMessage", "Session expired");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
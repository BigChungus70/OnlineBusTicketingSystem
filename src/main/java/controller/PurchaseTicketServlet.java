package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import org.hibernate.Session;
import repository.TicketDAO;
import repository.TripDAO;
import service.TicketService;
import utility.SessionExpiredException;
import utility.SessionManager;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/purchase")
public class PurchaseTicketServlet extends HttpServlet {
    private TicketService ticketService;

    @Override
    public void init() {
        ticketService = new TicketService(new TripDAO(),new TicketDAO());
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            User user = SessionManager.getLoggedInUserOrThrow(request);

            String tripIdStr = request.getParameter("tripId");
            String ticketTypeStr = request.getParameter("ticketType");

            if (tripIdStr == null || ticketTypeStr == null) {
                request.setAttribute("errorMessage", "Missing required parameters");
                request.getRequestDispatcher("purchaseTicket.jsp").forward(request, response);
                return;
            }

            int tripId = Integer.parseInt(tripIdStr);

            ticketService.purchaseTicket(user, ticketTypeStr, tripId, LocalDateTime.now());

            request.setAttribute("message", "Successfully purchased!");
            request.getRequestDispatcher("purchaseTicket.jsp").forward(request, response);

        } catch (SessionExpiredException e) {
            request.setAttribute("errorMessage","Session expired");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Purchase failed: " + e.getMessage());
            request.getRequestDispatcher("purchaseTicket.jsp").forward(request, response);
        }
    }
}

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tickets.Ticket;
import repository.TicketDAO;
import repository.TripDAO;
import service.TicketService;
import utility.SessionExpiredException;
import utility.SessionManager;

import java.io.IOException;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private TicketService ticketService;

    @Override
    public void init() {
        ticketService = new TicketService(new TripDAO(), new TicketDAO());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if ("report".equals(request.getParameter("action"))) {
                List<Ticket> tickets = ticketService.getAllTickets();
                request.setAttribute("tickets", tickets);
                request.getRequestDispatcher("adminReport.jsp").forward(request, response);
                return;
            }

            int userId = SessionManager.getLoggedInUserOrThrow(request).getId();
            if (request.getParameter("ticketId") != null) {
                int ticketId = Integer.parseInt(request.getParameter("ticketId"));
                ticketService.deleteTicket(ticketId);
                List<Ticket> userTickets = ticketService.getAllUserTickets(userId);
                request.setAttribute("userTickets", userTickets);
                request.getRequestDispatcher("ticketHistory.jsp").forward(request, response);
            } else {
                List<Ticket> userTickets = ticketService.getAllUserTickets(userId);
                request.setAttribute("userTickets", userTickets);
                request.getRequestDispatcher("ticketHistory.jsp").forward(request, response);
            }
        } catch (SessionExpiredException e) {
            request.setAttribute("errorMessage", "Session expired");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Enums.DiscountCategory;
import model.Enums.TicketType;
import model.Enums.TravelType;
import model.Enums.UserRole;
import model.User;
import repository.FareConfig;
import utility.SessionExpiredException;
import utility.SessionManager;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Map;


public class AdminRulesManagementServlet extends HttpServlet {
    private FareConfig fareConfig;

    @Override
    public void init() {
        fareConfig = FareConfig.getInstance();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = SessionManager.getLoggedInUserOrThrow(request);
            if (UserRole.Admin != user.getRole()) {
                throw new AuthenticationException();
            }
            if ("changeDiscount".equals(request.getParameter("action"))) {
                DiscountCategory discountCategory = DiscountCategory.valueOf(request.getParameter("category"));
                double newDiscount = Double.parseDouble(request.getParameter("newDiscount"));
                fareConfig.changeDiscountRate(discountCategory, newDiscount);
            }
            if ("changeBaseFare".equals(request.getParameter("action"))) {
                TicketType ticketType = TicketType.valueOf(request.getParameter("ticketType"));
                TravelType travelType = TravelType.valueOf(request.getParameter("travelType"));
                double newBaseFare = Double.parseDouble(request.getParameter("newBaseFare"));
                fareConfig.changeBaseFare(travelType, ticketType, newBaseFare);

            }
            Map<String, Double> baseFares = fareConfig.getAllBaseFares();
            Map<DiscountCategory, Double> discountRates = fareConfig.getAllDiscountRates();
            System.out.println(baseFares);
            request.setAttribute("baseFares", baseFares);
            request.setAttribute("discountRates", discountRates);
            request.getRequestDispatcher("adminFaresAndDiscounts.jsp").forward(request, response);
        } catch (SessionExpiredException e) {
            request.setAttribute("errorMessage", "Session expired");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (AuthenticationException e) {
            request.setAttribute("errorMessage", "You are not Authorized to access this resource");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

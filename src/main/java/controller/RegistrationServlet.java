package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Enums.UserRole;
import model.User;
import repository.UserDAO;
import service.UserService;
import utility.SessionManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService(new UserDAO());
    }

    public RegistrationServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String category = request.getParameter("category");
        User result = userService.registerPassenger(username, password, UserRole.Passenger, category);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (result != null) {
            SessionManager.setLoggedInUser(request, result);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("passengerDashboard.jsp");
        } else {
            request.setAttribute("errorMessage", "Try another username");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

        out.close();
    }

}
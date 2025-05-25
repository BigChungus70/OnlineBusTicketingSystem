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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService(new UserDAO());
    }

    public LoginServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.login(username, password);

            if (user != null) {

                SessionManager.setLoggedInUser(request, user);
                response.sendRedirect("passengerDashboard.jsp");
            } else {

                request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
                request.setAttribute("username", username);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {

            request.setAttribute("errorMessage", "Login error: " + e.getMessage());
            request.setAttribute("username", username);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (SessionManager.isLoggedIn(request) && SessionManager.getLoggedInUser(request).getRole() == UserRole.Passenger) {
                response.sendRedirect("passengerDashboard.jsp");
            }
        } catch (NullPointerException e) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Enums.UserRole;
import model.User;
import repository.UserDAO;
import service.UserService;
import utility.SessionExpiredException;
import utility.SessionManager;

import javax.naming.AuthenticationException;
import java.io.IOException;

public class AdminSessionServlet extends HttpServlet {

    private UserService userService;
    @Override
    public void init() {
        userService = new UserService(new UserDAO());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        User user = SessionManager.getLoggedInUser(request);
        if (user != null) {
            if (UserRole.Admin == user.getRole()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("adminDashboard.jsp");
            }
            else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("login.jsp");
            }
        } else {
            try {
                SessionManager.clearSession(request);
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                User loggingUser = userService.login(username, password);
                if (UserRole.Admin == loggingUser.getRole()) {
                    SessionManager.setLoggedInUser(request, loggingUser);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    throw new AuthenticationException();

                }
            } catch (AuthenticationException | NullPointerException e) {
                request.setAttribute("errorMessage", "Wrong username or password");
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            }
        }


    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = SessionManager.getLoggedInUserOrThrow(request);
            SessionManager.clearSession(request);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SessionExpiredException e) {
            request.setAttribute("errorMessage", "You have expired");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        }
    }
}

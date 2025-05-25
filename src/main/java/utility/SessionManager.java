package utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.User;

public class SessionManager {

    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final String USERNAME_SESSION_KEY = "username";

    public static User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static User getLoggedInUserOrThrow(HttpServletRequest request) throws SessionExpiredException {
        User user = getLoggedInUser(request);
        if (user == null) {
            throw new SessionExpiredException("User session has expired or user is not logged in");
        }
        return user;
    }

    public static void setLoggedInUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_SESSION_KEY, user);
        session.setAttribute(USERNAME_SESSION_KEY, user.getUsername());

        // this is in seconds, remember !!!
        session.setMaxInactiveInterval(30 * 60);
    }

    public static void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getLoggedInUser(request) != null;
    }
}
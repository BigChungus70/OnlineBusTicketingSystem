package service;

import model.Enums.DiscountCategory;
import model.User;
import model.Enums.UserRole;
import repository.UserDAO;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerPassenger(String username, String password, UserRole role, String category) {
        DiscountCategory discountCategory = parseCategory(category);
        User user = new User(username, password, role, discountCategory);

        if (userDAO.findByUsername(username) != null || discountCategory == null) {
            return null;
        }
        return userDAO.saveUser(user);
    }

    public User login(String username, String password) {
        if (userDAO.findByUsername(username) != null) {
            return userDAO.checkPassword(username, password);
        }
        return null;
    }

    private DiscountCategory parseCategory(String categoryStr) {
        try {
            return DiscountCategory.valueOf(categoryStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

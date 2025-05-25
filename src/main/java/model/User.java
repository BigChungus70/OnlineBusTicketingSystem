package model;

import jakarta.persistence.*;
import model.Enums.DiscountCategory;
import model.Enums.UserRole;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.Passenger;

    @Enumerated(EnumType.STRING)
    private DiscountCategory category = DiscountCategory.Regular;

    public User() {

    }

    public User(String username, String password, UserRole role, DiscountCategory category) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.category = category;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public DiscountCategory getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public void setCategory(DiscountCategory category) {
        this.category = category;
    }
}

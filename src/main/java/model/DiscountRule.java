package model;

import jakarta.persistence.*;
import model.Enums.DiscountCategory;

@Entity
public class DiscountRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Enumerated(EnumType.STRING)
    private DiscountCategory discountCategory;

    @Column
    private double discountRate;

    public double getDiscountRate() {
        return discountRate;
    }

    public DiscountCategory getUserCategory() {
        return discountCategory;
    }

    public int getId() {
        return id;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public void setUserCategory(DiscountCategory discountCategory) {
        this.discountCategory = discountCategory;
    }
}

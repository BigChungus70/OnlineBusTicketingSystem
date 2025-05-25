package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.DiscountRule;
import model.Enums.DiscountCategory;
import model.Enums.TicketType;
import model.Enums.TravelType;
import model.FareRule;
import utility.GlobalInitializationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FareConfig {

    private static FareConfig instance;
    private static final EntityManagerFactory emf = GlobalInitializationHandler.getEntityManagerFactory();
    private Map<String, Double> baseFares;
    private Map<DiscountCategory, Double> discountRates;

    private FareConfig() {
        loadFareRules();
        loadDiscountRules();
    }

    public static synchronized FareConfig getInstance() {
        if (instance == null) {
            instance = new FareConfig();
        }
        return instance;
    }

    private void loadFareRules() {
        baseFares = new HashMap<>();

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FareRule> fareQuery = em.createQuery(
                    "SELECT f FROM FareRule f", FareRule.class);


            List<FareRule> fareRules = fareQuery.getResultList();
            for (FareRule rule : fareRules) {
                String key = rule.getTravelType().toString() + "_" + rule.getTicketType().toString();
                baseFares.put(key, rule.getBaseFare());
            }

        } finally {
            em.close();
        }
    }

    private void loadDiscountRules() {
        discountRates = new HashMap<>();
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DiscountRule> discountQuery = em.createQuery(
                    "SELECT d FROM DiscountRule d", DiscountRule.class);

            List<DiscountRule> discountRules = discountQuery.getResultList();

            for (DiscountRule rule : discountRules) {
                discountRates.put(rule.getUserCategory(), rule.getDiscountRate());
            }
        } finally {
            em.close();
        }
    }

    public void changeBaseFare(TravelType travelType, TicketType ticketType, double newBaseFare) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE FareRule fr SET baseFare = :newBaseFare " +
                            "WHERE fr.ticketType = :ticketType AND fr.travelType = :travelType")
                    .setParameter("newBaseFare", newBaseFare)
                    .setParameter("ticketType", ticketType)
                    .setParameter("travelType", travelType)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        } finally {
            em.close();
            loadFareRules();
        }
    }
    public void changeDiscountRate(DiscountCategory discountCategory, double newDiscountRate) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE DiscountRule dr SET discountRate = :newDiscountRate " +
                            "WHERE dr.discountCategory = :discountCategory")
                    .setParameter("newDiscountRate", newDiscountRate/100f)
                    .setParameter("discountCategory", discountCategory)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        } finally {
            em.close();
            loadDiscountRules();
        }
    }

    public double getBaseFare(TravelType travelType, TicketType ticketType) {
        String key = travelType.toString() + "_" + ticketType.toString();
        Double fare = baseFares.get(key);

        return fare;
    }
    public Map<DiscountCategory, Double> getAllDiscountRates() {
        return discountRates;
    }
    public Map<String, Double> getAllBaseFares() {
        return baseFares;
    }

    public double getDiscountRate(DiscountCategory discountCategory) {
        return discountRates.get(discountCategory);
    }

    public double getEveningDiscountRate() {
        return discountRates.get(DiscountCategory.Evening);
    }
}
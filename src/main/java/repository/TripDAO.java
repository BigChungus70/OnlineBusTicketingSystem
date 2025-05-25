package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Enums.TravelType;
import model.Trip;
import utility.GlobalInitializationHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TripDAO {

    private static final EntityManagerFactory emf = GlobalInitializationHandler.getEntityManagerFactory();


    public List<Trip> findTrips(String origin, String destination, TravelType travelType, LocalDate date) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT t FROM Trip t WHERE t.origin = :origin AND t.destination = :destination " +
                                    "AND t.travelType = :type AND DATE(t.departureTime) = :date", Trip.class)
                    .setParameter("origin", origin)
                    .setParameter("destination", destination)
                    .setParameter("type", travelType)
                    .setParameter("date", date)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Trip findTripById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT t FROM Trip t WHERE t.id = :id", Trip.class)
                    .setParameter("id", id)
                    .getSingleResult();

        } finally {
            em.close();
        }

    }

    public boolean bookSeat(int tripId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Query query = em.createQuery(
                    "UPDATE Trip t SET t.availableSeats = t.availableSeats - 1 " +
                            "WHERE t.id = :tripId AND t.availableSeats > 0"
            );
            query.setParameter("tripId", tripId);

            int updatedRows = query.executeUpdate();

            if (updatedRows == 0) {
                Trip trip = em.find(Trip.class, tripId);
                if (trip == null) {
                    throw new IllegalArgumentException("Trip not found.");
                } else {
                    throw new IllegalStateException("No available seats.");
                }
            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean cancelSeat(int tripId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Query query = em.createQuery(
                    "UPDATE Trip t SET t.availableSeats = t.availableSeats + 1 " +
                            "WHERE t.id = :tripId"
            );
            query.setParameter("tripId", tripId);

            int updatedRows = query.executeUpdate();

            if (updatedRows == 0) {
                throw new IllegalArgumentException("Trip not found.");
            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Trip> getAllTrips() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Trip t", Trip.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void removeTrip(int tripId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            em.remove(trip);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void registerTrip(Trip trip) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateTrip(int tripId, String origin, String destination, LocalDateTime departure, TravelType travelType, int availableSeats) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("UPDATE Trip t SET " +
                                    "t.origin = :origin, " +
                                    "t.destination = :destination, " +
                                    "t.departureTime = :departure, " +
                                    "t.travelType = :travelType, " +
                                    "t.availableSeats = :availableSeats " +
                                    "WHERE t.id = :tripId")
                    .setParameter("origin", origin)
                    .setParameter("destination", destination)
                    .setParameter("departure", departure)
                    .setParameter("travelType", travelType)
                    .setParameter("availableSeats", availableSeats)
                    .setParameter("tripId", tripId)
                    .executeUpdate();

            em.getTransaction().commit();


        } finally {
            em.close();
        }
    }

}

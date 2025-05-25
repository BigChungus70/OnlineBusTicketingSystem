package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import model.Tickets.Ticket;
import utility.GlobalInitializationHandler;

import java.util.List;

public class TicketDAO {
    private static final EntityManagerFactory emf = GlobalInitializationHandler.getEntityManagerFactory();

    public void persistTicket(Ticket ticket) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
        }
    }

    public void removeTicket(Ticket ticket) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Ticket WHERE id = " + ticket.getId())
                    .executeUpdate();
            em.getTransaction().commit();
        }
    }

    public Ticket findTicketById(int ticketId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Ticket.class, ticketId);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Ticket> findAllTickets() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
        }
    }

    public List<Ticket> findAllUserTickets(int userId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Ticket t WHERE t.user.id = :userId", Ticket.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
}

package model;

import jakarta.persistence.*;
import model.Tickets.Ticket;

import java.time.LocalDateTime;

@Entity
public class ArchivedTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;

    private LocalDateTime purchaseDate;



}

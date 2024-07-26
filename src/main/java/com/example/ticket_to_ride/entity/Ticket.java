package com.example.ticket_to_ride.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ticket", schema = "public")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departure;
    private String arrival;
    private int segments;
    private BigDecimal price;
    private String currency = "GBP";
    private BigDecimal travellerAmount;
    private String traveller;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(String departure, String arrival, int segments, BigDecimal price, BigDecimal travellerAmount, String traveller, User user) {
        this.departure = departure;
        this.arrival = arrival;
        this.segments = segments;
        this.price = price;
        this.travellerAmount = travellerAmount;
        this.traveller = traveller;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

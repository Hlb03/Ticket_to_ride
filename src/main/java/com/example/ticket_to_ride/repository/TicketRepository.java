package com.example.ticket_to_ride.repository;

import com.example.ticket_to_ride.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = """
            SELECT t FROM Ticket t JOIN User u ON t.user.id = u.id WHERE u.fullName = :fullName
            """)
    List<Ticket> getTicketsByUsername(@Param("fullName") String username);
}

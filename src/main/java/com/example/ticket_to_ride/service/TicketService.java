package com.example.ticket_to_ride.service;

import com.example.ticket_to_ride.dto.TicketDto;
import com.example.ticket_to_ride.dto.TicketStoreDto;
import com.example.ticket_to_ride.dto.ticket_store_response.TicketStoreResponse;
import com.example.ticket_to_ride.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<TicketDto> getAllUserTickets(String username);

    TicketStoreResponse saveTicket(TicketStoreDto ticketStoreDto, String username);
}

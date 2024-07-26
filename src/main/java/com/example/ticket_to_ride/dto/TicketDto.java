package com.example.ticket_to_ride.dto;

import java.math.BigDecimal;

public record TicketDto(
        Long id,
        String departure,
        String arrival,
        int segments,
        BigDecimal price,
        String currency,
        BigDecimal travellerAmount,
        String traveller
) {
}

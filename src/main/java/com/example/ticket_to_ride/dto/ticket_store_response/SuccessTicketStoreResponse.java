package com.example.ticket_to_ride.dto.ticket_store_response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SuccessTicketStoreResponse extends TicketStoreResponse{

    private final BigDecimal change;

    public SuccessTicketStoreResponse(BigDecimal change) {
        super("success");
        this.change = change;
    }
}

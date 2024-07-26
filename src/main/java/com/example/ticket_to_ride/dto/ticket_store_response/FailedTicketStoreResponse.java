package com.example.ticket_to_ride.exception_handler.exception_response;

import com.example.ticket_to_ride.dto.ticket_store_response.TicketStoreResponse;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FailedTicketStoreResponse extends TicketStoreResponse {

    private final BigDecimal lackOf;

    public FailedTicketStoreResponse(BigDecimal lackOf) {
        super("failure");
        this.lackOf = lackOf;
    }
}

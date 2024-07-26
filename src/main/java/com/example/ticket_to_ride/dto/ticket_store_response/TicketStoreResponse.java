package com.example.ticket_to_ride.dto.ticket_store_response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketStoreResponse {

    private final String result;
    private final String currency = "GBP";
}

package com.example.ticket_to_ride.exceptions;

public class InvalidTicketStoreException extends BadRequestException {
    public InvalidTicketStoreException(String message) {
        super(message);
    }
}

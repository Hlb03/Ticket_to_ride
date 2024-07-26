package com.example.ticket_to_ride.exceptions;

import java.math.BigDecimal;

public class FailedToStoreTicketException extends BadRequestException {

    private final BigDecimal lackOfMoneyAmount;

    public FailedToStoreTicketException(String message, BigDecimal lackOfMoneyAmount) {
        super(message);
        this.lackOfMoneyAmount = lackOfMoneyAmount;
    }

    public BigDecimal getLackOfMoneyAmount() {
        return lackOfMoneyAmount;
    }
}

package com.example.ticket_to_ride.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TicketStoreDto(
        String departure,
        String arrival,
        @NotNull(message = "'segments' field should be present")
        @Min(value = 1, message = "Segments amount could not be less than 1")
        int segments,
        @NotNull(message = "'price' field should be present")
        @Min(value = 1, message = "Price could not be less than 5")
        BigDecimal price,
        @Pattern(regexp = "[A-Z]{3,4}", message = "'currency' should contain from 3 to 4 capital letters")
        String currency,
        @NotNull(message = "'travellerPrice' field should be present")
        @Min(value = 1, message = "Traveller price could not be less than 5")
        BigDecimal travellerAmount,
        @NotNull(message = "'traveller' field should be present")
        @Size(max = 32, message = "'traveller' field should contain 32 symbols max")
        @Pattern(regexp = "[A-Z][a-z]{1,9} [A-Z][a-z]{1,15}", message = "'traveller' field should contain 2 words (e.g. Jane Doe)")
        String traveller
) {
}

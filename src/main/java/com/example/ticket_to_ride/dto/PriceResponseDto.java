package com.example.ticket_to_ride.dto;

import java.math.BigDecimal;

public record PriceResponseDto(
        int segments,
        BigDecimal price,
        String currency
) {}

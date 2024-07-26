package com.example.ticket_to_ride.service;

import java.math.BigDecimal;

public interface CalculatePriceService {

    BigDecimal calculatePrice(int amountOfSegmentsToTravel);
}

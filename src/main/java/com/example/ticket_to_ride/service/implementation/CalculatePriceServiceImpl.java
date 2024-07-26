package com.example.ticket_to_ride.service.implementation;

import com.example.ticket_to_ride.service.CalculatePriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatePriceServiceImpl implements CalculatePriceService {

    @Value("${route.price.three}")
    private int priceForThreeSegments;
    @Value("${route.price.two}")
    private int priceForTwoSegments;
    @Value("${route.price.one}")
    private int priceForOneSegment;

    @Override
    public BigDecimal calculatePrice(int amountOfSegmentsToTravel) {
        log.info("Calculating price for {} segments", amountOfSegmentsToTravel);
        BigDecimal generalPrice = BigDecimal.ZERO;
        if (amountOfSegmentsToTravel >= 3) {
            generalPrice = generalPrice.add(new BigDecimal((amountOfSegmentsToTravel / 3) * priceForThreeSegments));
            amountOfSegmentsToTravel = amountOfSegmentsToTravel % 3;
        }
        if (amountOfSegmentsToTravel == 2) {
            generalPrice = generalPrice.add(new BigDecimal((amountOfSegmentsToTravel / 2) * priceForTwoSegments));
        }
        if (amountOfSegmentsToTravel == 1) {
            generalPrice = generalPrice.add(new BigDecimal(priceForOneSegment));
        }

        return generalPrice;
    }
}

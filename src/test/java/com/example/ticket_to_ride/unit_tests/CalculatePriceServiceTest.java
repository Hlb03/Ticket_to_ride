package com.example.ticket_to_ride.unit_tests;

import com.example.ticket_to_ride.service.CalculatePriceService;
import com.example.ticket_to_ride.service.implementation.CalculatePriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CalculatePriceServiceTest {

    private CalculatePriceService calculatePriceService;

    @BeforeEach
    public void setUp() throws Exception {
        calculatePriceService = new CalculatePriceServiceImpl();
        setUpProperties();
    }

    @Test
    public void calculatePrice() {
        assertEquals(new BigDecimal(5), calculatePriceService.calculatePrice(1));
        assertEquals(new BigDecimal(10), calculatePriceService.calculatePrice(3));
        assertEquals(new BigDecimal(17), calculatePriceService.calculatePrice(5));
        assertEquals(new BigDecimal(25), calculatePriceService.calculatePrice(7));
    }

    @Test
    public void calculatePrice_WrongInfo() {
        assertNotEquals(new BigDecimal(4), calculatePriceService.calculatePrice(1));
        assertNotEquals(new BigDecimal(8), calculatePriceService.calculatePrice(5));
        assertNotEquals(new BigDecimal(20), calculatePriceService.calculatePrice(3));
        assertNotEquals(new BigDecimal(21), calculatePriceService.calculatePrice(7));
    }

    private void setUpProperties() throws Exception {
        Field threeSegmentsPrice = calculatePriceService.getClass().getDeclaredField("priceForThreeSegments");
        threeSegmentsPrice.setAccessible(true);
        threeSegmentsPrice.setInt(calculatePriceService, 10);

        Field twoSegmentsPrice = calculatePriceService.getClass().getDeclaredField("priceForTwoSegments");
        twoSegmentsPrice.setAccessible(true);
        twoSegmentsPrice.setInt(calculatePriceService, 7);

        Field oneSegmentPrice = calculatePriceService.getClass().getDeclaredField("priceForOneSegment");
        oneSegmentPrice.setAccessible(true);
        oneSegmentPrice.setInt(calculatePriceService, 5);
    }
}

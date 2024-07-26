package com.example.ticket_to_ride.unit_tests;

import com.example.ticket_to_ride.exceptions.RouteNotFoundException;
import com.example.ticket_to_ride.service.RouteSearchService;
import com.example.ticket_to_ride.service.implementation.RouteSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RouteSearchServiceTest {

    private RouteSearchService routeSearchService;

    @BeforeEach
    public void setUp() {
        routeSearchService = new RouteSearchServiceImpl();
    }

    @Test
    public void getRouteDistance() {
        assertEquals(5, routeSearchService.getRouteDistance("London", "Birgmingham"));
        assertEquals(3, routeSearchService.getRouteDistance("Birgmingham", "Bristol"));
        assertEquals(4, routeSearchService.getRouteDistance("Swindon", "Reading"));
        assertEquals(2, routeSearchService.getRouteDistance("Coventry", "Northhampton"));
    }

    @Test
    public void getRouteDistance_FailedToFindRoute() {
        Exception e = assertThrows(RouteNotFoundException.class,
                () -> routeSearchService.getRouteDistance("London", "New York"));

        assertEquals("Failed to find a route from London to New York", e.getMessage());
    }
}

package com.example.ticket_to_ride.service.implementation;

import com.example.ticket_to_ride.entity.Route;
import com.example.ticket_to_ride.exceptions.RouteNotFoundException;
import com.example.ticket_to_ride.service.RouteSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RouteSearchServiceImpl implements RouteSearchService {

    private final List<Route> routes = new ArrayList<>();

    {
        routes.add(new Route("Birgmingham", "Bristol", 3));
        routes.add(new Route("Birgmingham", "Coventry", 1));
        routes.add(new Route("Birgmingham", "Swindon", 4));
        routes.add(new Route("Birgmingham", "Northhampton", 3));
        routes.add(new Route("Birgmingham", "London", 5));
        routes.add(new Route("Birgmingham", "Reading", 6));

        routes.add(new Route("Bristol", "Swindon", 2));
        routes.add(new Route("Bristol", "Reading", 6));
        routes.add(new Route("Bristol", "London", 7));
        routes.add(new Route("Bristol", "Coventry", 4));
        routes.add(new Route("Bristol", "Northhampton", 6));

        routes.add(new Route("Swindon", "Reading", 4));
        routes.add(new Route("Swindon", "London", 5));
        routes.add(new Route("Swindon", "Coventry", 5));
        routes.add(new Route("Swindon", "Northhampton", 7));

        routes.add(new Route("Reading", "London", 1));
        routes.add(new Route("Reading", "Northhampton", 3));
        routes.add(new Route("Reading", "Coventry", 5));

        routes.add(new Route("London", "Northhampton", 2));
        routes.add(new Route("London", "Coventry", 4));

        routes.add(new Route("Northhampton", "Coventry", 2));
    }

    /**
     * Calculates the route distance between two towns
     *
     * @param departureTown start point of route
     * @param arrivalTown   destination point of route
     * @return amount of segments between provided towns
     * @throws RouteNotFoundException in case there is no route between these towns
     */
    @Override
    public int getRouteDistance(String departureTown, String arrivalTown) {
        log.info("Searching route with departure -> {} and arrival -> {}", departureTown, arrivalTown);
        Route route = routes
                .stream()
                .filter(r -> (r.getDeparture().equals(departureTown) && r.getArrival().equals(arrivalTown))
                        || (r.getDeparture().equals(arrivalTown) && r.getArrival().equals(departureTown)))
                .findFirst()
                .orElseThrow(() -> new RouteNotFoundException(
                        String.format("Failed to find a route from %s to %s", departureTown, arrivalTown))
                );

        return route.getSegments();
    }
}

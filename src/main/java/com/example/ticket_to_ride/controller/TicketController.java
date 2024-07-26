package com.example.ticket_to_ride.controller;

import com.example.ticket_to_ride.dto.PriceResponseDto;
import com.example.ticket_to_ride.dto.TicketDto;
import com.example.ticket_to_ride.dto.TicketStoreDto;
import com.example.ticket_to_ride.dto.ticket_store_response.TicketStoreResponse;
import com.example.ticket_to_ride.service.CalculatePriceService;
import com.example.ticket_to_ride.service.RouteSearchService;
import com.example.ticket_to_ride.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final CalculatePriceService calculatePriceService;
    private final RouteSearchService routeSearchService;
    private final TicketService ticketService;

    @GetMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public PriceResponseDto calculateRoutePrice(@RequestParam String departure, @RequestParam String arrival) {
        int amountOfSegmentsToTravel = routeSearchService.getRouteDistance(departure, arrival);
        BigDecimal travelPrice = calculatePriceService.calculatePrice(amountOfSegmentsToTravel);

        return new PriceResponseDto(amountOfSegmentsToTravel, travelPrice, "GPB");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketDto> getAllUserTickets(Authentication authentication) {
        return ticketService.getAllUserTickets(authentication.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketStoreResponse saveTicket(@RequestBody @Valid TicketStoreDto ticketStoreDto,
                                          Authentication authentication) {
        return ticketService.saveTicket(ticketStoreDto, authentication.getName());
    }
}

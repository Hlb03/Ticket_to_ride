package com.example.ticket_to_ride.unit_tests;

import com.example.ticket_to_ride.dto.TicketStoreDto;
import com.example.ticket_to_ride.dto.ticket_store_response.SuccessTicketStoreResponse;
import com.example.ticket_to_ride.entity.Ticket;
import com.example.ticket_to_ride.entity.User;
import com.example.ticket_to_ride.exceptions.FailedToStoreTicketException;
import com.example.ticket_to_ride.exceptions.InvalidTicketStoreException;
import com.example.ticket_to_ride.repository.TicketRepository;
import com.example.ticket_to_ride.repository.UserRepository;
import com.example.ticket_to_ride.service.CalculatePriceService;
import com.example.ticket_to_ride.service.RouteSearchService;
import com.example.ticket_to_ride.service.implementation.TicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RouteSearchService routeSearchService;
    @Mock
    private CalculatePriceService calculatePriceService;

    @Test
    public void saveTicket() {
        Ticket ticketToStore = buildTicket(17, 20);
        TicketStoreDto requestDto = buildTicketDto(17, 20);

        when(ticketRepository.save(ticketToStore)).thenReturn(ticketToStore);
        when(routeSearchService.getRouteDistance(requestDto.departure(), requestDto.arrival())).thenReturn(requestDto.segments());
        when(calculatePriceService.calculatePrice(requestDto.segments())).thenReturn(requestDto.price());
        when(userRepository.findUserByFullName("Jane Doe")).thenReturn(Optional.of(new User()));

        SuccessTicketStoreResponse response = (SuccessTicketStoreResponse) ticketService.saveTicket(requestDto, "Jane Doe");

        assertEquals(requestDto.travellerAmount().subtract(requestDto.price()), response.getChange());
        assertEquals("success", response.getResult());
        assertEquals("GBP", response.getCurrency());
        verify(ticketRepository).save(ticketToStore);
        verify(userRepository).findUserByFullName("Jane Doe");
        verify(routeSearchService).getRouteDistance(requestDto.departure(), requestDto.arrival());
        verify(calculatePriceService).calculatePrice(requestDto.segments());
    }

    @Test
    public void saveTicket_LackOfMoneyException() {
        TicketStoreDto requestDto = buildTicketDto(17, 15);

        when(routeSearchService.getRouteDistance(requestDto.departure(), requestDto.arrival())).thenReturn(requestDto.segments());
        when(calculatePriceService.calculatePrice(requestDto.segments())).thenReturn(requestDto.price());

        Exception exception = assertThrows(FailedToStoreTicketException.class,
                () -> ticketService.saveTicket(requestDto, "Another User"));

        assertEquals("Failed to store ticket. User has lack of 2 money", exception.getMessage());
        verify(routeSearchService).getRouteDistance(requestDto.departure(), requestDto.arrival());
        verify(calculatePriceService).calculatePrice(requestDto.segments());
    }

    @Test
    public void saveTicket_IncorrectSegmentsValue() {
        TicketStoreDto requestDto = buildTicketDto(17, 20);

        when(routeSearchService.getRouteDistance(requestDto.departure(), requestDto.arrival())).thenReturn(requestDto.segments() - 1);

        Exception exception = assertThrows(InvalidTicketStoreException.class,
                () -> ticketService.saveTicket(requestDto, "User Two"));

        assertEquals("Actual route contains " + (requestDto.segments() - 1) + " segments, but provided value is " + requestDto.segments(),
                exception.getMessage());
        verify(routeSearchService).getRouteDistance(requestDto.departure(), requestDto.arrival());
    }

    @Test
    public void saveTicket_IncorrectPriceValue() {
        TicketStoreDto requestDto = buildTicketDto(15, 15);

        when(routeSearchService.getRouteDistance(requestDto.departure(), requestDto.arrival())).thenReturn(requestDto.segments());
        when(calculatePriceService.calculatePrice(requestDto.segments())).thenReturn(requestDto.price().add(BigDecimal.TEN));

        Exception exception = assertThrows(InvalidTicketStoreException.class,
                () -> ticketService.saveTicket(requestDto, "More Users"));

        assertEquals("Actual price for the trip is " + requestDto.price().add(BigDecimal.TEN) + ", but provided value is " + requestDto.price(),
                exception.getMessage());
        verify(routeSearchService).getRouteDistance(requestDto.departure(), requestDto.arrival());
        verify(calculatePriceService).calculatePrice(requestDto.segments());
    }

    private Ticket buildTicket(int price, int travellerAmount) {
        return new Ticket("London", "Birgmingham", 5, new BigDecimal(price), new BigDecimal(travellerAmount),
                "Random User", new User());
    }

    private TicketStoreDto buildTicketDto(int price, int travellerAmount) {
        return new TicketStoreDto("London", "Birgmingham", 5, new BigDecimal(price),
                "GBP", new BigDecimal(travellerAmount), "Random User");
    }
}

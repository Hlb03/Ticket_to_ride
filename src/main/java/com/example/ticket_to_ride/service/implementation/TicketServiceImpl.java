package com.example.ticket_to_ride.service.implementation;

import com.example.ticket_to_ride.dto.TicketDto;
import com.example.ticket_to_ride.dto.TicketStoreDto;
import com.example.ticket_to_ride.dto.ticket_store_response.SuccessTicketStoreResponse;
import com.example.ticket_to_ride.dto.ticket_store_response.TicketStoreResponse;
import com.example.ticket_to_ride.entity.Ticket;
import com.example.ticket_to_ride.entity.User;
import com.example.ticket_to_ride.exceptions.FailedToStoreTicketException;
import com.example.ticket_to_ride.exceptions.InvalidTicketStoreException;
import com.example.ticket_to_ride.repository.TicketRepository;
import com.example.ticket_to_ride.repository.UserRepository;
import com.example.ticket_to_ride.service.CalculatePriceService;
import com.example.ticket_to_ride.service.RouteSearchService;
import com.example.ticket_to_ride.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CalculatePriceService calculatePriceService;
    private final RouteSearchService routeSearchService;

    @Override
    public List<TicketDto> getAllUserTickets(String username) {
        log.info("Getting all tickets for user {}", username);
        return ticketRepository.getTicketsByUsername(username)
                .stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    /**
     * Validates provided ticket information. Checks whether user provided enough money.
     * If all early mentioned conditions are true then ticket is stored in database
     *
     * @param ticketStoreDto ticket parameters to be stored
     * @param username       ticket owner's name
     * @return information about ticket stored ticket
     * @throws InvalidTicketStoreException in case provided route segments is not the same as it is actully
     *                                     or ticket price is not the same as it is actually
     * @throws FailedToStoreTicketException in case user provided less money than route costs
     */
    @Override
    public TicketStoreResponse saveTicket(TicketStoreDto ticketStoreDto, String username) {
        validateTicketDto(ticketStoreDto);

        BigDecimal subtractTravellerPriceFromActualPrice = ticketStoreDto.travellerAmount().subtract(ticketStoreDto.price());
        if (subtractTravellerPriceFromActualPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new FailedToStoreTicketException(
                    String.format("Failed to store ticket. User has lack of %s money", subtractTravellerPriceFromActualPrice),
                    subtractTravellerPriceFromActualPrice
            );
        }
        User user = userRepository.findUserByFullName(username).get();
        ticketRepository.save(
                new Ticket(ticketStoreDto.departure(), ticketStoreDto.arrival(), ticketStoreDto.segments(),
                        ticketStoreDto.price(), ticketStoreDto.travellerAmount(), ticketStoreDto.traveller(), user)
        );
        log.info("Ticket successfully stored for the user {}", username);
        return new SuccessTicketStoreResponse(subtractTravellerPriceFromActualPrice.abs());
    }

    private void validateTicketDto(TicketStoreDto ticketStoreDto) {
        int segmentsToTravel = routeSearchService.getRouteDistance(ticketStoreDto.departure(), ticketStoreDto.arrival());
        BigDecimal routePrice = calculatePriceService.calculatePrice(segmentsToTravel);

        if (ticketStoreDto.segments() != segmentsToTravel) {
            throw new InvalidTicketStoreException(
                    String.format("Actual route contains %s segments, but provided value is %s", segmentsToTravel, ticketStoreDto.segments())
            );
        }
        if (!routePrice.equals(ticketStoreDto.price())) {
            throw new InvalidTicketStoreException(
                    String.format("Actual price for the trip is %s, but provided value is %s", routePrice, ticketStoreDto.price())
            );
        }
    }

    private TicketDto mapEntityToDto(Ticket ticket) {
        return new TicketDto(ticket.getId(), ticket.getDeparture(), ticket.getArrival(), ticket.getSegments(),
                ticket.getPrice(), ticket.getCurrency(), ticket.getTravellerAmount(), ticket.getTraveller());
    }
}

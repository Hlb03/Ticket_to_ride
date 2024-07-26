package com.example.ticket_to_ride.exception_handler;

import com.example.ticket_to_ride.dto.ticket_store_response.FailedTicketStoreResponse;
import com.example.ticket_to_ride.exception_handler.exception_response.ExceptionResponse;
import com.example.ticket_to_ride.exceptions.BadRequestException;
import com.example.ticket_to_ride.exceptions.ConflictException;
import com.example.ticket_to_ride.exceptions.FailedToStoreTicketException;
import com.example.ticket_to_ride.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleObjectValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("Validation exception. All errors: {}", e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        return new ExceptionResponse(LocalDateTime.now(), 400, HttpStatus.BAD_REQUEST,
                e.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList(),
                request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleNotFoundExceptions(NotFoundException e, HttpServletRequest request) {
        log.warn("Not found exception occurred: {}", e.getMessage());
        return new ExceptionResponse(LocalDateTime.now(), 404, HttpStatus.NOT_FOUND, List.of(e.getMessage()), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ExceptionResponse handleConflictExceptions(ConflictException e, HttpServletRequest request) {
        log.warn("Conflict exception occurred: {}", e.getMessage());
        return new ExceptionResponse(LocalDateTime.now(), 409, HttpStatus.CONFLICT, List.of(e.getMessage()), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse handleBadRequestExceptions(BadRequestException e, HttpServletRequest request) {
        log.warn("Bad request exception occurred: {}", e.getMessage());
        return new ExceptionResponse(LocalDateTime.now(), 400, HttpStatus.BAD_REQUEST, List.of(e.getMessage()), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedToStoreTicketException.class)
    public FailedTicketStoreResponse handleBadRequestToStoreTicket(FailedToStoreTicketException e) {
        log.warn(e.getMessage());
        return new FailedTicketStoreResponse(e.getLackOfMoneyAmount());
    }
}

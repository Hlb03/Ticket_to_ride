package com.example.ticket_to_ride.exception_handler.exception_response;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionResponse(
        LocalDateTime timestamp,
        Integer code,
        HttpStatus status,
        @JsonAlias("message(s)")
        List<String> message,
        String requestPath
) {
}

package com.example.ticket_to_ride.exceptions;

public class UserAlreadyRegisteredException extends ConflictException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}

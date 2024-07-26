package com.example.ticket_to_ride.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotNull(message = "'fullName' field should be present")
        @Size(max = 32, message = "'fullName' field should contain 32 symbols max")
        @Pattern(regexp = "[A-Z][a-z]{1,9} [A-Z][a-z]{1,15}", message = "'fullName' field should contain 2 words (e.g. Jane Doe)")
        String fullName,
        @NotNull(message = "'password' field should be present")
        @Size(min = 6, max = 24, message = "'password' length should be between 6 and 24 symbols")
        @Pattern(regexp = "\\S{6,24}", message = "'password' can consist of any symbols except for whitespaces")
        String password
) {
}

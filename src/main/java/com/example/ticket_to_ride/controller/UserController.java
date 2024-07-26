package com.example.ticket_to_ride.controller;

import com.example.ticket_to_ride.dto.UserRegistrationDto;
import com.example.ticket_to_ride.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserRegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
    }
}

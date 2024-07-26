package com.example.ticket_to_ride.unit_tests;

import com.example.ticket_to_ride.dto.UserRegistrationDto;
import com.example.ticket_to_ride.entity.User;
import com.example.ticket_to_ride.exceptions.UserAlreadyRegisteredException;
import com.example.ticket_to_ride.repository.UserRepository;
import com.example.ticket_to_ride.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void registerUser() {
        UserRegistrationDto requestDto = buildRegistrationDto();
        User user = new User(requestDto.fullName(), requestDto.password());

        when(passwordEncoder.encode(requestDto.password())).thenReturn(requestDto.password());
        when(userRepository.findUserByFullName(requestDto.fullName())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        userService.registerUser(requestDto);

        verify(passwordEncoder).encode(requestDto.password());
        verify(userRepository).findUserByFullName(requestDto.fullName());
        verify(userRepository).save(user);
    }

    @Test
    public void registerUser_UserNameConflictException() {
        UserRegistrationDto requestDto = buildRegistrationDto();

        when(userRepository.findUserByFullName(requestDto.fullName())).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(UserAlreadyRegisteredException.class,
                () -> userService.registerUser(requestDto));

        assertEquals("User with fullName " + requestDto.fullName() + " is already registered", exception.getMessage());
        verify(userRepository).findUserByFullName(requestDto.fullName());
    }

    private UserRegistrationDto buildRegistrationDto() {
        return new UserRegistrationDto("First User", "password");
    }
}

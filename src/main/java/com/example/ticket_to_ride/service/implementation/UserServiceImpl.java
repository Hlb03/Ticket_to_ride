package com.example.ticket_to_ride.service.implementation;

import com.example.ticket_to_ride.dto.UserRegistrationDto;
import com.example.ticket_to_ride.entity.User;
import com.example.ticket_to_ride.exceptions.UserAlreadyRegisteredException;
import com.example.ticket_to_ride.repository.UserRepository;
import com.example.ticket_to_ride.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.findUserByFullName(registrationDto.fullName()).isPresent())
            throw new UserAlreadyRegisteredException(
                    String.format("User with fullName %s is already registered", registrationDto.fullName())
            );

        userRepository.save(new User(registrationDto.fullName(), passwordEncoder.encode(registrationDto.password())));
        log.info("User with fullName {} is successfully saved", registrationDto.fullName());
    }
}

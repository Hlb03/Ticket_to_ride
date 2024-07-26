package com.example.ticket_to_ride.security_configuration;

import com.example.ticket_to_ride.entity.User;
import com.example.ticket_to_ride.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByFullName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to found user with full name " + username));

        log.info("Authenticating user with username {}", username);
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .disabled(false)
                .build();
    }
}

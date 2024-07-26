package com.example.ticket_to_ride.repository;

import com.example.ticket_to_ride.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByFullName(String fullName);
}

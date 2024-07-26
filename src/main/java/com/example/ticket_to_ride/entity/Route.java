package com.example.ticket_to_ride.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private String departure;
    private String arrival;
    private int segments;
}

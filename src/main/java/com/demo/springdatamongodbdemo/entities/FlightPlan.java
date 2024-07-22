package com.demo.springdatamongodbdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "FlightPlans")
@AllArgsConstructor
public class FlightPlan {
    @Id
    private String id;
    @TextIndexed
    @Field(name = "departure")
    private String departureCity;
    @TextIndexed
    @Field(name = "destination")
    private String destinationCity;
    @Indexed(direction = IndexDirection.ASCENDING, unique = false)
    private LocalDateTime departureDateTime;
    private int flightDuration;
    private List<String> crossedCountries;
    private boolean isInternational;
    private Aircraft aircraft;
}

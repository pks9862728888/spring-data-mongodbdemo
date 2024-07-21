package com.demo.springdatamongodbdemo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "FlightPlans")
public class FlightPlan {
    @Id
    private String id;
    @Field(name = "departure")
    private String departureCity;
    @Field(name = "destination")
    private String destinationCity;
    @Indexed(direction = IndexDirection.ASCENDING, unique = false)
    private LocalDateTime departureDateTime;
    private int flightDuration;
    private List<String> crossedCountries;
    private boolean isInternational;
    private Aircraft aircraft;
}

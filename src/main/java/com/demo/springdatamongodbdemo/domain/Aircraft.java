package com.demo.springdatamongodbdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Aircraft {
    private String model;
    @Field(name = "capacity")
    private int seatCapacity;
    private WakeTurbulence wakeTurbulence;
}

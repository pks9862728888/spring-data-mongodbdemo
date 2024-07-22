package com.demo.springdatamongodbdemo.factories;

import com.demo.springdatamongodbdemo.entities.Aircraft;
import com.demo.springdatamongodbdemo.entities.WakeTurbulence;

public abstract class AircraftFactory {
    public static Aircraft buildBoeing737() {
        return new Aircraft("Boeing 737 500", 130, WakeTurbulence.Medium);
    }

    public static Aircraft buildBoeing747() {
        return new Aircraft("Boeing 747 300", 490, WakeTurbulence.Heavy);
    }

    public static Aircraft buildAirbusA350() {
        return new Aircraft("Boeing 747 300", 330, WakeTurbulence.Heavy);
    }

    public static Aircraft buildEmbraerE175() {
        return new Aircraft("Embraer E175 E2", 80, WakeTurbulence.Medium);
    }

    public static Aircraft buildEmbraerPhenom() {
        return new Aircraft("Embraer Phenom 300", 6, WakeTurbulence.Light);
    }
}

package com.example.expensecalculator.service;

import com.example.expensecalculator.enums.VehicleType;

import java.util.EnumMap;
import java.util.Map;

public class VehicleCapacityRetrieverImpl implements VehicleCapacityRetriever {
    private static final Map<VehicleType, Integer> capacities = new EnumMap<>(VehicleType.class);

    static {
        capacities.put(VehicleType.CAR, 5);
        capacities.put(VehicleType.SUV, 7);
        capacities.put(VehicleType.VAN, 10);
        capacities.put(VehicleType.BUS, 40);
    }

    @Override
    public int getMaxPassengerCapacity(VehicleType vehicleType) {
        return capacities.getOrDefault(vehicleType, 0);
    }
}

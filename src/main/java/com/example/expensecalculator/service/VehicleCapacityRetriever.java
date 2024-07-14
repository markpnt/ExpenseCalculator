package com.example.expensecalculator.service;

import com.example.expensecalculator.enums.VehicleType;

public interface VehicleCapacityRetriever {
    int getMaxPassengerCapacity(VehicleType vehicleType);
}

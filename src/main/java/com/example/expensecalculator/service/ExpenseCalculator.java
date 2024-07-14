package com.example.expensecalculator.service;

import com.example.expensecalculator.enums.FuelType;
import com.example.expensecalculator.enums.VehicleType;

import java.math.BigDecimal;

public interface ExpenseCalculator {

    BigDecimal calculateExpense(VehicleType vehicleType, FuelType fuelType, String destination, Integer numberOfPeopleTravelling, Boolean isAirConditioningRequired);

}

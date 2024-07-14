package com.example.expensecalculator.service;

import com.example.expensecalculator.enums.FuelType;
import com.example.expensecalculator.enums.VehicleType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.example.expensecalculator.enums.FuelType.DIESEL;
import static com.example.expensecalculator.enums.VehicleType.BUS;

public class ExpenseCalculatorImpl implements ExpenseCalculator {
    private static final BigDecimal STANDARD_RATE = new BigDecimal("0.20");
    private static final BigDecimal DIESEL_DISCOUNT = new BigDecimal("0.05");
    private static final BigDecimal AIR_CONDITION_RATE = new BigDecimal("0.10");
    private static final BigDecimal ADDITIONAL_CHARGE_PER_PERSON = new BigDecimal("0.05");
    private static final BigDecimal BUS_DISCOUNT = new BigDecimal("0.02");

    private final DistanceRetriever distanceRetriever;
    private final VehicleCapacityRetriever vehicleCapacityRetriever;

    public ExpenseCalculatorImpl(DistanceRetriever distanceRetriever, VehicleCapacityRetriever vehicleCapacityRetriever) {
        this.distanceRetriever = distanceRetriever;
        this.vehicleCapacityRetriever = vehicleCapacityRetriever;
    }

    @Override
    public BigDecimal calculateExpense(VehicleType vehicleType, FuelType fuelType, String destination, Integer numberOfPeopleTravelling, Boolean isAirConditioningRequired) {
        int distance = distanceRetriever.getDistance(destination);
        int vehicleCapacity = vehicleCapacityRetriever.getMaxPassengerCapacity(vehicleType);
        BigDecimal perKmCost = STANDARD_RATE;
        if (Objects.equals(fuelType, DIESEL)) {
            perKmCost = perKmCost.subtract(DIESEL_DISCOUNT);
        }
        if (Objects.equals(isAirConditioningRequired, Boolean.TRUE)) {
            perKmCost = perKmCost.add(AIR_CONDITION_RATE);
        }
        if (numberOfPeopleTravelling > vehicleCapacity) {
            perKmCost = perKmCost.add(ADDITIONAL_CHARGE_PER_PERSON.multiply(new BigDecimal(numberOfPeopleTravelling - vehicleCapacity)));
        }
        if (Objects.equals(vehicleType, BUS)) {
            perKmCost = perKmCost.multiply(BigDecimal.ONE.subtract(BUS_DISCOUNT));
        }
        return perKmCost.multiply(new BigDecimal(distance)).setScale(2, RoundingMode.HALF_UP);
    }
}

package com.example.expensecalculator.unit.service;

import com.example.expensecalculator.enums.FuelType;
import com.example.expensecalculator.enums.VehicleType;
import com.example.expensecalculator.service.DistanceRetriever;
import com.example.expensecalculator.service.DistanceRetrieverImpl;
import com.example.expensecalculator.service.ExpenseCalculator;
import com.example.expensecalculator.service.ExpenseCalculatorImpl;
import com.example.expensecalculator.service.VehicleCapacityRetriever;
import com.example.expensecalculator.service.VehicleCapacityRetrieverImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.example.expensecalculator.unit.service.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseCalculatorImplTest {
    private ExpenseCalculator expenseCalculator;

    @BeforeEach
    public void setUp() {
        DistanceRetriever distanceRetriever = new DistanceRetrieverImpl();
        VehicleCapacityRetriever vehicleCapacityRetriever = new VehicleCapacityRetrieverImpl();
        expenseCalculator = new ExpenseCalculatorImpl(distanceRetriever, vehicleCapacityRetriever);
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testCalculateExpense(VehicleType vehicleType, FuelType fuelType, String destination, int numberOfPeopleTravelling, boolean isAirConditioningRequired, BigDecimal expectedExpense) {
        BigDecimal expense = expenseCalculator.calculateExpense(vehicleType, fuelType, destination, numberOfPeopleTravelling, isAirConditioningRequired);
        assertEquals(expectedExpense, expense);
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(VehicleType.CAR, FuelType.PETROL, MUNICH, FIVE, false, new BigDecimal(CAR_PETROL_NO_AC_NO_EXTRA_PASSENGERS_COST)), // Calculation: 0.20 EUR/Km * 584 Km = 116.80 EUR
                Arguments.of(VehicleType.CAR, FuelType.PETROL, MUNICH, FIVE, true, new BigDecimal(CAR_PETROL_WITH_AC_NO_EXTRA_PASSENGERS_COST)), // Calculation: (0.20 + 0.10) EUR/Km * 584 Km = 175.20 EUR
                Arguments.of(VehicleType.CAR, FuelType.PETROL, MUNICH, SIX, true, new BigDecimal(CAR_PETROL_WITH_AC_EXTRA_PASSENGERS_COST)), // Calculation: (0.20  + 0.10 + 0.05) EUR/Km * 584 Km = 204.40 EUR
                Arguments.of(VehicleType.SUV, FuelType.DIESEL, MUNICH, EIGHT, false, new BigDecimal(SUV_DIESEL_NO_AC_EXTRA_PASSENGERS_COST)), // Calculation: (0.20 - 0.05 + 0.05) EUR/Km * 584 Km = 116.80 EUR
                Arguments.of(VehicleType.BUS, FuelType.PETROL, MUNICH, FORTY, false, new BigDecimal(BUS_PETROL_NO_AC_NO_EXTRA_PASSENGERS_COST)), // Calculation: 0.196 EUR/Km * 584 Km = 114.46 EUR
                Arguments.of(VehicleType.BUS, FuelType.DIESEL, MUNICH, FORTY_FIVE, true, new BigDecimal(BUS_DIESEL_WITH_AC_EXTRA_PASSENGERS_COST)) // Calculation: ((0.20 - 0.05 + 0.10 + 0.25) * 0.98 EUR/Km * 584 Km = 286.16 EUR
        );
    }
}

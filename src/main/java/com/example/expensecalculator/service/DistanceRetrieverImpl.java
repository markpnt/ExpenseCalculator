package com.example.expensecalculator.service;

import java.util.HashMap;
import java.util.Map;

public class DistanceRetrieverImpl implements DistanceRetriever{
    private static final Map<String, Integer> distances = new HashMap<>();

    static {
        distances.put("Berlin", 0);
        distances.put("Munich", 584);
        distances.put("Hamburg", 289);
        distances.put("Frankfurt", 545);
        distances.put("Cologne", 576);
    }

    @Override
    public int getDistance(String destination) {
        return distances.getOrDefault(destination, 0);
    }
}

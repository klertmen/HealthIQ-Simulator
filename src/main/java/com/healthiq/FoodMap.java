package com.healthiq;

import com.healthiq.model.Exercise;
import com.healthiq.model.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simulate database table for Food
 */
public final class FoodMap extends HashMap<Integer, Food> {
    private static final Map<Integer, Food> map = new HashMap<>();

    public static void initialize(String foodMap) {
        if (!map.isEmpty()) {
            return;
        }

        // open file
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(foodMap))) {
            //br returns as stream and convert it into a List
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.forEach(line -> {
            String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            // skip first line
            if (tokens[0].equals("ID")) {
                return;
            }
            Integer id = Integer.valueOf(tokens[0]);
            map.put(id, new Food(id, tokens[1], Integer.valueOf(tokens[2])));
        });
    }

    public static Food getFood(Integer id) {
        return map.get(id);
    }
}

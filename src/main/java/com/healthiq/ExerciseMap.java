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
 * Simulate database table for Exercise
 */
public final class ExerciseMap {
    private static final Map<Integer, Exercise> map = new HashMap<>();

    public static void initialize(String exerciseMap) {
        // don't initialize twice
        if (!map.isEmpty()) {
            return;
        }

        // open file
        List<String> list;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(exerciseMap))) {
            //br returns as stream and convert it into a List
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        list.forEach(line -> {
            String[] tokens = line.split(",");
            // skip first line
            if (tokens[0].equals("ID")) {
                return;
            }
            Integer id = Integer.valueOf(tokens[0]);
            map.put(id, new Exercise(id, tokens[1], Integer.valueOf(tokens[2])));
        });
    }

    public static Exercise getExercise(Integer id) {
        return map.get(id);
    }
}

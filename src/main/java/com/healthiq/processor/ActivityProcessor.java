package com.healthiq.processor;

import com.healthiq.ExerciseMap;
import com.healthiq.FoodMap;
import com.healthiq.model.Exercise;
import com.healthiq.model.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Process input file.
 */
public class ActivityProcessor {

    private static void populateDecayMap(Integer decayRate, OffsetTime timestamp, Map<OffsetTime, Double> returnMap,
                                         Double valuePerMinute) {
        for (int i = 1; i <= decayRate; i++) {
            Double existingValue = returnMap.get(timestamp.plusMinutes(i));
            returnMap.put(timestamp.plusMinutes(i),
                    existingValue != null ? existingValue + valuePerMinute : valuePerMinute);
        }
    }

    private static void processFood(OffsetTime timestamp, Integer id, Map<OffsetTime, Double> returnMap) {
        Food food = FoodMap.getFood(id);
        Integer foodValue = food.getGlycemicIndex();
        Double foodValuePerMinute = foodValue * 1.0 / food.getBloodSugarDecayRateInMinutes();
        populateDecayMap(food.getBloodSugarDecayRateInMinutes(), timestamp, returnMap, foodValuePerMinute);
    }

    private static void processExercise(OffsetTime timestamp, Integer id, Map<OffsetTime, Double> returnMap) {
        Exercise exercise = ExerciseMap.getExercise(id);
        Double exerciseValuePerMinute = -(exercise.getExerciseIndex() * 1.0 / exercise.getBloodSugarDecayRateInMinutes());
        populateDecayMap(exercise.getBloodSugarDecayRateInMinutes(), timestamp, returnMap, exerciseValuePerMinute);
    }

    public static Map<OffsetTime, Double> processInputFile(String filename) {
        List<String> inputLines;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            //br returns as stream and convert it into a List
            inputLines = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Map<OffsetTime, Double> returnMap = new TreeMap<>();
        inputLines.forEach(line -> {
            String[] tokens = line.split(",");
            // skip first line
            if (tokens[0].equals("Timestamp")) {
                return;
            }
            // timestamp,type,ID
            OffsetTime timestamp = OffsetTime.parse(tokens[0]);
            String type = tokens[1];
            Integer id = Integer.valueOf(tokens[2]);
            if (type.equals("F")) {
                processFood(timestamp, id, returnMap);
            } else if (type.equals("E")) {
                processExercise(timestamp, id, returnMap);
            }
        });
        return returnMap;
    }
}

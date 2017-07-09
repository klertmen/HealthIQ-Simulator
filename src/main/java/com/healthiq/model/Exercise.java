package com.healthiq.model;

/**
 * Represents Exercise object in database
 */
public class Exercise extends Activity {
    private Integer exerciseIndex;

    public Exercise(Integer id, String name, Integer exerciseIndex) {
        super(id, name);
        this.exerciseIndex = exerciseIndex;
    }

    public Integer getExerciseIndex() {
        return exerciseIndex;
    }

    @Override
    public Integer getBloodSugarDecayRateInMinutes() { return 60; }
}


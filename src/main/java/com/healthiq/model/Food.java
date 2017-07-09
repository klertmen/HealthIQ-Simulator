package com.healthiq.model;

/**
 * Represents Food object in database
 */
public class Food extends Activity {
    private Integer glycemicIndex;

    public Food(Integer id, String name, Integer glycemicIndex) {
        super(id, name);
        this.glycemicIndex = glycemicIndex;
    }

    public Integer getGlycemicIndex() {
        return glycemicIndex;
    }

    @Override
    public Integer getBloodSugarDecayRateInMinutes() { return 120; }
}

package com.healthiq.model;

/**
 * Activity types where there is a defined decay rate for blood sugar
 */
public abstract class Activity {
    private Integer id;
    private String name;

    Activity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // defines blood sugar decay rate
    abstract public Integer getBloodSugarDecayRateInMinutes();
}

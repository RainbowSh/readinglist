package com.example.domain.douban;

/**
 * Created by rainbow on 2017/1/19.
 */
public class Rating {
    private Integer max;

    private Integer numRaters;

    private float average;

    private Integer min;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(Integer numRaters) {
        this.numRaters = numRaters;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }
}

package com.reevel.testreevel.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Klemen on 18.8.2016.
 */

public class MovieRate {


    @SerializedName("value")
    @Expose
    private Double value;

    public MovieRate() {
    }

    public MovieRate(double v) {
        this.value = v;
    }

    /**
     * @return The value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(Double value) {
        this.value = value;
    }

}

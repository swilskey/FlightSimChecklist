package com.samwilskey.flightsimchecklist.model;

/**
 * Created by source41 on 5/21/2015.
 */
public class Developer {

    private String mName;
    private Aircraft[] mAircrafts;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Aircraft[] getAircrafts() {
        return mAircrafts;
    }

    public void setAircrafts(Aircraft[] aircrafts) {
        mAircrafts = aircrafts;
    }

    public void setAircraftModel(Aircraft aircraft, int position) {
        mAircrafts[position] = aircraft;
    }

    public Aircraft getAircraftModel(int position) {

        return mAircrafts[position];
    }
}

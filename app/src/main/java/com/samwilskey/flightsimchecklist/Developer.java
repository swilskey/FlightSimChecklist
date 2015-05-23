package com.samwilskey.flightsimchecklist;

/**
 * Created by source41 on 5/21/2015.
 */
public class Developer {

    private String mName;
    private AircraftModel[] mAircraftModels;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public AircraftModel[] getAircraftModels() {
        return mAircraftModels;
    }

    public void setAircraftModels(AircraftModel[] aircraftModels) {
        mAircraftModels = aircraftModels;
    }

    public void setAircraftModel(AircraftModel aircraftModel, int position) {
        mAircraftModels[position] = aircraftModel;
    }

    public AircraftModel getAircraftModel(int position) {

        return mAircraftModels[position];
    }
}

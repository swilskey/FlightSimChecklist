package com.samwilskey.flightsimchecklist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by source41 on 5/22/2015.
 */
public class AircraftModel implements Parcelable {

    private String mName;
    private String mChecklistFile;

    public AircraftModel() { }

    private AircraftModel(Parcel in) {
        mName = in.readString();
        mChecklistFile = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mChecklistFile);
    }

    public static final Creator<AircraftModel> CREATOR = new Creator<AircraftModel>() {

        @Override
        public AircraftModel createFromParcel(Parcel source) {
            return new AircraftModel(source);
        }

        @Override
        public AircraftModel[] newArray(int size) {
            return new AircraftModel[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getChecklistFile() {
        return mChecklistFile;
    }

    public void setChecklistFile(String checklistFile) {
        mChecklistFile = checklistFile;
    }
}

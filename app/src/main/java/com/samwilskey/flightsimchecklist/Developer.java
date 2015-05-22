package com.samwilskey.flightsimchecklist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by source41 on 5/21/2015.
 */
public class Developer implements Parcelable {
    private String mName;

    public Developer() { }

    private Developer(Parcel in) {
        mName = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
    }

    public static final Creator<Developer> CREATOR = new Creator<Developer>() {
        @Override
        public Developer createFromParcel(Parcel source) {
            return new Developer(source);
        }

        @Override
        public Developer[] newArray(int size) {
            return new Developer[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

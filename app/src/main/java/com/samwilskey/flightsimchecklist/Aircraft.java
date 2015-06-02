package com.samwilskey.flightsimchecklist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by source41 on 5/22/2015.
 */
public class Aircraft implements Parcelable {

    private String mName;
    private Checklist[] mChecklists;

    public Aircraft() { }

    private Aircraft(Parcel in) {
        mName = in.readString();
        mChecklists = in.createTypedArray(Checklist.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeTypedArray(mChecklists, flags);
    }

    public static final Creator<Aircraft> CREATOR = new Creator<Aircraft>() {

        @Override
        public Aircraft createFromParcel(Parcel source) {
            return new Aircraft(source);
        }

        @Override
        public Aircraft[] newArray(int size) {
            return new Aircraft[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Checklist[] getChecklists() {
        return mChecklists;
    }

    public void setChecklists(Checklist[] checklists) {
        mChecklists = checklists;
    }
}

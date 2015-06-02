package com.samwilskey.flightsimchecklist.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by source41 on 5/22/2015.
 */
public class Aircraft implements Parcelable {

    private String mName;
    private ArrayList<String> mKeys;
    private Map<String, Checklist> mChecklistMap;

    public Aircraft() { }

    private Aircraft(Parcel in) {
        mChecklistMap = new ArrayMap<>();
        mName = in.readString();
        mKeys = in.createStringArrayList();
        int size = in.readInt();
        for(int i = 0; i < size; i++) {
            String key = in.readString();
            Checklist value = in.readParcelable(Checklist.class.getClassLoader());
            mChecklistMap.put(key,value);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeStringList(mKeys);
        dest.writeInt(mChecklistMap.size());
        for(Map.Entry<String, Checklist> entry : mChecklistMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(),flags);
        }
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

    public Map<String, Checklist> getChecklistMap() {
        return mChecklistMap;
    }

    public void setChecklistMap(Map<String, Checklist> checklistMap) {
        mChecklistMap = checklistMap;
    }

    public ArrayList<String> getKeys() {
        return mKeys;
    }

    public void setKeys(ArrayList<String> keys) {
        mKeys = keys;
    }
}

package com.samwilskey.flightsimchecklist;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by source41 on 5/31/2015.
 */
public class Checklist implements Parcelable{

    private static final String TAG = Checklist.class.getSimpleName();

    private String mName;
    private String mFile;
    private String[] mSections;
    private Map<String, String[]> mChecklistItems;

    public Checklist() {
        mChecklistItems = new ArrayMap<String, String[]>();
    }

    public Checklist(Parcel in) {
        mName = in.readString();
        mFile = in.readString();
        mSections = in.createStringArray();

        int size = in.readInt();
        mChecklistItems = new ArrayMap<>();
        for(int i = 0; i < size; i++) {
            String key = in.readString();
            String[] value = in.createStringArray();
            mChecklistItems.put(key,value);
        }
    }

    public String[] getSections() {
        return mSections;
    }

    public void setSections(String[] sections) {
        mSections = sections;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFile() {
        return mFile;
    }

    public void setFile(String file) {
        mFile = file;
    }

    public Map<String, String[]> getChecklistItems() {
        return mChecklistItems;
    }

    public void setChecklistItems(Map<String, String[]> checklistItems) {
        mChecklistItems = checklistItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mFile);
        dest.writeStringArray(mSections);
        dest.writeInt(mChecklistItems.size());
        for(Map.Entry<String, String[]> entry : mChecklistItems.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeStringArray(entry.getValue());
        }
    }

    public static final Creator<Checklist> CREATOR = new Creator<Checklist>() {

        @Override
        public Checklist createFromParcel(Parcel source) {
            return new Checklist(source);
        }

        @Override
        public Checklist[] newArray(int size) {
            return new Checklist[size];
        }
    };


}

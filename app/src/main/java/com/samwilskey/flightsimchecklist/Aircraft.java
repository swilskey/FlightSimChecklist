package com.samwilskey.flightsimchecklist;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by source41 on 5/22/2015.
 */
public class Aircraft implements Parcelable {

    private String mName;
    private String mChecklistFile;
    private ArrayList<String> mChecklistFiles;

    public Aircraft() { }

    private Aircraft(Parcel in) {
        mName = in.readString();
        //mChecklistFile = in.readString();
        mChecklistFiles = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        //dest.writeString(mChecklistFile);
        dest.writeStringList(mChecklistFiles);
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

    public String getChecklistFile() {
        return mChecklistFile;
    }

    public void setChecklistFile(String checklistFile) {
        mChecklistFile = checklistFile;
    }

    public ArrayList<String> getChecklistFiles() {
        return mChecklistFiles;
    }

    public void setChecklistFiles(JSONArray checklistFiles) throws JSONException {
        mChecklistFiles = new ArrayList<String>();
        if (checklistFiles != null) {
            for (int i = 0; i < checklistFiles.length(); i++) {
                JSONObject obj = checklistFiles.getJSONObject(i);
                mChecklistFiles.add(checklistFiles.getString(i));
            }
        }
    }
}
package com.samwilskey.flightsimchecklist;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by source41 on 5/31/2015.
 */
public class JsonHelper {

    private Context mContext;

    public JsonHelper(Context context) {
        mContext = context;
    }

    public String loadJSONFromAsset(String fileName) throws IOException {
        String json = null;
        InputStream inputStream = mContext.getAssets().open(fileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        json = new String(buffer, "UTF-8");

        return json;
    }

    public Developer[] parseDeveloperDetails(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray devs = jsonObject.getJSONArray("developers");
        Developer[] developers = new Developer[devs.length()];
        for(int i = 0; i < devs.length(); i++) {

            JSONObject dev = devs.getJSONObject(i);
            Developer developer = new Developer();
            developer.setName(dev.getString("name"));
            developer.setAircrafts(getAircraftDetails(dev.getJSONArray("models")));
            developers[i] = developer;
        }

        return developers;
    }

    private Aircraft[] getAircraftDetails(JSONArray jsonObject) throws JSONException{

        Aircraft[] aircrafts = new Aircraft[jsonObject.length()];

        for(int j = 0; j < jsonObject.length(); j++) {

            JSONObject jsonModel = jsonObject.getJSONObject(j);
            JSONArray jsonArray = jsonModel.getJSONArray("files");

            Aircraft newAircraft = new Aircraft();
            Checklist[] checklists = new Checklist[jsonArray.length()];
            newAircraft.setName(jsonModel.getString("model"));

            for(int i = 0; i < jsonArray.length(); i++) {
                Checklist checklist = new Checklist();
                checklist.setFile(jsonArray.getString(i));
                checklists[i] = checklist;
            }
            newAircraft.setChecklists(checklists);
            aircrafts[j] = newAircraft;
        }

        return aircrafts;
    }

    public Checklist parseChecklistSections(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray sections = jsonObject.getJSONArray("sections");
        Checklist checklist = new Checklist();
        String[] checklistSections = new String[sections.length()];

        for(int i = 0; i < sections.length(); i++) {
            checklistSections[i] = sections.getString(i);
        }

        checklist.setSections(checklistSections);

        return checklist;
    }

    public Checklist parseChecklistItems(String jsonData, Checklist checklist) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        Map<String, String[]> checklistItems = new ArrayMap<String, String[]>();

        for(int i = 0; i < checklist.getSections().length; i++) {

            String key = checklist.getSections()[i];
            JSONArray items = jsonObject.getJSONArray(key);
            String[] array = new String[items.length()];
            for(int j = 0; j < items.length(); j++) {
                array[j] = items.getString(j);
            }
            checklistItems.put(key, array);
        }

        checklist.setChecklistItems(checklistItems);

        return checklist;
    }
}

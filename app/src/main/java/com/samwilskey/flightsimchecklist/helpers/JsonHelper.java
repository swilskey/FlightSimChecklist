package com.samwilskey.flightsimchecklist.helpers;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.samwilskey.flightsimchecklist.model.Aircraft;
import com.samwilskey.flightsimchecklist.model.Checklist;
import com.samwilskey.flightsimchecklist.model.Developer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
            developer.setAircrafts(parseAircraftDetails(dev.getJSONArray("models")));
            developers[i] = developer;
        }

        return developers;
    }

    private Aircraft[] parseAircraftDetails(JSONArray jsonObject) throws JSONException{

        Aircraft[] aircrafts = new Aircraft[jsonObject.length()];

        for(int j = 0; j < jsonObject.length(); j++) {

            JSONObject jsonAircraft = jsonObject.getJSONObject(j);

            Aircraft newAircraft = new Aircraft();
            newAircraft.setName(jsonAircraft.getString("model"));

            Map<String, Checklist> checklistMap = new ArrayMap<String, Checklist>();

            JSONObject jsonChecklists = jsonAircraft.getJSONObject("checklists");
            Iterator checklists = jsonChecklists.keys();
            ArrayList<String> keys = new ArrayList<>();
            while(checklists.hasNext()) {
                String key = (String) checklists.next();
                String value = jsonChecklists.getString(key);
                Checklist checklist = new Checklist();
                checklist.setFile(value);
                checklist.setName(key);
                checklist.setSectionFile(jsonAircraft.getString("sections"));
                keys.add(key);
                checklistMap.put(key, checklist);
            }

            newAircraft.setKeys(keys);
            newAircraft.setChecklistMap(checklistMap);
            aircrafts[j] = newAircraft;
        }

        return aircrafts;
    }

    public String[] parseChecklistSections(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        Iterator keys = jsonObject.keys();
        String [] checklistSections = null;
        while(keys.hasNext()) {
            String key = (String) keys.next();
            JSONArray sections = jsonObject.getJSONArray(key);
            checklistSections = new String[sections.length()];
            for(int i = 0; i < sections.length(); i++) {
                checklistSections[i] = sections.getString(i);
            }
        }

        return checklistSections;
    }
/*
    public Checklist parseChecklistItems(String jsonData, Checklist checklist) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        Map<String, String[]> checklistItems = new ArrayMap<String, String[]>();

        for(int i = 0; i < checklist.g; i++) {

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
*/
}

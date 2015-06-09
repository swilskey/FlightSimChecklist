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

    public static final String TAG = JsonHelper.class.getSimpleName();

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

    public String[] parseChecklistSections(String jsonData, String key) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        String[] checklistSections = new String[jsonArray.length()];

        for(int i = 0; i < jsonArray.length(); i++)
            checklistSections[i] = jsonArray.getString(i);

        return checklistSections;
    }
    public Map<String, String[]> parseChecklistItems(String jsonData, Checklist checklist) throws JSONException {
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

        return checklistItems;
    }

}

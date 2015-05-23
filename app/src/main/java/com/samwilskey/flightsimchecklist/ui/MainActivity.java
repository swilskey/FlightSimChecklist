package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.AircraftModel;
import com.samwilskey.flightsimchecklist.Developer;
import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.DeveloperAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    Developer[] mDevelopers;
    String mJSONVersion;

    @InjectView(android.R.id.list) ExpandableListView mListView;
    @InjectView(android.R.id.empty) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        try {
            parseDeveloperDetails(loadJSONFromAsset());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        DeveloperAdapter devAdapter = new DeveloperAdapter(this, mDevelopers);
        mListView.setAdapter(devAdapter);
        mListView.setEmptyView(mEmptyTextView);

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return true;
            }
        });

    }

    private String loadJSONFromAsset() throws IOException {
        String json = null;
        InputStream inputStream = getAssets().open("developers.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        json = new String(buffer, "UTF-8");

        return json;
    }

    private void parseDeveloperDetails(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        mJSONVersion = jsonObject.getString("version");

        JSONArray devs = jsonObject.getJSONArray("developers");
        mDevelopers = new Developer[devs.length()];


        for(int i = 0; i < devs.length(); i++) {
            JSONObject dev = devs.getJSONObject(i);
            Developer developer = new Developer();
            developer.setName(dev.getString("name"));
            developer.setAircraftModels(getModelDetails(dev.getJSONArray("models")));
            mDevelopers[i] = developer;
        }
    }

    private AircraftModel[] getModelDetails(JSONArray jsonObject) throws JSONException{
        AircraftModel[] aircraftModels = new AircraftModel[jsonObject.length()];

        for(int j = 0; j < jsonObject.length(); j++) {
            JSONObject jsonModel = jsonObject.getJSONObject(j);
            AircraftModel newAircraft = new AircraftModel();
            newAircraft.setName(jsonModel.getString("model"));
            newAircraft.setChecklistFile(jsonModel.getString("filename"));
            aircraftModels[j] = newAircraft;
        }
        return aircraftModels;
    }
}

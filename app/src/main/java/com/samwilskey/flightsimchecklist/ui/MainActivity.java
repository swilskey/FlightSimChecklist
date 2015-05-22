package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    @InjectView(android.R.id.list) ListView mListView;
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You clicked " + mDevelopers[position].getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private String loadJSONFromAsset() throws IOException {
        String json = null;
        InputStream inputStream = getAssets().open("airplanes.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        json = new String(buffer, "UTF-8");

        return json;
    }

    private void parseDeveloperDetails(String jsonData) throws JSONException {
        JSONObject object = new JSONObject(jsonData);
        JSONArray devs = object.getJSONArray("developers");
        mDevelopers = new Developer[devs.length()];

        for(int i = 0; i < devs.length(); i++) {
            JSONObject jsonObject = devs.getJSONObject(i);
            Developer developer = new Developer();
            developer.setName(jsonObject.getString("name"));
            mDevelopers[i] = developer;
        }
    }
}

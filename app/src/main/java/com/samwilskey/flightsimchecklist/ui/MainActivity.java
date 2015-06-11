package com.samwilskey.flightsimchecklist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.DeveloperAdapter;
import com.samwilskey.flightsimchecklist.helpers.JsonHelper;
import com.samwilskey.flightsimchecklist.model.Developer;

import org.json.JSONException;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Developer[] mDevelopers;
    private JsonHelper mJsonHelper;
    private Toolbar mToolbar;
    private android.support.v7.app.ActionBar abMain = null;

    @InjectView(android.R.id.list) ExpandableListView mListView;
    @InjectView(android.R.id.empty) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        abMain = getSupportActionBar();
        abMain.setHomeButtonEnabled(true);

        mJsonHelper = new JsonHelper(this);
        try {
            mDevelopers = mJsonHelper.parseDeveloperDetails(mJsonHelper.loadJSONFromAsset("developers.json"));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        DeveloperAdapter devAdapter = new DeveloperAdapter(this, mDevelopers);
        mListView.setAdapter(devAdapter);
        mListView.setEmptyView(mEmptyTextView);

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(MainActivity.this, ChecklistSelectActivity.class);
                intent.putExtra("aircraft", mDevelopers[groupPosition].getAircraftModel(childPosition));
                startActivity(intent);
                return true;
            }
        });

    }
}

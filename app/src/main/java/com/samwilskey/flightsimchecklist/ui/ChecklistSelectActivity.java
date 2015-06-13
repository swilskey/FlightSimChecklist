package com.samwilskey.flightsimchecklist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.helpers.JsonHelper;
import com.samwilskey.flightsimchecklist.model.Aircraft;
import com.samwilskey.flightsimchecklist.model.Checklist;

import org.json.JSONException;

import java.io.IOException;

public class ChecklistSelectActivity extends AppCompatActivity implements ChecklistSelectFragment.OnListItemSelectedListener {

    private Aircraft mAircraft;
    private JsonHelper mJsonHelper;
    private Toolbar mToolbar;
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_select);

        Intent intent = getIntent();
        mAircraft = intent.getParcelableExtra("aircraft");

        mJsonHelper = new JsonHelper(this);

        try {
            for (String key : mAircraft.getKeys()) {
                Checklist checklist = mAircraft.getChecklistMap().get(key);
                String fileName = checklist.getSectionFile();

                String sectionData = mJsonHelper.loadJSONFromAsset(fileName);
                checklist.setSections(mJsonHelper.parseChecklistSections(sectionData, key));

                String checklistFile = checklist.getFile();
                String checklistData = mJsonHelper.loadJSONFromAsset(checklistFile);
                checklist.setChecklistItems(mJsonHelper.parseChecklistItems(checklistData, checklist));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        determinePaneLayout();

    }

    @Override
    public void onItemSelected(Checklist checklist, String section, int index) {
        if (isTwoPane) {
            ChecklistFragment fragment = ChecklistFragment.newInstance(checklist, section, index);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragment);
            ft.commit();

        } else {
            Intent i = new Intent(this, ChecklistActivity.class);
            i.putExtra("checklist", checklist);
            i.putExtra("section", section);
            i.putExtra("index", index);
            startActivity(i);
        }
    }

    private void determinePaneLayout() {
        FrameLayout fragmentChecklist = (FrameLayout) findViewById(R.id.flDetailContainer);

        if (fragmentChecklist != null) {
            isTwoPane = true;
            ChecklistSelectFragment fragment = (ChecklistSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);

        }
    }

    public Aircraft getAircraft() {
        return mAircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        mAircraft = aircraft;
    }
}

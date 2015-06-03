package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistSelectAdapter;
import com.samwilskey.flightsimchecklist.helpers.JsonHelper;
import com.samwilskey.flightsimchecklist.model.Aircraft;
import com.samwilskey.flightsimchecklist.model.Checklist;

import org.json.JSONException;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistSelectActivity extends Activity {

    public static final String TAG = ChecklistSelectActivity.class.getSimpleName();

    private Aircraft mAircraft;
    private JsonHelper mJsonHelper;

    @InjectView(android.R.id.list)
    ExpandableListView mListView;
    @InjectView(android.R.id.empty)
    TextView mEmptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_select);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mAircraft = intent.getParcelableExtra("aircraft");

        mJsonHelper = new JsonHelper(this);

        try {
            for(int i = 0; i < mAircraft.getChecklistMap().size(); i++) {
                String key = mAircraft.getKeys().get(i);
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

        ChecklistSelectAdapter adapter = new ChecklistSelectAdapter(this, mAircraft);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyList);

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String key = mAircraft.getKeys().get(groupPosition);

                Checklist checklist = mAircraft.getChecklistMap().get(key);

                Intent intent = new Intent(ChecklistSelectActivity.this, ChecklistActivity.class);
                intent.putExtra("checklist", checklist);
                intent.putExtra("section", childPosition);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
        return true;
    }

}

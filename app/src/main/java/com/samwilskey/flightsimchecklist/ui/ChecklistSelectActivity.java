package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.Aircraft;
import com.samwilskey.flightsimchecklist.Checklist;
import com.samwilskey.flightsimchecklist.JsonHelper;
import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistSelectAdapter;

import org.json.JSONException;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistSelectActivity extends Activity {

    public static final String TAG = ChecklistSelectActivity.class.getSimpleName();

    private Aircraft mAircraft;
    private Checklist mChecklist;
    private JsonHelper mJsonHelper;

    @InjectView(android.R.id.list)
    ListView mListView;
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
            mChecklist = mJsonHelper.parseChecklistSections(
                    mJsonHelper.loadJSONFromAsset(mAircraft.getChecklists()[0].getFile()));
            mChecklist = mJsonHelper.parseChecklistItems(
                    mJsonHelper.loadJSONFromAsset(mAircraft.getChecklists()[2].getFile()),mChecklist);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        mAircraft.getChecklists()[2] = mChecklist;

        ChecklistSelectAdapter adapter = new ChecklistSelectAdapter(this, mChecklist);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyList);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChecklistSelectActivity.this, ChecklistActivity.class);
                intent.putExtra("checklist", mChecklist);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
        return true;
    }

}

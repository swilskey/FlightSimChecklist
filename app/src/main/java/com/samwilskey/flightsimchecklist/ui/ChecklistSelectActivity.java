package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistSelectAdapter;
import com.samwilskey.flightsimchecklist.helpers.JsonHelper;
import com.samwilskey.flightsimchecklist.model.Aircraft;

import org.json.JSONException;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistSelectActivity extends Activity {

    public static final String TAG = ChecklistSelectActivity.class.getSimpleName();

    private Aircraft mAircraft;
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
            for(int i = 0; i < mAircraft.getChecklistMap().size(); i++) {
                String key = mAircraft.getKeys().get(i);
                String fileName = mAircraft.getChecklistMap().get(key).getSectionFile();
                String jsonData = mJsonHelper.loadJSONFromAsset(fileName);
                mAircraft.getChecklistMap().get(key).setSections(mJsonHelper.parseChecklistSections(jsonData, key));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        ChecklistSelectAdapter adapter = new ChecklistSelectAdapter(this, mAircraft);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyList);

/*
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChecklistSelectActivity.this, ChecklistActivity.class);
                intent.putExtra("checklist", mChecklist);
                startActivity(intent);
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
        return true;
    }

}

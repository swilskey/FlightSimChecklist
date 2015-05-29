package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.samwilskey.flightsimchecklist.Aircraft;
import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistSelectActivity extends Activity {

    public static final String TAG = ChecklistSelectActivity.class.getSimpleName();

    private Aircraft mAircraft;

    @InjectView(android.R.id.list) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_select);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mAircraft = intent.getParcelableExtra("aircraft");

        ChecklistAdapter adapter = new ChecklistAdapter(this, mAircraft);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChecklistSelectActivity.this, ChecklistActivity.class);
                intent.putExtra("aircraft", mAircraft);
                startActivity(intent);
            }
        });

    }

}

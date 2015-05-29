package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.samwilskey.flightsimchecklist.AircraftModel;
import com.samwilskey.flightsimchecklist.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistActivity extends Activity {

    public static final String TAG = ChecklistActivity.class.getSimpleName();

    private AircraftModel mAircraft;

    @InjectView(android.R.id.list) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mAircraft = intent.getParcelableExtra("aircraft");

    }

}

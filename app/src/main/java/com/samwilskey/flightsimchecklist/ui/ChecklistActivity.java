package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.samwilskey.flightsimchecklist.model.Checklist;
import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistActivity extends Activity {

    private Checklist mChecklist;

    @InjectView(android.R.id.list)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mChecklist = intent.getParcelableExtra("checklist");

        ChecklistAdapter adapter = new ChecklistAdapter(this, mChecklist, "Pre-Flight");
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
        return true;
    }
}

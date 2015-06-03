package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistAdapter;
import com.samwilskey.flightsimchecklist.model.Checklist;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistActivity extends Activity {

    private Checklist mChecklist;
    private String mSection;

    @InjectView(android.R.id.list)
    ListView mListView;
    @InjectView(android.R.id.empty)
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mChecklist = intent.getParcelableExtra("checklist");
        mSection = mChecklist.getSections()[intent.getIntExtra("section", 0)];

        //mEmptyTextView.setText(mChecklist.getSections()[mSection]);


        ChecklistAdapter adapter = new ChecklistAdapter(this, mChecklist, mSection);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
        return true;
    }
}

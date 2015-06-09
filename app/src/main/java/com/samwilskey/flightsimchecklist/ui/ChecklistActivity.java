package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistAdapter;
import com.samwilskey.flightsimchecklist.model.Checklist;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChecklistActivity extends Activity {

    private Checklist mChecklist;
    private String mSection;
    int mIndex;
    private ChecklistAdapter mAdapter;

    @InjectView(android.R.id.list)
    ListView mListView;
    @InjectView(android.R.id.empty)
    TextView mEmptyTextView;
    @InjectView(R.id.nextButton)
    Button mNextButton;
    @InjectView(R.id.resetButton)
    Button mLastButton;
    @InjectView(R.id.checklistName)
    TextView mChecklistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mChecklist = intent.getParcelableExtra("checklist");
        mIndex = intent.getIntExtra("section", 0);
        mSection = mChecklist.getSections()[mIndex];

        mChecklist.setIsChecked(new int[mChecklist.getChecklistItems().get(mSection).length]);
        for(int i = 0; i < mChecklist.getIsChecked().length; i++) {
            mChecklist.getIsChecked()[i] = 0;
        }

        mLastButton.setOnClickListener(buttonClick);
        mNextButton.setOnClickListener(buttonClick);
        mChecklistName.setText(mSection);

        mAdapter = new ChecklistAdapter(this, mChecklist, mSection);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyTextView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mChecklist.getIsChecked()[position] == 0) {
                    view.setBackgroundResource(0);
                    mChecklist.getIsChecked()[position] = 1;
                }

            }
        });
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.resetButton: {
                    for(int i = 0; i < mChecklist.getIsChecked().length; i++) {
                        mChecklist.getIsChecked()[i] = 0;
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                }
                case R.id.nextButton: {
                    if(mIndex + 1 < mChecklist.getSections().length) {
                        for(int i = 0; i < mChecklist.getIsChecked().length; i++) {
                            mChecklist.getIsChecked()[i] = 0;
                        }
                        mIndex += 1;
                        mSection = mChecklist.getSections()[mIndex];

                        mChecklist.setIsChecked(new int[mChecklist.getChecklistItems().get(mSection).length]);
                        for(int i = 0; i < mChecklist.getIsChecked().length; i++) {
                            mChecklist.getIsChecked()[i] = 0;
                        }
                        mChecklistName.setText(mSection);

                        mAdapter.setSection(mSection);

                        mAdapter.notifyDataSetChanged();

                        //Intent intent = new Intent(ChecklistActivity.this, ChecklistActivity.class);
                        //intent.putExtra("checklist", mChecklist);
                        //intent.putExtra("section", mIndex + 1);
                        //startActivity(intent);
                    } else {
                        Toast.makeText(ChecklistActivity.this, "Last Checklist", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        }
    };

}

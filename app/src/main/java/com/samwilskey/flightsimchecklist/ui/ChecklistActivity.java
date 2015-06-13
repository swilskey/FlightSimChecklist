package com.samwilskey.flightsimchecklist.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.model.Checklist;

/**
 * Created by source41 on 6/12/2015.
 */
public class ChecklistActivity extends AppCompatActivity {

    private ChecklistFragment mFragment;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Checklist checklist = getIntent().getParcelableExtra("checklist");
        String section = getIntent().getStringExtra("section");
        int index = getIntent().getIntExtra("index", 0);


        if(savedInstanceState == null) {
            mFragment = ChecklistFragment.newInstance(checklist, section, index);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer,mFragment);
            ft.commit();
        }
    }
}

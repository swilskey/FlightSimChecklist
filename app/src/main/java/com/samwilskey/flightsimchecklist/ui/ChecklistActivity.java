package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.samwilskey.flightsimchecklist.Developer;
import com.samwilskey.flightsimchecklist.R;

public class ChecklistActivity extends Activity {
    Developer mDeveloper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra("DEVELOPER");

    }

}

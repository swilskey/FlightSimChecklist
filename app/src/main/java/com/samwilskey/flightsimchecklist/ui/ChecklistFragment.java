package com.samwilskey.flightsimchecklist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistAdapter;
import com.samwilskey.flightsimchecklist.model.Checklist;

/**
 * Created by source41 on 6/12/2015.
 */
public class ChecklistFragment extends Fragment implements View.OnClickListener {

    private Checklist mChecklist;
    private String mSection;
    private int mIndex;
    private ChecklistAdapter mAdapter;
    private TextView mChecklistName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChecklist = getArguments().getParcelable("checklist");
        mSection = getArguments().getString("section");
        mIndex = getArguments().getInt("index");

        mChecklist.setIsChecked(new int[mChecklist.getChecklistItems().get(mSection).length]);
        for(int i = 0; i < mChecklist.getIsChecked().length; i++) {
            mChecklist.getIsChecked()[i] = 0;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checklist,
                container, false);


        ListView listView = (ListView) view.findViewById(android.R.id.list);
        TextView textView = (TextView) view.findViewById(android.R.id.empty);
        mChecklistName = (TextView) view.findViewById(R.id.checklistName);
        Button reset = (Button) view.findViewById(R.id.resetButton);
        Button next = (Button) view.findViewById(R.id.nextButton);
        if(next != null)
            next.setOnClickListener(this);

        reset.setOnClickListener(this);

        mChecklistName.setText(mSection);

        mAdapter = new ChecklistAdapter(getActivity(), mChecklist, mSection);

        listView.setAdapter(mAdapter);
        listView.setEmptyView(textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mChecklist.getIsChecked()[position] == 0) {
                    view.setBackgroundResource(0);
                    mChecklist.getIsChecked()[position] = 1;
                } else {
                    view.setBackgroundResource(R.drawable.bg_not_checked);
                    mChecklist.getIsChecked()[position] = 0;
                }
            }
        });

        return view;
    }

    public static ChecklistFragment newInstance(Checklist checklist, String section, int index) {
        ChecklistFragment fragment = new ChecklistFragment();
        Bundle args = new Bundle();
        args.putParcelable("checklist", checklist);
        args.putString("section", section);
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextButton:
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

            } else {
                Toast.makeText(getActivity(), "Last Checklist", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.resetButton:
                for(int i = 0; i < mChecklist.getIsChecked().length; i++) {
                    mChecklist.getIsChecked()[i] = 0;
                    mAdapter.notifyDataSetChanged();

                }
                break;
        }
    }
}

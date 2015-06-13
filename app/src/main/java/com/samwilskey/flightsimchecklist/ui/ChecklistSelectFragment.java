package com.samwilskey.flightsimchecklist.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.adapters.ChecklistSelectAdapter;
import com.samwilskey.flightsimchecklist.model.Aircraft;
import com.samwilskey.flightsimchecklist.model.Checklist;


/**
 * Created by source41 on 6/12/2015.
 */
public class ChecklistSelectFragment extends Fragment {

    private ExpandableListView mListView;
    private TextView mEmptyList;
    private Aircraft mAircraft;

    private OnListItemSelectedListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mAircraft = bundle.getParcelable("aircraft");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_list,
                container, false);

        mListView = (ExpandableListView) view.findViewById(android.R.id.list);
        mEmptyList = (TextView) view.findViewById(android.R.id.empty);

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String key = mAircraft.getKeys().get(groupPosition);

                Checklist checklist = mAircraft.getChecklistMap().get(key);

                listener.onItemSelected(checklist, checklist.getSections()[childPosition], childPosition);
                return true;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListItemSelectedListener) {
            listener = (OnListItemSelectedListener) activity;
        } else {
            throw new ClassCastException(
                    activity.toString()
                            + " must implement ItemsListFragment.OnListItemSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mAircraft = bundle.getParcelable("aircraft");

        } else {
            ChecklistSelectActivity activity = (ChecklistSelectActivity) getActivity();
            mAircraft = activity.getAircraft();
        }
        mListView.setAdapter(new ChecklistSelectAdapter(getActivity(),mAircraft));
        mListView.setEmptyView(mEmptyList);
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        mListView.setChoiceMode(activateOnItemClick ? ExpandableListView.CHOICE_MODE_SINGLE : ExpandableListView.CHOICE_MODE_NONE);
    }

    public interface OnListItemSelectedListener {
        public void onItemSelected(Checklist checklist, String section, int index);
    }

}

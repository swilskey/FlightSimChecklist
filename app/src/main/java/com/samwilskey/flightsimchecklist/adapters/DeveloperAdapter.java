package com.samwilskey.flightsimchecklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.AircraftModel;
import com.samwilskey.flightsimchecklist.Developer;
import com.samwilskey.flightsimchecklist.R;

/**
 * Created by source41 on 5/21/2015.
 */
public class DeveloperAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private Developer[] mDevelopers;

    public DeveloperAdapter(Context context, Developer[] developers) {
        mContext = context;
        mDevelopers = developers;
    }

    @Override
    public int getGroupCount() {
        return mDevelopers.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDevelopers[groupPosition].getAircraftModels().length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDevelopers[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        AircraftModel[] child = mDevelopers[groupPosition].getAircraftModels();
        return child[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dev_list_item, null);
            holder = new ViewHolder();
            holder.devLabel = (TextView) convertView.findViewById(R.id.devLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Developer developer = mDevelopers[groupPosition];

        holder.devLabel.setText(developer.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //String aircraft = (String) getChild(groupPosition, childPosition);
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dev_child_list_item, null);
            holder = new ViewHolder();
            holder.aircraftLabel = (TextView) convertView.findViewById(R.id.aircraftLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Developer developer = mDevelopers[groupPosition];


        holder.aircraftLabel.setText(developer.getAircraftModel(childPosition).getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder {
        TextView devLabel;
        TextView aircraftLabel;
    }
}

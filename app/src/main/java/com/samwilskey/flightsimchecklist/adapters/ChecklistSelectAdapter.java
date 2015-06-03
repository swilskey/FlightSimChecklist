package com.samwilskey.flightsimchecklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.model.Aircraft;

/**
 * Created by source41 on 5/29/2015.
 */
public class ChecklistSelectAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private Aircraft mAircraft;

    public ChecklistSelectAdapter(Context context, Aircraft aircraft) {
        mContext = context;
        mAircraft = aircraft;
    }

    @Override
    public int getGroupCount() {
        return mAircraft.getChecklistMap().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = mAircraft.getKeys().get(groupPosition);
        return mAircraft.getChecklistMap().get(key).getSections().length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        String key = mAircraft.getKeys().get(groupPosition);
        return mAircraft.getChecklistMap().get(key);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = mAircraft.getKeys().get(groupPosition);
        String[] sections = mAircraft.getChecklistMap().get(key).getSections();
        return sections[childPosition];
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
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
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

        holder.devLabel.setText(mAircraft.getChecklistMap().get(
                mAircraft.getKeys().get(groupPosition)).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
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

        String key = mAircraft.getKeys().get(groupPosition);
        holder.aircraftLabel.setText(mAircraft.getChecklistMap().get(key).getSections()[childPosition]);

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

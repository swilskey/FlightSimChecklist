package com.samwilskey.flightsimchecklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.Aircraft;
import com.samwilskey.flightsimchecklist.Checklist;
import com.samwilskey.flightsimchecklist.R;

/**
 * Created by source41 on 5/29/2015.
 */
public class ChecklistSelectAdapter extends BaseAdapter {

    private Context mContext;
    private Aircraft mAircraft;
    private Checklist mChecklist;

    public ChecklistSelectAdapter(Context context, Aircraft aircraft) {
        mContext = context;
        mAircraft = aircraft;
    }

    public ChecklistSelectAdapter(Context context, Checklist checklist) {
        mContext = context;
        mChecklist = checklist;
    }


    @Override
    public int getCount() {
        if(mAircraft == null) {
            return mChecklist.getSections().length;
        }
        return mAircraft.getChecklists().length;
    }

    @Override
    public Object getItem(int position) {
        if(mAircraft == null) {
            return mChecklist.getSections()[position];
        }
        return mAircraft.getChecklists()[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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

        if(mAircraft == null) {
            holder.devLabel.setText(mChecklist.getSections()[position]);
        }
        else {
            holder.devLabel.setText(mAircraft.getChecklists()[position].getName());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView devLabel;
    }
}

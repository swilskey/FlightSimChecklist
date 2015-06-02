package com.samwilskey.flightsimchecklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.R;
import com.samwilskey.flightsimchecklist.model.Aircraft;

/**
 * Created by source41 on 5/29/2015.
 */
public class ChecklistSelectAdapter extends BaseAdapter {

    private Context mContext;
    private Aircraft mAircraft;

    public ChecklistSelectAdapter(Context context, Aircraft aircraft) {
        mContext = context;
        mAircraft = aircraft;
    }

    @Override
    public int getCount() {
        return mAircraft.getChecklistMap().size();
    }

    @Override
    public Object getItem(int position) {
        return mAircraft.getChecklistMap().get(mAircraft.getChecklistMap().get(mAircraft.getKeys().get(position)));
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

        holder.devLabel.setText(mAircraft.getChecklistMap().get(mAircraft.getKeys().get(position)).getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView devLabel;
    }
}

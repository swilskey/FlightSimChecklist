package com.samwilskey.flightsimchecklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.Developer;
import com.samwilskey.flightsimchecklist.R;

/**
 * Created by source41 on 5/21/2015.
 */
public class DeveloperAdapter extends BaseAdapter {

    private Context mContext;
    private Developer[] mDevelopers;

    public DeveloperAdapter(Context context, Developer[] developers) {
        mContext = context;
        mDevelopers = developers;
    }

    @Override
    public int getCount() {
        return mDevelopers.length;
    }

    @Override
    public Object getItem(int position) {
        return mDevelopers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.airplane_list_item, null);
            holder = new ViewHolder();
            holder.devLabel = (TextView) convertView.findViewById(R.id.devLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Developer developer = mDevelopers[position];

        holder.devLabel.setText(developer.getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView devLabel;
    }
}

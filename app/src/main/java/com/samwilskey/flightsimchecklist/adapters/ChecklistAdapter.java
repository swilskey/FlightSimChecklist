package com.samwilskey.flightsimchecklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samwilskey.flightsimchecklist.model.Checklist;
import com.samwilskey.flightsimchecklist.R;

/**
 * Created by source41 on 6/1/2015.
 */
public class ChecklistAdapter extends BaseAdapter {

    private Context mContext;
    private Checklist mChecklist;
    private int mSection;
    public ChecklistAdapter(Context context, Checklist checklist, String section) {
        mContext = context;
        mChecklist = checklist;
        //mSection = section;
    }


    @Override
    public int getCount() {
        return mChecklist.getChecklistItems().get(mSection).length;
    }

    @Override
    public Object getItem(int position) {
        return mChecklist.getChecklistItems().get(mSection)[position];
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

        holder.devLabel.setText(mChecklist.getChecklistItems().get(mChecklist)[position]);

        return convertView;
    }

    private static class ViewHolder {
        TextView devLabel;
    }
}

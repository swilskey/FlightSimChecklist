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
    private String mSection;
    private int[] mIsChecked;

    public ChecklistAdapter(Context context, Checklist checklist, String section) {
        mContext = context;
        mChecklist = checklist;
        mSection = section;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.checklist_item, null);
            holder = new ViewHolder();
            holder.questionLabel = (TextView) convertView.findViewById(R.id.questionLabel);
            holder.responseLabel = (TextView) convertView.findViewById(R.id.responseLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] item = mChecklist.getChecklistItems().get(mSection)[position].split(",");
        holder.questionLabel.setText(item[0]);
        if(item.length > 1) {
            holder.responseLabel.setText(item[1]);
        }
        if(mChecklist.getIsChecked()[position] == 1) {
            convertView.setBackgroundResource(0);
        } else {
            convertView.setBackgroundResource(R.drawable.bg_not_checked);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView questionLabel;
        TextView responseLabel;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Checklist getChecklist() {
        return mChecklist;
    }

    public void setChecklist(Checklist checklist) {
        mChecklist = checklist;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public int[] getIsChecked() {
        return mIsChecked;
    }

    public void setIsChecked(int[] isChecked) {
        mIsChecked = isChecked;
    }
}

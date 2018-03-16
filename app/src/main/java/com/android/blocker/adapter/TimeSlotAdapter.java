package com.android.blocker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.blocker.R;

import java.util.ArrayList;

public class TimeSlotAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<String> timeSlots;

    public TimeSlotAdapter(Context context, ArrayList<String> timeSlots) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.timeSlots = timeSlots;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return timeSlots.size();
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if (customView == null) {
            listViewHolder = new ViewHolder();
            customView = layoutInflater.inflate(R.layout.single_timeslot_template, parent, false);

            listViewHolder.time_slot = (TextView) customView.findViewById(R.id.timeSlot);
            customView.setTag(listViewHolder);

        } else {
            listViewHolder = (ViewHolder) customView.getTag();
        }
        listViewHolder.time_slot.setText(timeSlots.get(position));
        return customView;

    }


    static class ViewHolder {

        TextView time_slot;
    }


}

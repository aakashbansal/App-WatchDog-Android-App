package com.android.blocker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.R;

import java.util.List;

public class AppAddUsageLimitAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<AppAddUsageLimit> installedAppAddUsageLimits;

    public AppAddUsageLimitAdapter(Context context, List<AppAddUsageLimit> appAddUsageLimits) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        installedAppAddUsageLimits = appAddUsageLimits;
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
        return installedAppAddUsageLimits.size();
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if (customView == null) {
            listViewHolder = new ViewHolder();
            customView = layoutInflater.inflate(R.layout.single_app_template, parent, false);

            listViewHolder.app_name = (TextView) customView.findViewById(R.id.appNameTextView);
            listViewHolder.app_icon = (ImageView) customView.findViewById(R.id.appIconImageView);
            customView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) customView.getTag();
        }
        listViewHolder.app_name.setText(installedAppAddUsageLimits.get(position).getAppName());
        listViewHolder.app_icon.setImageDrawable(installedAppAddUsageLimits.get(position).getIcon());

        return customView;

    }


    static class ViewHolder {

        TextView app_name;
        ImageView app_icon;
    }


}

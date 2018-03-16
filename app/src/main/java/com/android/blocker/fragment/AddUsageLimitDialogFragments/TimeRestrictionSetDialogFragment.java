package com.android.blocker.fragment.AddUsageLimitDialogFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blocker.R;

public class TimeRestrictionSetDialogFragment extends DialogFragment {

    private TextView perHourMinutes, perDayMinutes, perDayHours;
    private View rootView;


    public TimeRestrictionSetDialogFragment() {
        // Required empty public constructor
    }

    public static TimeRestrictionSetDialogFragment newInstance() {
        return new TimeRestrictionSetDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_restriction_usage_time_restriction, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        perHourMinutes = (TextView) rootView.findViewById(R.id.perHourTime);
        perDayHours = (TextView) rootView.findViewById(R.id.perDayTime);
        perDayMinutes = (TextView) rootView.findViewById(R.id.perDayMinutes);
    }

    public int getPerHourMinutes() {
        String string = perHourMinutes.getText().toString();
        int number = Integer.parseInt("0" + string);
        return number;
    }

    public int getPerDayHours() {
        String string = perDayHours.getText().toString();
        int number = Integer.parseInt("0" + string);
        return number;
    }

    public int getPerDayMinutes() {
        String string = perDayMinutes.getText().toString();
        int number = Integer.parseInt("0" + string);
        return number;
    }
}

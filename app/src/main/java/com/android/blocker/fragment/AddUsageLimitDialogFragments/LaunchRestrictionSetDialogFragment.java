package com.android.blocker.fragment.AddUsageLimitDialogFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blocker.R;

public class LaunchRestrictionSetDialogFragment extends DialogFragment {

    private View rootView;
    private TextView perHourLaunches, perDayLaunches;

    public LaunchRestrictionSetDialogFragment() {
        // Required empty public constructor
    }

    public static LaunchRestrictionSetDialogFragment newInstance() {
        return new LaunchRestrictionSetDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_restriction_launch_restriction_set, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        perHourLaunches = (TextView) rootView.findViewById(R.id.perHourTime);
        perDayLaunches = (TextView) rootView.findViewById(R.id.perDayTime);
    }

    public int getPerDayLaunches() {
        String string = perDayLaunches.getText().toString();
        int number = Integer.parseInt("0" + string);
        return number;
    }

    public int getPerHourLaunches() {
        String string = perHourLaunches.getText().toString();
        int number = Integer.parseInt("0" + string);
        return number;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}

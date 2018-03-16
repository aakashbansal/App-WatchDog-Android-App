package com.android.blocker.fragment.AddUsageLimitDialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android.blocker.R;

public class RestrictionsTypeSelectDialogFragment extends DialogFragment {

    private View rootView;

    private CheckBox launchCheckBox, timeCheckBox, specificTimeCheckBox;

    private boolean isLaunchCheckBoxChecked;
    private boolean isTimeCheckBoxChecked;
    private boolean isSpecificTimeCheckBoxChecked;

    public RestrictionsTypeSelectDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_restriction_restrictions_type, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        launchCheckBox = (CheckBox) rootView.findViewById(R.id.launchCheckBox);
        launchCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLaunchCheckBoxChecked = launchCheckBox.isChecked();
            }
        });

        timeCheckBox = (CheckBox) rootView.findViewById(R.id.timeCheckBox);
        timeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTimeCheckBoxChecked = timeCheckBox.isChecked();
            }
        });

        specificTimeCheckBox = (CheckBox) rootView.findViewById(R.id.specificTimeCheckBox);
        specificTimeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpecificTimeCheckBoxChecked = specificTimeCheckBox.isChecked();
            }
        });

    }


    public boolean isLaunchCheckBoxChecked() {
        return isLaunchCheckBoxChecked;
    }

    public boolean isTimeCheckBoxChecked() {
        return isTimeCheckBoxChecked;
    }

    public boolean isSpecificTimeCheckBoxChecked() {
        return isSpecificTimeCheckBoxChecked;
    }

    public static RestrictionsTypeSelectDialogFragment newInstance() {
        return new RestrictionsTypeSelectDialogFragment();

    }

}

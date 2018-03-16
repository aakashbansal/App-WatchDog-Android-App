package com.android.blocker.fragment.AddUsageLimitDialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.blocker.adapter.TimeSlotAdapter;
import com.android.blocker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SpecificTimeRestrictionSetDialogFragment extends DialogFragment {

    private View rootView;
    private ListView timeSlotsListView;
    private ArrayList<String> timeSlotsList;
    private TimeSlotAdapter timeSlotAdapter;

    public SpecificTimeRestrictionSetDialogFragment() {
        // Required empty public constructor
    }

    public static SpecificTimeRestrictionSetDialogFragment newInstance() {
        return new SpecificTimeRestrictionSetDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_restriction_specific_time_restriction, container, false);

        timeSlotsListView = (ListView) rootView.findViewById(R.id.timeSlotListView);

        timeSlotsList = new ArrayList<>();
        timeSlotsList.add("Slots Empty");

        timeSlotAdapter = new TimeSlotAdapter(getActivity(), timeSlotsList);
        timeSlotsListView.setAdapter(timeSlotAdapter);

        return rootView;
    }

    public void updateTimeSlots(String initialTime, String endTime) {
        timeSlotsList = new ArrayList<>();

        String[] beginSlots = initialTime.split("-");
        String[] endSlots = endTime.split("-");

        for (int i = 0; i < beginSlots.length; i++) {
            timeSlotsList.add(beginSlots[i] + "  -  " + endSlots[i]);
        }
        Collections.sort(timeSlotsList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        timeSlotAdapter = new TimeSlotAdapter(getActivity(), timeSlotsList);
        timeSlotsListView.setAdapter(timeSlotAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

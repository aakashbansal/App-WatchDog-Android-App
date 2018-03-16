package com.android.blocker.fragment.AppsWithUsageLimitInfoFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.blocker.DialogController.AppsUsageRestrictionInfoDialogs;
import com.android.blocker.adapter.AppAddUsageLimitAdapter;
import com.android.blocker.helper.DBWrappers;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.R;

import java.util.List;


public class AppsWithLaunchLimitFragment extends Fragment {

    private View rootView;
    private ListView allAppsListView;
    private List<AppAddUsageLimit> allAppsList;

    private boolean isFragmentCreated = false;

    public AppsWithLaunchLimitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_apps_list, container, false);
        isFragmentCreated = true;
        allAppsListView = (ListView) rootView.findViewById(R.id.allAppsListView);
        allAppsList = DBWrappers.getAppsWithLaunchLimit(getActivity());

        AppAddUsageLimitAdapter appAddUsageLimitAdapter = new AppAddUsageLimitAdapter(getActivity(), allAppsList);
        allAppsListView.setAdapter(appAddUsageLimitAdapter);

        allAppsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        AppAddUsageLimit appAddUsageLimit = allAppsList.get(position);

                        if (!appAddUsageLimit.getAppName().equals(DBWrappers.NO_LAUNCH_RESTRICTED_APPS)) {
                            AppsUsageRestrictionInfoDialogs.showDialogAppsInfoWithLaunchLimit(getActivity(), appAddUsageLimit);
                        }
                    }
                }
        );
        return rootView;
    }

    public void updateList() {

        if (!isFragmentCreated) {
            return;
        }

        allAppsList = DBWrappers.getAppsWithLaunchLimit(getActivity());
        AppAddUsageLimitAdapter appAddUsageLimitAdapter = new AppAddUsageLimitAdapter(getActivity(), allAppsList);
        allAppsListView.setAdapter(appAddUsageLimitAdapter);
    }

    @Override
    public void onDestroyView() {
        isFragmentCreated = false;
        super.onDestroyView();
    }

}

package com.android.blocker.fragment.AddUsageLimitDialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.blocker.adapter.AppAddUsageLimitAdapter;
import com.android.blocker.helper.AndroidSystemWrappers.AndroidPackageManagerWrappers;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.R;

import java.util.List;

public class RestrictableAppsListDialogFragment extends DialogFragment {

    private List<AppAddUsageLimit> installedAppsList;
    private ListView installedAppsListView;

    private String appPackageName;
    private String appName;

    public RestrictableAppsListDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_apps_list, container, false);

        installedAppsListView = (ListView) rootView.findViewById(R.id.allAppsListView);
        installedAppsList = AndroidPackageManagerWrappers.getAllInstalledAppsExceptRestrictedAppsAndMyApp(getActivity());
        appPackageName = null;
        appName = null;

        AppAddUsageLimitAdapter appAddUsageLimitAdapter = new AppAddUsageLimitAdapter(getActivity(), installedAppsList);
        installedAppsListView.setAdapter(appAddUsageLimitAdapter);

        installedAppsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AppAddUsageLimit appAddUsageLimit = installedAppsList.get(position);
                        appPackageName = appAddUsageLimit.getPackageName();
                        appName = appAddUsageLimit.getAppName();
                    }
                }
        );

        return rootView;
    }

    public static RestrictableAppsListDialogFragment newInstance() {
        return new RestrictableAppsListDialogFragment();
    }


    public String getCurrentSelectedAppPackage() {
        return appPackageName;
    }

    public String getCurrentSelectedAppName() {
        return appName;
    }

}

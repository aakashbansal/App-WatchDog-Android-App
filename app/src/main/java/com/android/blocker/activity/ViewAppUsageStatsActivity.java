package com.android.blocker.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.blocker.DialogController.AppsUsageStatsInfoDialog;
import com.android.blocker.adapter.AppViewUsageStatsAdapter;
import com.android.blocker.helper.AndroidSystemWrappers.AndroidUsageStatsManagerWrappers;
import com.android.blocker.helper.CustomComparators;
import com.android.blocker.model.AppViewUsageStats;
import com.android.blocker.database.dbViewUsageStats.DBViewUsageStatsHandler;
import com.android.blocker.R;

import java.util.Collections;
import java.util.List;

public class ViewAppUsageStatsActivity extends AppCompatActivity {

    private List<AppViewUsageStats> allApps;
    private AppViewUsageStatsAdapter appListAdapter;
    private ListView allAppsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_usage_stats);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        allAppsListView = (ListView) findViewById(R.id.allAppsListView);
        allAppsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AppViewUsageStats app = allApps.get(position);

                        AppViewUsageStats myApp = DBViewUsageStatsHandler.getAppUsageData(app.getPackageName());

                        myApp.setAppName(app.getAppName());
                        myApp.setIcon(app.getIcon());

                        AppsUsageStatsInfoDialog.showDialogAppUsageStats(ViewAppUsageStatsActivity.this, myApp);
                    }
                }
        );

        getRecentlyUsedApps();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecentlyUsedApps();
    }


    public void getRecentlyUsedApps() {
        allApps = AndroidUsageStatsManagerWrappers.getRecentlyUsedApps(this);
        appListAdapter = new AppViewUsageStatsAdapter(ViewAppUsageStatsActivity.this, allApps);
        Collections.sort(allApps, new CustomComparators.alphabetOrderAppViewUsageStats());
        allAppsListView.setAdapter(appListAdapter);
    }

}

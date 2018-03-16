package com.android.blocker.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.android.blocker.ToastController.ToastDisplay;
import com.android.blocker.ToastController.ToastMessages;
import com.android.blocker.helper.DBWrappers;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.database.dbAddUsageLimit.DBAddUsageLimitHandler;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.ConfirmAddingAppRestrictionDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.SpecificTimeRestrictionFinalTimePickerDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.SpecificTimeRestrictionInitialTimePickerDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.RestrictableAppsListDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.RestrictionsTypeSelectDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.LaunchRestrictionSetDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.SpecificTimeRestrictionSetDialogFragment;
import com.android.blocker.fragment.AddUsageLimitDialogFragments.TimeRestrictionSetDialogFragment;
import com.android.blocker.fragment.AppsWithUsageLimitInfoFragments.AppsWithAnyLimitFragment;
import com.android.blocker.fragment.AppsWithUsageLimitInfoFragments.AppsWithLaunchLimitFragment;
import com.android.blocker.fragment.AppsWithUsageLimitInfoFragments.AppsWithSpecificTimesLimitFragment;
import com.android.blocker.fragment.AppsWithUsageLimitInfoFragments.AppsWithTimeLimitFragment;
import com.android.blocker.R;

public class AddAppUsageLimitActivity extends AppCompatActivity implements SpecificTimeRestrictionInitialTimePickerDialogFragment.InitialTimePickerInterface,
        SpecificTimeRestrictionFinalTimePickerDialogFragment.FinalTimePickerInterface, ConfirmAddingAppRestrictionDialogFragment.FinalDialogInterface {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentManager mFragmentManager;

    // dialog fragments for setting up restriction limit on app
    private RestrictableAppsListDialogFragment mRestrictableAppsListDialogFragment;
    private RestrictionsTypeSelectDialogFragment mRestrictionsTypeSelectDialogFragment;
    private LaunchRestrictionSetDialogFragment mLaunchRestrictionSetDialogFragment;
    private TimeRestrictionSetDialogFragment mTimeRestrictionSetDialogFragment;
    private SpecificTimeRestrictionSetDialogFragment mSpecificTimeRestrictionSetDialogFragment;
    private SpecificTimeRestrictionInitialTimePickerDialogFragment mSpecificTimeRestrictionInitialTimePickerDialogFragment;
    private SpecificTimeRestrictionFinalTimePickerDialogFragment mSpecificTimeRestrictionFinalTimePickerDialogFragment;
    private ConfirmAddingAppRestrictionDialogFragment mConfirmAddingAppRestrictionDialogFragment;

    // dialog fragments for viewing restriction limit set on apps
    private AppsWithAnyLimitFragment mAppsWithAnyLimitFragment;
    private AppsWithLaunchLimitFragment mAppsWithLaunchLimitFragment;
    private AppsWithTimeLimitFragment mAppsWithTimeLimitFragment;
    private AppsWithSpecificTimesLimitFragment mAppsWithSpecificTimesLimitFragment;

    // properties of current app on which restriction limit is being set
    private String currentSelectedAppPackageName;
    private String currentSelectedAppName;
    private boolean isLaunchCheckBoxChecked, isTimeCheckBoxChecked, isSpecificTimeCheckBoxChecked;
    private int perDayLaunches, perHourLaunches;
    private int perHourMinutes, perDayHours, perDayMinutes;
    private String bufferInitialTime = "", bufferFinalTime = "";
    private String initialTime = "", finalTime = "";

    private int whichTabSelected = 0;

    private AppAddUsageLimit appAddUsageLimit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // setup for toolbars and floating action button
        setContentView(R.layout.activity_add_and_view_app_usage_restriction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFloatingActionButtonClicked();
            }
        });

        // setup for tabbed fragments view
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF1493"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                whichTabSelected = tab.getPosition();
                if (whichTabSelected == 0) {
                    mAppsWithAnyLimitFragment.updateList();
                } else if (whichTabSelected == 1) {
                    mAppsWithLaunchLimitFragment.updateList();
                } else if (whichTabSelected == 2) {
                    mAppsWithTimeLimitFragment.updateList();
                } else if (whichTabSelected == 3) {
                    mAppsWithSpecificTimesLimitFragment.updateList();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // fragments initialisation
        mFragmentManager = getSupportFragmentManager();
        mRestrictableAppsListDialogFragment = RestrictableAppsListDialogFragment.newInstance();
        mRestrictionsTypeSelectDialogFragment = RestrictionsTypeSelectDialogFragment.newInstance();
        mLaunchRestrictionSetDialogFragment = LaunchRestrictionSetDialogFragment.newInstance();
        mTimeRestrictionSetDialogFragment = TimeRestrictionSetDialogFragment.newInstance();
        mSpecificTimeRestrictionSetDialogFragment = SpecificTimeRestrictionSetDialogFragment.newInstance();
        mSpecificTimeRestrictionInitialTimePickerDialogFragment = SpecificTimeRestrictionInitialTimePickerDialogFragment.newInstance();
        mSpecificTimeRestrictionFinalTimePickerDialogFragment = SpecificTimeRestrictionFinalTimePickerDialogFragment.newInstance();
        mConfirmAddingAppRestrictionDialogFragment = ConfirmAddingAppRestrictionDialogFragment.newInstance();
        mAppsWithLaunchLimitFragment = new AppsWithLaunchLimitFragment();
        mAppsWithSpecificTimesLimitFragment = new AppsWithSpecificTimesLimitFragment();
        mAppsWithTimeLimitFragment = new AppsWithTimeLimitFragment();
        mAppsWithAnyLimitFragment = new AppsWithAnyLimitFragment();

    }


    // all the functions defined below are the click handlers for different buttons
    // present in various fragments of this activity

    // opens the list of installed apps for selection
    public void onFloatingActionButtonClicked() {
        showDialog(mRestrictableAppsListDialogFragment);
    }

    // closes the fragment that contains the list of installed apps
    public void onCancelButtonSelected(View view) {
        dismissDialog(mRestrictableAppsListDialogFragment);
    }

    // selects the app from the list of installed apps for adding the restriction
    public void onSelectButtonSelected(View view) {
        currentSelectedAppPackageName = mRestrictableAppsListDialogFragment.getCurrentSelectedAppPackage();
        currentSelectedAppName = mRestrictableAppsListDialogFragment.getCurrentSelectedAppName();
        initialTime = "";
        finalTime = "";
        bufferFinalTime = "";
        bufferInitialTime = "";

        if (currentSelectedAppPackageName != null) {
            dismissDialog(mRestrictableAppsListDialogFragment);
            showDialog(mRestrictionsTypeSelectDialogFragment);
        } else {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.SELECT_AN_APP);
        }
    }


    // selects which type of restriction is needed to be added on the app
    // i.e Launch type, usage time type, specific time type
    public void onProceedButtonSelected(View view) {
        isLaunchCheckBoxChecked = mRestrictionsTypeSelectDialogFragment.isLaunchCheckBoxChecked();
        isSpecificTimeCheckBoxChecked = mRestrictionsTypeSelectDialogFragment.isSpecificTimeCheckBoxChecked();
        isTimeCheckBoxChecked = mRestrictionsTypeSelectDialogFragment.isTimeCheckBoxChecked();

        if (!isTimeCheckBoxChecked && !isLaunchCheckBoxChecked && !isSpecificTimeCheckBoxChecked) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.SELECT_AT_LEAST_ONE);
            return;
        }

        dismissDialog(mRestrictionsTypeSelectDialogFragment);

        if (isLaunchCheckBoxChecked) {
            showDialog(mLaunchRestrictionSetDialogFragment);
        } else if (isTimeCheckBoxChecked) {
            showDialog(mTimeRestrictionSetDialogFragment);
        } else {
            showDialog(mSpecificTimeRestrictionSetDialogFragment);
        }
    }

    // for going back from "Restrictions Type Select" Fragment
    public void onBackButtonSelected(View view) {
        dismissDialog(mRestrictionsTypeSelectDialogFragment);
        showDialog(mRestrictableAppsListDialogFragment);
    }

    // Next Button of LaunchRestrictionSet Fragment
    public void onNextButtonSelected(View view) {
        perHourLaunches = mLaunchRestrictionSetDialogFragment.getPerHourLaunches();
        perDayLaunches = mLaunchRestrictionSetDialogFragment.getPerDayLaunches();

        if (perDayLaunches == 0 && perHourLaunches == 0) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.FILL_AT_LEAST_ONE_ENTRY);
            return;
        }

        if (perDayLaunches < perHourLaunches && perDayLaunches != 0) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.LAUNCH_PER_DAY_MORE_THAN_LAUNCH_PER_HOUR);
        }

        dismissDialog(mLaunchRestrictionSetDialogFragment);

        if (isTimeCheckBoxChecked) {
            showDialog(mTimeRestrictionSetDialogFragment);
        } else if (isSpecificTimeCheckBoxChecked) {
            showDialog(mSpecificTimeRestrictionSetDialogFragment);
        } else {
            showDialog(mConfirmAddingAppRestrictionDialogFragment);
        }
    }

    // Next button of TimeRestrictionSet fragment
    public void onNextTimeButtonSelected(View view) {
        perHourMinutes = mTimeRestrictionSetDialogFragment.getPerHourMinutes();
        perDayHours = mTimeRestrictionSetDialogFragment.getPerDayHours();
        perDayMinutes = mTimeRestrictionSetDialogFragment.getPerDayMinutes();

        if (perHourMinutes > 60) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.HOUR_HAS_60_MIN);
            return;
        }
        if (perDayHours > 24) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.DAY_HAS_24_HOURS);
        }
        if (perDayMinutes > 60) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.HOUR_HAS_60_MIN);
            return;
        }
        if (perDayMinutes == 0 && perDayHours == 0 && perHourMinutes == 0) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.FILL_AT_LEAST_ONE_ENTRY);
            return;
        }

        if (perDayHours == 24 && perDayMinutes > 0) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.DAY_HAS_24_HOURS);
            return;
        }

        if ((perDayHours * 60 + perDayMinutes) < perHourMinutes && (perDayHours * 60 + perDayMinutes) != 0) {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.TIME_PER_DAY_MORE_THAN_TIME_PER_HOUR);
            return;
        }

        dismissDialog(mTimeRestrictionSetDialogFragment);

        if (isSpecificTimeCheckBoxChecked) {
            showDialog(mSpecificTimeRestrictionSetDialogFragment);
        } else {
            showDialog(mConfirmAddingAppRestrictionDialogFragment);
        }
    }

    // for adding initial time slot for specific time limit fragment
    public void onAddTimeSlotSelected(View view) {
        showDialog(mSpecificTimeRestrictionInitialTimePickerDialogFragment);
    }


    // setting initial time for "Specific Time Limit Set" Fragment
    @Override
    public void InitialTimeSetListener(int hour, int minute) {
        StringBuilder SB = new StringBuilder();
        SB.append(hour + ":" + minute);
        bufferInitialTime = SB.toString();
        showDialog(mSpecificTimeRestrictionFinalTimePickerDialogFragment);
    }

    // setting final time for "Specific Time Limit Set" Fragment
    @Override
    public void FinalTimeSetListener(int hour, int minute) {
        StringBuilder SB = new StringBuilder();
        SB.append(hour + ":" + minute);
        bufferFinalTime = SB.toString();
        initialTime = bufferInitialTime + "-" + initialTime;
        finalTime = bufferFinalTime + "-" + finalTime;
        mSpecificTimeRestrictionSetDialogFragment.updateTimeSlots(initialTime, finalTime);
    }

    // for going back from the final time picker fragment
    @Override
    public void onBackButtonFinalTimePickerFragmentSelected() {
        dismissDialog(mSpecificTimeRestrictionFinalTimePickerDialogFragment);
        showDialog(mSpecificTimeRestrictionInitialTimePickerDialogFragment);
    }

    // the next button after selecting the required time slots for app
    public void onDoneButtonSelected(View view) {
        if (bufferFinalTime == "") {
            ToastDisplay.makeShortDurationToast(this, ToastMessages.SELECT_AT_LEAST_TIME_SLOT);
        }
        showDialog(mConfirmAddingAppRestrictionDialogFragment);
    }

    // for finally adding the restriction limit on app by selecting "Yes"
    // on last alert dialog
    @Override
    public void onYesFinalAlertSelected() {
        if (isSpecificTimeCheckBoxChecked) {
            dismissDialog(mSpecificTimeRestrictionSetDialogFragment);
        } else if (isTimeCheckBoxChecked) {
            dismissDialog(mTimeRestrictionSetDialogFragment);
        } else {
            dismissDialog(mLaunchRestrictionSetDialogFragment);
        }

        initializeAppAddUsageLimitObjectWithSelectedOptions();

        DBAddUsageLimitHandler.addNewAppLimit(appAddUsageLimit);

        // update the current fragment
        if (whichTabSelected == 0) {
            mAppsWithAnyLimitFragment.updateList();
        } else if (whichTabSelected == 1) {
            mAppsWithLaunchLimitFragment.updateList();
        } else if (whichTabSelected == 2) {
            mAppsWithTimeLimitFragment.updateList();
        } else if (whichTabSelected == 3) {
            mAppsWithSpecificTimesLimitFragment.updateList();
        }

        String toastMsg = ToastMessages.LIMIT_SET + appAddUsageLimit.getAppName() + ".";
        ToastDisplay.makeLongDurationToast(this, toastMsg);
    }

    // selecting exit on the final alert dialog
    @Override
    public void onExitFinalAlertSelected() {
        dismissDialog(mSpecificTimeRestrictionSetDialogFragment);
    }

    // construct an object for adding restriction limit on given selected app
    private void initializeAppAddUsageLimitObjectWithSelectedOptions() {

        appAddUsageLimit = new AppAddUsageLimit();

        appAddUsageLimit.setPackageName(currentSelectedAppPackageName);
        appAddUsageLimit.setAppName(currentSelectedAppName);

        appAddUsageLimit.setIsLaunchRestrictionSet(isLaunchCheckBoxChecked);
        appAddUsageLimit.setIsUsageTimeRestrictionSet(isTimeCheckBoxChecked);
        appAddUsageLimit.setIsSpecifiTimeRestrictionSet(isSpecificTimeCheckBoxChecked);

        if (isLaunchCheckBoxChecked) {
            if (perDayLaunches != 0) {
                appAddUsageLimit.setLaunchAllowedPerDay(perDayLaunches);
            } else {
                appAddUsageLimit.setLaunchAllowedPerDay(DBWrappers.LAUNCH_COUNT_INFINITY);
            }
            if (perHourLaunches != 0) {
                appAddUsageLimit.setLaunchAllowedPerHour(perHourLaunches);
            } else {
                appAddUsageLimit.setLaunchAllowedPerHour(DBWrappers.LAUNCH_COUNT_INFINITY);
            }
        }


        if (isTimeCheckBoxChecked) {
            if (perDayHours == 0 && perDayMinutes == 0) {
                appAddUsageLimit.setTimeAllowedPerDay(DBWrappers.TIME_VALUE_INFINITY);
            } else {
                appAddUsageLimit.setTimeAllowedPerDay(perDayHours * 60 * 60 * 1000 + perDayMinutes * 60 * 1000);
            }

            if (perHourMinutes != 0) {
                appAddUsageLimit.setTimeAllowedPerHour(perHourMinutes * 60 * 1000);
            } else {
                appAddUsageLimit.setTimeAllowedPerHour(DBWrappers.TIME_VALUE_INFINITY);
            }
        }


        if (isSpecificTimeCheckBoxChecked) {
            appAddUsageLimit.setSpecificTimeBegin(initialTime);
            appAddUsageLimit.setSpecificTimeEnd(finalTime);
        }
    }


    private void dismissDialog(DialogFragment dialog) {
        dialog.dismiss();
    }

    private void showDialog(DialogFragment dialog) {
        dialog.show(mFragmentManager, "DialogFragment");
    }


    // default class for managing different fragments of the tabbed activity
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mAppsWithAnyLimitFragment;
                case 1:
                    return mAppsWithLaunchLimitFragment;
                case 2:
                    return mAppsWithTimeLimitFragment;
                case 3:
                    return mAppsWithSpecificTimesLimitFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ALL";
                case 1:
                    return "LAUNCHES";
                case 2:
                    return "USAGE TIME";
                case 3:
                    return "SPECIFIC TIMES";
            }
            return null;
        }
    }
}

package com.android.blocker.helper;

import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.model.AppViewUsageStats;

import java.util.Comparator;

public class CustomComparators {


    public static class alphabetOrderAppAddUsageLimit implements Comparator<AppAddUsageLimit> {
        @Override
        public int compare(AppAddUsageLimit left, AppAddUsageLimit right) {
            return left.getAppName().compareToIgnoreCase(right.getAppName());
        }
    }



    public static class alphabetOrderAppViewUsageStats implements Comparator<AppViewUsageStats> {

        @Override
        public int compare(AppViewUsageStats left, AppViewUsageStats right) {
            return left.getAppName().compareToIgnoreCase(right.getAppName());
        }
    }
}

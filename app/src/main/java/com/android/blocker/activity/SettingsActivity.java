package com.android.blocker.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.android.blocker.DialogController.PromptAlertUserDialogs;
import com.android.blocker.R;
import com.android.blocker.ToastController.ToastDisplay;
import com.android.blocker.ToastController.ToastMessages;
import com.android.blocker.helper.SpecialIntents;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        boolean notificationsOn = MySharedPreferences.getBooleanValue(getApplicationContext(), SPNames.SP_NOTIFICATION,
                SPNames.SP_KEY_NOTIFICATION_SHOULD_NOTIFY, false);

        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        if (notificationsOn) {
            notificationSwitch.setChecked(true);
        }

    }

    public void onSwitchClicked(View v) {
        if (notificationSwitch.isChecked()) {
            MySharedPreferences.storeBooleanValue(getApplicationContext(), SPNames.SP_NOTIFICATION,
                    SPNames.SP_KEY_NOTIFICATION_SHOULD_NOTIFY, true);
        } else {
            MySharedPreferences.storeBooleanValue(getApplicationContext(), SPNames.SP_NOTIFICATION,
                    SPNames.SP_KEY_NOTIFICATION_SHOULD_NOTIFY, false);
        }
    }

    public void onDisableAppOptionClicked(View v) {

        boolean wasAppAlreadyDisabledOnceThisDay = MySharedPreferences.getBooleanValue(getApplicationContext(),
                SPNames.SP_APP_DISABLED, SPNames.SP_KEY_WAS_APP_DISABLED_ONCE_TODAY, false);

        if (wasAppAlreadyDisabledOnceThisDay) {
            ToastDisplay.makeLongDurationToast(getApplicationContext(), ToastMessages.APP_WAS_ALREADY_DISABLED_ONCE);
        } else {
            PromptAlertUserDialogs.showDisableAppDialog(SettingsActivity.this);
        }

    }

    public void onProvideAutostartPermissionOptionClicked(View v) {
        String manufacturer = "xiaomi";
        if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            SpecialIntents.showAutoStartPermissionScreen(getApplicationContext());
        }else{
            ToastDisplay.makeLongDurationToast(getApplicationContext(),ToastMessages.AUTOSTART_PERMISSION_NOT_REQUIRED);
        }
    }


}

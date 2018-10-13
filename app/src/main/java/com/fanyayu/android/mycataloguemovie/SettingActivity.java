package com.fanyayu.android.mycataloguemovie;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.fanyayu.android.mycataloguemovie.reminder.DailyReminder;
import com.fanyayu.android.mycataloguemovie.reminder.ReleaseReminder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.switch_release)
    Switch switchRelease;
    @BindView(R.id.switch_daily) Switch switchDaily;
    @BindView(R.id.change_language)
    LinearLayout changeLang;

    private DailyReminder mDailyReminder;
    private ReleaseReminder mReleaseReminder;
    private AppPreference appPref;
    private boolean isDaily, isRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        switchDaily.setOnClickListener(this);
        switchRelease.setOnClickListener(this);
        changeLang.setOnClickListener(this);

        appPref = new AppPreference(this);
        mDailyReminder = new DailyReminder();
        mReleaseReminder = new ReleaseReminder();
        getSupportActionBar().setTitle(R.string.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        setNotification();
    }

    private void setNotification() {
        if (appPref.isDaily()){
            switchDaily.setChecked(true);
        } else {
            switchDaily.setChecked(false);
        }

        if (appPref.isRelease()){
            switchRelease.setChecked(true);
        } else {
            switchRelease.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.switch_daily:
                isDaily = switchDaily.isChecked();
                if (isDaily){
                    switchDaily.setEnabled(true);
                    appPref.setDaily(isDaily);
                    mDailyReminder.setRepeatingAlarm(this, DailyReminder.TYPE_REPEATING, "11:00");

                } else {
                    switchDaily.setChecked(false);
                    appPref.setDaily(isDaily);
                    mDailyReminder.cancelAlarm(this);

                }
                break;
            case R.id.switch_release:
                isRelease = switchRelease.isChecked();
                if (isRelease){
                    switchRelease.setEnabled(true);
                    appPref.setRelease(isRelease);
                    mReleaseReminder.setReleaseAlarm(this, ReleaseReminder.TYPE_REPEATING, "11:30");

                } else {
                    switchRelease.setChecked(false);
                    appPref.setRelease(isRelease);
                    mReleaseReminder.cancelAlarmRelease(this);

                }
                break;
            case R.id.change_language:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

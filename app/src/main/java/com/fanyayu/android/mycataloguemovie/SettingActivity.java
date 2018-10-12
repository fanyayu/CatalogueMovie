package com.fanyayu.android.mycataloguemovie;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.fanyayu.android.mycataloguemovie.reminder.DailyReminder;
import com.fanyayu.android.mycataloguemovie.reminder.SchedulerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.switch_release)
    Switch switchRelease;
    @BindView(R.id.switch_daily) Switch switchDaily;
    @BindView(R.id.change_language)
    LinearLayout changeLang;

    private SchedulerTask mSchedulerTask;
    private DailyReminder mDailyReminder;
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
        mDailyReminder = new DailyReminder(this);
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
                    mDailyReminder.setRepeatingAlarm(this, DailyReminder.TYPE_REPEATING, "07:15");
                    Toast.makeText(this, R.string.daily_activated, Toast.LENGTH_SHORT).show();
                } else {
                    switchDaily.setChecked(false);
                    appPref.setDaily(isDaily);
                    mDailyReminder.cancelAlarm(this, DailyReminder.TYPE_REPEATING);
                    Toast.makeText(this, R.string.daily_activated_cancel, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_release:
                isRelease = switchRelease.isChecked();
                mSchedulerTask = new SchedulerTask(this);
                if (isRelease){
                    switchRelease.setEnabled(true);
                    appPref.setRelease(isRelease);

                    mSchedulerTask.createPeriodicTask();
                    Toast.makeText(this, R.string.release_activated, Toast.LENGTH_SHORT).show();
                } else {
                    switchRelease.setChecked(false);
                    appPref.setRelease(isRelease);
                    mSchedulerTask.cancelPeriodicTask();
                    Toast.makeText(this, R.string.release_activated_cancel, Toast.LENGTH_SHORT).show();
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

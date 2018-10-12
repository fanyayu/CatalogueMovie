package com.fanyayu.android.mycataloguemovie.reminder;

import android.content.Context;

import com.fanyayu.android.mycataloguemovie.reminder.SchedulerService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask(){
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(3 * 60 * 1000)
                .setFlex(10)
                .setTag(SchedulerService.TAG_TASK_RELEASEMOV_LOG)
                .setPersisted(true)
                .build();

        mGcmNetworkManager.schedule(periodicTask);
    }
    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(SchedulerService.TAG_TASK_RELEASEMOV_LOG, SchedulerService.class);
        }
    }
}

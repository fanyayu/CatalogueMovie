package com.fanyayu.android.mycataloguemovie.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.fanyayu.android.mycataloguemovie.MainActivity;
import com.fanyayu.android.mycataloguemovie.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {
    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;
    private static final String CHANNEL_ID = "myCatalogueMovie";
    public static final String EXTRA_TYPE = "type";
    private Context mContext;

    public DailyReminder(Context context){
        mContext = context;
    }

    public DailyReminder(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        showAlarmNotification(context, notifId);
    }

    private void showAlarmNotification(Context context, int notifId) {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, mContext.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat
                .Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_filter_24dp)
                .setContentTitle(mContext.getResources().getString(R.string.daily_reminder))
                .setContentText(mContext.getResources().getString(R.string.daily_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID);
        notificationManager.notify(notifId, notification.build());
    }

    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void setRepeatingAlarm(Context context, String type, String time) {
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}

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
import android.widget.Toast;

import com.fanyayu.android.mycataloguemovie.MainActivity;
import com.fanyayu.android.mycataloguemovie.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    private final int NOTIF_ID_REPEATING = 101;
    private static final String CHANNEL_ID = "myCatalogueMovie";
    public static final String EXTRA_TYPE = "type";

    public DailyReminder(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int notifId = NOTIF_ID_REPEATING;
        showAlarmNotification(context, notifId);
    }

    private void showAlarmNotification(Context context, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_filter_24dp)
                .setContentTitle(context.getResources().getString(R.string.daily_reminder))
                .setContentText(context.getResources().getString(R.string.daily_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID);
        notificationManager.notify(notifId, notification.build());
    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.daily_activated_cancel, Toast.LENGTH_SHORT).show();
    }

    public void setRepeatingAlarm(Context context, String type, String time) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, R.string.daily_activated, Toast.LENGTH_SHORT).show();
    }
}

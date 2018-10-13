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

import com.fanyayu.android.mycataloguemovie.BuildConfig;
import com.fanyayu.android.mycataloguemovie.MainActivity;
import com.fanyayu.android.mycataloguemovie.MovieDetailFragment;
import com.fanyayu.android.mycataloguemovie.R;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;
import com.fanyayu.android.mycataloguemovie.taskloader.MovieTaskLoader;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ReleaseReminder extends BroadcastReceiver {
    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    private final int NOTIF_ID_REPEATING = 102;
    private static final String CHANNEL_ID = "myCatalogueMovie";
    public static final String EXTRA_TYPE = "type";
    private static final String API_KEY = BuildConfig.API_KEY;

    public ReleaseReminder(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        getReleasedToday(context);
    }

    private void showAlarmNotification(Context context,int notifId, String title, String overview, MovieItems item) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(channel);
        }
        String date = item.getReleaseDate();
        String poster = item.getPosterPath();
        String popularity = item.getMoviePopularity();
        String language = item.getMovieLanguage();

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MovieDetailFragment.EXTRA_NAME, title);
        intent.putExtra(MovieDetailFragment.EXTRA_DATE, date);
        intent.putExtra(MovieDetailFragment.EXTRA_DESC, overview);
        intent.putExtra(MovieDetailFragment.EXTRA_IMG, poster);
        intent.putExtra(MovieDetailFragment.EXTRA_LANG, language);
        intent.putExtra(MovieDetailFragment.EXTRA_POP, popularity);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_filter_24dp)
                .setContentTitle(title)
                .setContentText(title+ " "+context.getString(R.string.remindertext))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID);
        notificationManager.notify(notifId, notification.build());
    }

    public void cancelAlarmRelease(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.release_activated_cancel, Toast.LENGTH_SHORT).show();
    }

    public void setReleaseAlarm(Context context, String type, String time) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseReminder.class);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, R.string.release_activated, Toast.LENGTH_SHORT).show();
    }

    private void getReleasedToday(final Context context) {
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
        MovieTaskLoader movieTaskLoader = new MovieTaskLoader(context, url);
        List<MovieItems> items = movieTaskLoader.loadInBackground();
        int index = new Random().nextInt(items.size());

        MovieItems item = items.get(index);

        int notifId = 100;
        String title = items.get(index).getMovieTitle();
        String overview = items.get(index).getMovieOverview();

        showAlarmNotification(context,notifId, title, overview, item);
    }
}

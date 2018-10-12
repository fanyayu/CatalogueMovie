package com.fanyayu.android.mycataloguemovie.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.fanyayu.android.mycataloguemovie.BuildConfig;
import com.fanyayu.android.mycataloguemovie.MainActivity;
import com.fanyayu.android.mycataloguemovie.MovieDetailFragment;
import com.fanyayu.android.mycataloguemovie.R;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;
import com.fanyayu.android.mycataloguemovie.taskloader.MovieTaskLoader;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.List;
import java.util.Random;

public class SchedulerService extends GcmTaskService {
    public static String TAG_TASK_RELEASEMOV_LOG = "ReleaseTodayTask";
    private static final String CHANNEL_ID = "myCatalogueMovie";
    public static final int NOTIFICATION_ID = 100;
    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;

        if (taskParams.getTag().equals(TAG_TASK_RELEASEMOV_LOG)){
            getReleasedToday();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    public void getReleasedToday() {
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
        MovieTaskLoader movieTaskLoader = new MovieTaskLoader(getApplicationContext(), url);
        List<MovieItems> items = movieTaskLoader.loadInBackground();
        int index = new Random().nextInt(items.size());
        
        MovieItems item = items.get(index);
        String title = items.get(index).getMovieTitle();
        String overview = items.get(index).getMovieOverview();
        
        showNotification(getApplicationContext(), title, overview, item);
    }

    private void showNotification(Context applicationContext, String title, String overview, MovieItems item) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(channel);
        }

        String date = item.getReleaseDate();
        String poster = item.getPosterPath();
        String popularity = item.getMoviePopularity();
        String language = item.getMovieLanguage();

        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.putExtra(MovieDetailFragment.EXTRA_NAME, title);
        intent.putExtra(MovieDetailFragment.EXTRA_DATE, date);
        intent.putExtra(MovieDetailFragment.EXTRA_DESC, overview);
        intent.putExtra(MovieDetailFragment.EXTRA_IMG, poster);
        intent.putExtra(MovieDetailFragment.EXTRA_LANG, language);
        intent.putExtra(MovieDetailFragment.EXTRA_POP, popularity);
        PendingIntent pendingIntent = PendingIntent.getActivity(applicationContext, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat
                .Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_filter_24dp)
                .setContentTitle(title)
                .setContentText(title+ " "+getResources().getString(R.string.remindertext))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID);
        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }
}

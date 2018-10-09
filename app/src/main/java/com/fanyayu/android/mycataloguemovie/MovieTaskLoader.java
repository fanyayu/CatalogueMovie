package com.fanyayu.android.mycataloguemovie;

import android.os.Build;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.fanyayu.android.mycataloguemovie.entity.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mDataMovie;
    private boolean mHasResult = false;

    private String mMovieUrl;


    public MovieTaskLoader(final Context context, String movieUrl) {
        super(context);
        onContentChanged();
        this.mMovieUrl = movieUrl;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mDataMovie);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResource(mDataMovie);
            mDataMovie = null;
            mHasResult = false;
        }
    }

    private void onReleaseResource(ArrayList<MovieItems> data) {
    }

    @Override
    public void deliverResult(ArrayList<MovieItems> data) {
        mDataMovie = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemss = new ArrayList<>();

        client.get(mMovieUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++){
                        JSONObject movies = results.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movies);
                        movieItemss.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemss;
    }


}

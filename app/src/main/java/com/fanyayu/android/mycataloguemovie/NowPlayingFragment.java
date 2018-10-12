package com.fanyayu.android.mycataloguemovie;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanyayu.android.mycataloguemovie.adapter.MovieAdapter;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;
import com.fanyayu.android.mycataloguemovie.taskloader.MovieTaskLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    @BindView(R.id.rv_movie_nowplay)
    RecyclerView rvNowPlay;
    MovieAdapter adapter;
    public static final String NOW_PLAYING_URL = "NOW_PLAYING";
    private static final String API_KEY = BuildConfig.API_KEY;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new MovieAdapter(getContext());
        adapter.notifyDataSetChanged();

        rvNowPlay.setAdapter(adapter);
        rvNowPlay.setHasFixedSize(true);
        rvNowPlay.setLayoutManager(new LinearLayoutManager(getContext()));

        final Bundle bundle = new Bundle();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";
        bundle.putString(NOW_PLAYING_URL, url);
        getLoaderManager().initLoader(0,bundle,this);
        return rootView;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String movieUrl = "";
        if (args!= null) {
            movieUrl = args.getString(NOW_PLAYING_URL);
        }
        return new MovieTaskLoader(getContext(), movieUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }
}

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    @BindView(R.id.rv_movie_upcoming)
    RecyclerView rvUpcoming;
    MovieAdapter adapter;
    public static final String UPCOMING_URL = "UPCOMING_URL";
    private static final String API_KEY = BuildConfig.API_KEY;

    public UpcomingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, rootView);
        adapter = new MovieAdapter(getContext());

        adapter.notifyDataSetChanged();

        rvUpcoming.setAdapter(adapter);

        rvUpcoming.setHasFixedSize(true);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(getContext()));


        final Bundle bundle = new Bundle();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
        bundle.putString(UPCOMING_URL, url);
        getLoaderManager().initLoader(0, bundle, this);
        return rootView;

    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String movieURL = "";
        if (args != null) {
            movieURL = args.getString(UPCOMING_URL);
        }
        return new MovieTaskLoader(getContext(), movieURL);
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

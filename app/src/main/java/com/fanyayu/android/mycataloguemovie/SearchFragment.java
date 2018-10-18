package com.fanyayu.android.mycataloguemovie;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fanyayu.android.mycataloguemovie.adapter.MovieAdapter;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    @BindView(R.id.rv_movie_search) RecyclerView recyclerView;
    MovieAdapter adapter;
    @BindView(R.id.sv_movie)
    android.support.v7.widget.SearchView svMovie;
    @BindView(R.id.search_Progress)
    ProgressBar searchProg;
    static final String MOVIE_TITLE = "MOVIE_TITLE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        adapter = new MovieAdapter(getContext());
        adapter.notifyDataSetChanged();

        searchProg.setVisibility(View.GONE);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        svMovie.setActivated(true);
        svMovie.setIconified(false);
        svMovie.onActionViewExpanded();
        svMovie.clearFocus();
        svMovie.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 1) {

                    Bundle bundle = new Bundle();
                    bundle.putString(MOVIE_TITLE, newText);
                    getLoaderManager().restartLoader(0,bundle, SearchFragment.this);
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String movieTitle = "";
        if (args!= null) {
            movieTitle = args.getString(MOVIE_TITLE);
        }
        searchProg.setVisibility(View.VISIBLE);
        return new MovieTaskLoader(getContext(),null, movieTitle);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        searchProg.setVisibility(View.GONE);
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }


}

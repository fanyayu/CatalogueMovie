package com.fanyayu.android.mycataloguemovie;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fanyayu.android.mycataloguemovie.adapter.MovieAdapter;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>, View.OnClickListener {

    @BindView(R.id.rv_movie_search) RecyclerView recyclerView;
    MovieAdapter adapter;
    @BindView(R.id.edt_Cari) EditText edtCari;
    @BindView(R.id.btn_Cari) Button btnCari;
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

        btnCari.setOnClickListener(this);
        searchProg.setVisibility(View.GONE);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Cari){
            String movieTitle = edtCari.getText().toString();

            if (TextUtils.isEmpty(movieTitle))return;

            Bundle bundle = new Bundle();
            bundle.putString(MOVIE_TITLE,movieTitle);
            getLoaderManager().restartLoader(0,bundle, SearchFragment.this);
        }
    }
}

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

import com.fanyayu.android.mycataloguemovie.adapter.MovieAdapter;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>{

    @BindView(R.id.rv_movie_search) RecyclerView recyclerView;
    MovieAdapter adapter;
    @BindView(R.id.edt_Cari) EditText edtCari;
    @BindView(R.id.btn_Cari) Button btnCari;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        adapter = new MovieAdapter(getContext());
        adapter.notifyDataSetChanged();

        btnCari.setOnClickListener(myListener);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String movieTitle = edtCari.getText().toString();
        final Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movieTitle);
        getLoaderManager().initLoader(0, bundle, this);
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String movieTitle = "";
        if (args!= null) {
            movieTitle = args.getString(EXTRAS_MOVIE);
        }
        return new MyTaskLoader(getContext(), movieTitle);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {

        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String movieTitle = edtCari.getText().toString();

            if (TextUtils.isEmpty(movieTitle))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE,movieTitle);
            getLoaderManager().restartLoader(0,bundle, SearchFragment.this);
        }
    };
}

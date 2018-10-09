package com.fanyayu.android.mycataloguemovie;


import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fanyayu.android.mycataloguemovie.db.DatabaseContract;

import java.text.DateFormat;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.CONTENT_URI;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns.LANGUAGE;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns.OVERVIEW;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns.POPULARITY;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns.POSTERPATH;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns.RELEASEDATE;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns.TITLE;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns._ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.rl_detail)
    RelativeLayout rldetail;
    @BindView(R.id.tv_det_date)
    TextView dateMovie;
    @BindView(R.id.tv_det_title)
    TextView movieName;
    @BindView(R.id.img_det_movie)
    ImageView moviePoster;
    @BindView(R.id.tv_det_desc) TextView movieDesc;
    @BindView(R.id.tv_det_language) TextView movieLang;
    @BindView(R.id.tv_det_popularity) TextView moviePopularity;
    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFav;

    public static Context appContext;
    public static String EXTRA_ID = "id";
    public static String EXTRA_IMG = "extra_image";
    public static String EXTRA_NAME = "extra_name";
    public static String EXTRA_DESC = "extra_desc";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_LANG = "extra_lang";
    public static String EXTRA_POP = "extra_pop";

    public static int RESULT_ADD = 101;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        fabFav.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appContext = getContext();
        movieName.setText(getArguments().getString(EXTRA_NAME));
        movieDesc.setText(getArguments().getString(EXTRA_DESC));
        movieLang.setText(getArguments().getString(EXTRA_LANG));
        dateMovie.setText(getArguments().getString(EXTRA_DATE));
        moviePopularity.setText(getArguments().getString(EXTRA_POP));
        Glide
                .with(this)
                .load("http://image.tmdb.org/t/p/w185"+getArguments().getString(EXTRA_IMG))
                .into(moviePoster);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_favorite) {
            ContentValues values = new ContentValues();
            values.put(_ID, getArguments().getInt(EXTRA_ID));
            values.put(TITLE, getArguments().getString(EXTRA_NAME));
            values.put(OVERVIEW, getArguments().getString(EXTRA_DESC));
            values.put(POSTERPATH, getArguments().getString(EXTRA_IMG));
            values.put(RELEASEDATE, getArguments().getString(EXTRA_DATE));
            values.put(LANGUAGE, getArguments().getString(EXTRA_LANG));
            values.put(POPULARITY, getArguments().getString(EXTRA_POP));

            appContext.getContentResolver().insert(CONTENT_URI, values);
            getActivity().setResult(RESULT_ADD);

            showSnackbarMessage(getString(R.string.fav_success));
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rldetail, message, Snackbar.LENGTH_SHORT).show();
    }
}

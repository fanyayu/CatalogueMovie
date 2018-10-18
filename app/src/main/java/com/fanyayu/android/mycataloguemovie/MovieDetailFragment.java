package com.fanyayu.android.mycataloguemovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.fanyayu.android.mycataloguemovie.db.FavoriteHelper;

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
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.TABLE_FAV;


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
    private FavoriteHelper faveHelper;

    public static Context appContext;
    public static String EXTRA_ID = "id";
    public static String EXTRA_IMG = "extra_image";
    public static String EXTRA_NAME = "extra_name";
    public static String EXTRA_DESC = "extra_desc";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_LANG = "extra_lang";
    public static String EXTRA_POP = "extra_pop";
    private String movieID;
    private static String DB_TABLE = TABLE_FAV;

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
        movieID = String.valueOf(getArguments().getInt(EXTRA_ID));
        faveHelper = new FavoriteHelper(getContext());
        faveHelper.open();
        isExist(movieID);
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
            Cursor cursor = faveHelper.queryByIdProvider(movieID);
            if (cursor.getCount() > 0) {
                fabFav.setImageResource(R.drawable.ic_favorite_pink_24dp);
                faveHelper.deleteProvider(movieID);
                getActivity().setResult(RESULT_ADD);
                showSnackbarMessage(getString(R.string.del_success));

            } else {
                fabFav.setImageResource(R.drawable.ic_favorite_border_pink_24dp);
                ContentValues values = new ContentValues();
                values.put(_ID, getArguments().getInt(EXTRA_ID));
                values.put(TITLE, getArguments().getString(EXTRA_NAME));
                values.put(OVERVIEW, getArguments().getString(EXTRA_DESC));
                values.put(POSTERPATH, getArguments().getString(EXTRA_IMG));
                values.put(RELEASEDATE, getArguments().getString(EXTRA_DATE));
                values.put(LANGUAGE, getArguments().getString(EXTRA_LANG));
                values.put(POPULARITY, getArguments().getString(EXTRA_POP));
                faveHelper.insertProvider(values);
                getActivity().setResult(RESULT_ADD);
                showSnackbarMessage(getString(R.string.fav_success));

            }
            isExist(movieID);
        }
    }

    public void isExist(String movieId){
        Cursor cursor1 = faveHelper.queryByIdProvider(movieId);
        if (cursor1.getCount() > 0) {
            fabFav.setImageResource(R.drawable.ic_favorite_pink_24dp);
        } else {
            fabFav.setImageResource(R.drawable.ic_favorite_border_pink_24dp);
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rldetail, message, Snackbar.LENGTH_SHORT).show();
    }
}

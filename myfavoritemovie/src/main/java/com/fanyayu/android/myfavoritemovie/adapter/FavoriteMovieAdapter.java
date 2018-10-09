package com.fanyayu.android.myfavoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanyayu.android.myfavoritemovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.OVERVIEW;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.POSTERPATH;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.RELEASEDATE;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.TITLE;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.getColumnString;

public class FavoriteMovieAdapter extends CursorAdapter {

    @BindView(R.id.tv_movie_name)
    TextView tvTitle;
    @BindView(R.id.tv_movie_date) TextView tvDate;
    @BindView(R.id.tv_movie_desc) TextView tvDesc;
    @BindView(R.id.img_item_moviesearch)
    ImageView imgMovie;
    public FavoriteMovieAdapter(Context context, Cursor c, boolean autoRequery){
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            tvTitle.setText(getColumnString(cursor, TITLE));
            tvDate.setText(getColumnString(cursor, RELEASEDATE));
            tvDesc.setText(getColumnString(cursor, OVERVIEW));
            Glide
                    .with(context)
                    .load("http://image.tmdb.org/t/p/w185"+getColumnString(cursor, POSTERPATH))
                    .into(imgMovie);
        }
    }
}

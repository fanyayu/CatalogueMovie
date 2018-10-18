package com.fanyayu.android.mycataloguemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanyayu.android.mycataloguemovie.MovieDetailFragment;
import com.fanyayu.android.mycataloguemovie.entity.MovieItems;
import com.fanyayu.android.mycataloguemovie.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns._ID;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.TABLE_FAV;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<MovieItems> mData = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context){
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_moviesearch) ImageView imgItemMovieSearch;
        @BindView(R.id.tv_movie_name) TextView tvMovieName;
        @BindView(R.id.tv_movie_desc) TextView tvMovieDesc;
        @BindView(R.id.tv_movie_date) TextView tvMovieDate;
        @BindView(R.id.btn_detail)
        Button btnDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setData(ArrayList<MovieItems> items) {
        mData = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w185"+mData.get(position).getPosterPath())
                .into(viewHolder.imgItemMovieSearch);
        viewHolder.tvMovieName.setText(mData.get(position).getMovieTitle());
        viewHolder.tvMovieDesc.setText(mData.get(position).getMovieOverview());
        viewHolder.tvMovieDate.setText(mData.get(position).getReleaseDate());

        viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(MovieDetailFragment.EXTRA_ID, mData.get(position).getId());
                bundle.putString(MovieDetailFragment.EXTRA_IMG, mData.get(position).getPosterPath());
                bundle.putString(MovieDetailFragment.EXTRA_NAME, mData.get(position).getMovieTitle());
                bundle.putString(MovieDetailFragment.EXTRA_DATE, mData.get(position).getReleaseDate());
                bundle.putString(MovieDetailFragment.EXTRA_DESC, mData.get(position).getMovieOverview());
                bundle.putString(MovieDetailFragment.EXTRA_LANG, mData.get(position).getMovieLanguage());
                bundle.putString(MovieDetailFragment.EXTRA_POP, mData.get(position).getMoviePopularity());
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager mFragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                ((AppCompatActivity)context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity)context).getSupportActionBar().setTitle(R.string.detail_movie);

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

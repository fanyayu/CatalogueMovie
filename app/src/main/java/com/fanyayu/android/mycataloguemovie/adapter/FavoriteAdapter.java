package com.fanyayu.android.mycataloguemovie.adapter;

import android.content.Context;
import android.database.Cursor;
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
import com.fanyayu.android.mycataloguemovie.entity.FavoriteItems;
import com.fanyayu.android.mycataloguemovie.MovieDetailFragment;
import com.fanyayu.android.mycataloguemovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FaveViewHolder> {

    private Cursor listFaves;
    private Context context;

    public FavoriteAdapter(Context context){
        this.context = context;
    }

    public void setListFaves(Cursor listFaves){
        this.listFaves = listFaves;
    }

    @NonNull
    @Override
    public FaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_search, parent, false);
        FaveViewHolder faveViewHolder = new FaveViewHolder(view);
        return faveViewHolder;
    }

    public class FaveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_moviesearch)
        ImageView imgItemMovieSearch;
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindView(R.id.tv_movie_desc) TextView tvMovieDesc;
        @BindView(R.id.tv_movie_date) TextView tvMovieDate;
        @BindView(R.id.btn_detail)
        Button btnDetail;

        public FaveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FaveViewHolder holder, int position) {
        final FavoriteItems fave = getItem(position);
        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w185"+fave.getPosterpath())
                .into(holder.imgItemMovieSearch);
        holder.tvMovieName.setText(fave.getTitle());
        holder.tvMovieDesc.setText(fave.getOverview());
        holder.tvMovieDate.setText(fave.getDate());

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(MovieDetailFragment.EXTRA_ID, fave.getId());
                bundle.putString(MovieDetailFragment.EXTRA_IMG, fave.getPosterpath());
                bundle.putString(MovieDetailFragment.EXTRA_NAME, fave.getTitle());
                bundle.putString(MovieDetailFragment.EXTRA_DATE, fave.getDate());
                bundle.putString(MovieDetailFragment.EXTRA_DESC, fave.getOverview());
                bundle.putString(MovieDetailFragment.EXTRA_LANG, fave.getLanguage());
                bundle.putString(MovieDetailFragment.EXTRA_POP, fave.getPopularity());
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
    public int getItemCount() {
        if (listFaves == null) return 0;
        return listFaves.getCount();
    }

    private FavoriteItems getItem(int position){
        if (!listFaves.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new FavoriteItems(listFaves);
    }

}

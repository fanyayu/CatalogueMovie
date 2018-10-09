package com.fanyayu.android.mycataloguemovie;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanyayu.android.mycataloguemovie.adapter.FavoriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.rv_fave)
    RecyclerView rvFave;
    private Cursor list;
    FavoriteAdapter adapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new FavoriteAdapter(getContext());
        adapter.notifyDataSetChanged();

        rvFave.setAdapter(adapter);
        rvFave.setHasFixedSize(true);
        rvFave.setLayoutManager(new LinearLayoutManager(getContext()));
        new LoadFaveAsync().execute();

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    private class LoadFaveAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            adapter.setListFaves(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showSnackbarMessage(getString(R.string.no_data));
            }
        }
        private void showSnackbarMessage(String message){
            Snackbar.make(rvFave, message, Snackbar.LENGTH_SHORT).show();
        }
    }
}

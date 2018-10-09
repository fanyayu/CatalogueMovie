package com.fanyayu.android.mycataloguemovie.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.fanyayu.android.mycataloguemovie.db.DatabaseContract;

import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns._ID;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.getColumnInt;
import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.getColumnString;

public class FavoriteItems implements Parcelable{
    private int id;
    private String title;
    private String overview;
    private String posterpath;
    private String date;
    private String language;
    private String popularity;

    public FavoriteItems(){

    }

    protected FavoriteItems(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        posterpath = in.readString();
        date = in.readString();
        language = in.readString();
        popularity = in.readString();
    }

    public FavoriteItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavColumns.OVERVIEW);
        this.posterpath = getColumnString(cursor, DatabaseContract.FavColumns.POSTERPATH);
        this.date = getColumnString(cursor, DatabaseContract.FavColumns.RELEASEDATE);
        this.language = getColumnString(cursor, DatabaseContract.FavColumns.LANGUAGE);
        this.popularity = getColumnString(cursor, DatabaseContract.FavColumns.POPULARITY);
    }

    public static final Creator<FavoriteItems> CREATOR = new Creator<FavoriteItems>() {
        @Override
        public FavoriteItems createFromParcel(Parcel in) {
            return new FavoriteItems(in);
        }

        @Override
        public FavoriteItems[] newArray(int size) {
            return new FavoriteItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterpath);
        dest.writeString(date);
        dest.writeString(language);
        dest.writeString(popularity);
    }
}

package com.fanyayu.android.myfavoritemovie.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.fanyayu.android.myfavoritemovie.database.DatabaseContract;

import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.OVERVIEW;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.RELEASEDATE;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns.TITLE;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.FavColumns._ID;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.getColumnInt;
import static com.fanyayu.android.myfavoritemovie.database.DatabaseContract.getColumnString;

public class FaveItem implements Parcelable {
    private int id;
    private String title, overview, date;

    public FaveItem(){

    }

    public static final Creator<FaveItem> CREATOR = new Creator<FaveItem>() {
        @Override
        public FaveItem createFromParcel(Parcel in) {
            return new FaveItem(in);
        }

        @Override
        public FaveItem[] newArray(int size) {
            return new FaveItem[size];
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.date);
    }

    public FaveItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavColumns.TITLE);
        this.date = getColumnString(cursor, DatabaseContract.FavColumns.RELEASEDATE);
        this.overview = getColumnString(cursor, DatabaseContract.FavColumns.OVERVIEW);
    }

    protected FaveItem(Parcel in){
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.overview = in.readString();
    }
}

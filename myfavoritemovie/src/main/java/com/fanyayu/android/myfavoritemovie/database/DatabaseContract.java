package com.fanyayu.android.myfavoritemovie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAV = "favorite";

    public static final class FavColumns implements BaseColumns {
        public static String _ID = "_id";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTERPATH = "posterpath";
        public static String RELEASEDATE = "releasedate";
        public static String LANGUAGE = "language";
        public static String POPULARITY = "popularity";
    }

    public static final String AUTHORITY = "com.fanyayu.android.mycataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(TABLE_FAV).build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt (Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}

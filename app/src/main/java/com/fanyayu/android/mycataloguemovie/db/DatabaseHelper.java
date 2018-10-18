package com.fanyayu.android.mycataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fanyayu.android.mycataloguemovie.db.DatabaseContract.FavColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavemovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAV = String.format("CREATE TABLE %s"
            + " (%s INT PRIMARY KEY,"
            + " %s TEXT NOT NULL,"
            + " %s TEXT NOT NULL,"
            + " %s TEXT NOT NULL,"
            + " %s TEXT NOT NULL,"
            + " %s TEXT NOT NULL,"
            + " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAV,
            FavColumns._ID,
            FavColumns.TITLE,
            FavColumns.OVERVIEW,
            FavColumns.POSTERPATH,
            FavColumns.RELEASEDATE,
            FavColumns.LANGUAGE,
            FavColumns.POPULARITY);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAV);
        onCreate(db);
    }
}

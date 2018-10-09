package com.fanyayu.android.mycataloguemovie.entity;

import org.json.JSONObject;

import java.util.ArrayList;

public class MovieItems extends ArrayList {
    private int id;
    private String posterPath;
    private String movieTitle;
    private String movieOverview;
    private String releaseDate;
    private String movieLanguage;
    private String moviePopularity;

    public MovieItems(JSONObject object){
        try {
            int id = object.getInt("id");
            String movieTitle = object.getString("title");
            String movieOverview = object.getString("overview");
            String releaseDate = object.getString("release_date");
            String posterPath = object.getString("poster_path");
            String movieLanguage = object.getString("original_language");
            String moviePopularity = object.getString("popularity");
            this.id = id;
            this.movieTitle = movieTitle;
            this.movieOverview = movieOverview;
            this.posterPath = posterPath;
            this.releaseDate = releaseDate;
            this.movieLanguage = movieLanguage;
            this.moviePopularity = moviePopularity;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public String getMoviePopularity() {
        return moviePopularity;
    }

    public void setMoviePopularity(String moviePopularity) {
        this.moviePopularity = moviePopularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

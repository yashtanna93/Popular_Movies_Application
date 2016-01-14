package com.example.yash.popularmovies.models;

/**
 * Created by Yash on 12/28/2015.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    @SerializedName("results")
    public List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public Movie getEachMovie(int i) {
        return movies.get(i);
    }

    public int getSize() {
        return movies.size();
    }

    public class Movie {
        @SerializedName("original_title")
        private String movieTitle;
        @SerializedName("vote_average")
        private double movieVoteAverage;
        @SerializedName("overview")
        private String movieOverview;
        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("adult")
        private boolean isAdult;
        @SerializedName("genre_ids")
        private List<Integer> genres;
        @SerializedName("id")
        private int id;
        @SerializedName("original_language")
        private String language;
        @SerializedName("popularity")
        private double popularity;
        @SerializedName("video")
        private boolean isVideo;
        @SerializedName("release_date")
        private String releaseDate;

        public String getMovieTitle() {
            return movieTitle;
        }

        public void setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
        }

        public double getMovieVoteAverage() {
            return movieVoteAverage;
        }

        public void setMovieVoteAverage(double movieVoteAverage) {
            this.movieVoteAverage = movieVoteAverage;
        }

        public String getMovieOverview() {
            return movieOverview;
        }

        public void setMovieOverview(String movieOverview) {
            this.movieOverview = movieOverview;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public boolean isAdult() {
            return isAdult;
        }

        public void setIsAdult(boolean isAdult) {
            this.isAdult = isAdult;
        }

        public List<Integer> getGenres() {
            return genres;
        }

        public void setGenres(List<Integer> genres) {
            this.genres = genres;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public boolean isVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }
    }
}

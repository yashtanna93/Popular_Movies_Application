package com.example.yash.popularmovies.utils;

import com.example.yash.popularmovies.models.Movies;

/**
 * Created by Yash on 01/14/2016.
 */
public class CreateMovieData {
    String[] movieData = new String[5];
    public CreateMovieData(Movies movieList, int position) {
        movieData[0] = movieList.getEachMovie(position).getMovieTitle();
        movieData[1] = movieList.getEachMovie(position).getPosterPath();
        movieData[2] = movieList.getEachMovie(position).getMovieOverview();
        movieData[3] = Double.toString(movieList.getEachMovie(position)
                .getMovieVoteAverage());
        movieData[4] = movieList.getEachMovie(position).getReleaseDate();
    }
    String[] getMovieData() {
        return movieData;
    }
}

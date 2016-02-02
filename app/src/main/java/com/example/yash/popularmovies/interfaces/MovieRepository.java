package com.example.yash.popularmovies.interfaces;

import com.example.yash.popularmovies.models.MovieReview;
import com.example.yash.popularmovies.models.Genres;
import com.example.yash.popularmovies.models.Movie;
import com.example.yash.popularmovies.models.Sort;
import com.example.yash.popularmovies.models.Video;

import java.util.List;

import rx.Observable;

/**
 * Created by Yash on 01/28/2016.
 */
public interface MovieRepository {

    Observable<List<Movie>> discoverMovies(Sort sort, int page);

    Observable<List<MovieReview>> getMovieReview(long id);

    Observable<List<Genres>> getListOfGenres();

    Observable<List<Video>> videos(long movieId);

    void saveMovie(Movie movie);

    Observable<List<Movie>> savedMovies();

    void saveGenre(Genres genre);

    Observable<List<Genres>> savedGenres();
}

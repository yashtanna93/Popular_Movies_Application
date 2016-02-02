package com.example.yash.popularmovies.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.util.Log;

import com.example.yash.popularmovies.BuildConfig;
import com.example.yash.popularmovies.models.MovieReview;
import com.example.yash.popularmovies.providers.MoviesContract;
import com.example.yash.popularmovies.interfaces.MovieRepository;
import com.example.yash.popularmovies.interfaces.MoviesAPI;
import com.example.yash.popularmovies.models.Genres;
import com.example.yash.popularmovies.models.Movie;
import com.example.yash.popularmovies.models.Sort;
import com.example.yash.popularmovies.models.Video;
import com.squareup.sqlbrite.BriteContentResolver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Yash on 01/28/2016.
 */
public class MovieRepositoryImplement implements MovieRepository {

    private final MoviesAPI mMovieApi;
    private final ContentResolver mContentResolver;
    private final BriteContentResolver mBriteContentResolver;

    public MovieRepositoryImplement(MoviesAPI mMovieApi,
                                    ContentResolver mContentResolver,
                                    BriteContentResolver mBriteContentResolver) {

        this.mMovieApi = mMovieApi;
        this.mContentResolver = mContentResolver;

        this.mBriteContentResolver = mBriteContentResolver;
    }

    @Override
    public Observable<List<Movie>> discoverMovies(Sort sort, int page) {
        Log.i("popMoviesCount",Integer.toString(page));
        return mMovieApi.discoverMovies(BuildConfig.TMDB_API_KEY, sort, page)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .map(new Func1<Movie.Response, List<Movie>>() {
                    @Override
                    public List<Movie> call(Movie.Response response) {
                        return response.movies;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MovieReview>> getMovieReview(long id) {
        return mMovieApi.getReview(id, BuildConfig.TMDB_API_KEY)
                .timeout(5,TimeUnit.SECONDS)
                .retry(2)
                .map(new Func1<MovieReview.Response, List<MovieReview>>() {
                    @Override
                    public List<MovieReview> call(MovieReview.Response response) {
                        return response.reviews;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Genres>> getListOfGenres() {
        return mMovieApi.getGenres(BuildConfig.TMDB_API_KEY)
                .timeout(5,TimeUnit.SECONDS)
                .retry(2)
                .map(new Func1<Genres.Response, List<Genres>>() {
                    @Override
                    public List<Genres> call(Genres.Response response) {
                        return response.genres;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Video>> videos(long movieId) {
        return mMovieApi.videos(movieId, BuildConfig.TMDB_API_KEY)
                .timeout(2, TimeUnit.SECONDS)
                .retry(2)
                .map(new Func1<Video.Response, List<Video>>() {
                    @Override
                    public List<Video> call(Video.Response response) {
                        return response.videos;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Movie>> savedMovies() {
        return mBriteContentResolver.createQuery(MoviesContract.MoviesDBHelper.CONTENT_URI,
                Movie.PROJECTION, null, null, MoviesContract.MoviesDBHelper.DEFAULT_SORT, true)
                .map(Movie.PROJECTION_MAP)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Genres>> savedGenres() {
        return mBriteContentResolver.createQuery(MoviesContract.GenresDBHelper.CONTENT_URI,
                Genres.PROJECTION, null, null, MoviesContract.GenresDBHelper.DEFAULT_SORT, true)
                .map(Genres.PROJECTION_LIST)
                .subscribeOn(Schedulers.io());
    }

    //save movies
    @Override
    public void saveMovie(Movie movie) {
        AsyncQueryHandler handler = new AsyncQueryHandler(mContentResolver) {};
        handler.startInsert(-1, null, MoviesContract.MoviesDBHelper.CONTENT_URI, new Movie.Builder()
                .movie(movie)
                .build());
    }


    //save genres
    @Override
    public void saveGenre(Genres genre) {

        AsyncQueryHandler handler = new AsyncQueryHandler(mContentResolver) {};
        handler.startInsert(-1,null, MoviesContract.GenresDBHelper.CONTENT_URI, new Genres.Builder().
                id((int) genre.getId()).name(genre.getName()).build());

    }



}

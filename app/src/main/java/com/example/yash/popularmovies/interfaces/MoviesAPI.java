package com.example.yash.popularmovies.interfaces;



import com.example.yash.popularmovies.models.MovieReview;
import com.example.yash.popularmovies.models.Genres;
import com.example.yash.popularmovies.models.Movie;
import com.example.yash.popularmovies.models.Sort;
import com.example.yash.popularmovies.models.Video;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Yash on 01/28/2016.
 */
public interface MoviesAPI {

    //get movies data
    @GET("discover/movie") Observable<Movie.Response> discoverMovies(
            @Query("api_key") String api_key,
            @Query("sort_by") Sort sort,
            @Query("page") int page);

    //get movies data with adult
    @GET("discover/movie")
    Observable<Movie.Response> discoverMovies(
            @Query("api_key") String api_key,
            @Query("sort_by") Sort sort,
            @Query("page") int page,
            @Query("include_adult") boolean includeAdult);

    //reviews get api
    @GET("movie/{id}/reviews") Observable<MovieReview.Response> getReview(
            @Path("id") long id,
            @Query("api_key") String api_key
    );

    //genres call
    @GET("genre/movie/list")
    Observable<Genres.Response> getGenres(
            @Query("api_key") String api_key
    );

    //get trailers
    @GET("movie/{id}/videos") Observable<Video.Response> videos(
            @Path("id") long movieId,
            @Query("api_key") String api_key);

}

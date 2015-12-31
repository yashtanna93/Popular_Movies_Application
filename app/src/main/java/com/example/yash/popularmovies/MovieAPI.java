package com.example.yash.popularmovies;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Yash on 12/28/2015.
 */
public interface MovieAPI {
    @GET("/3/movie/{sort_order}")
    Observable<Movies> loadMovies(@Path("sort_order") String sortOrder, @Query
            ("api_key") String API_KEY, @Query("page") String page);
}

package com.example.yash.popularmovies.network;

import com.example.yash.popularmovies.interfaces.MoviesAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Yash on 01/28/2016.
 */
@Module
public class DataProviderModule {

    @Provides
    @Singleton
    protected MoviesAPI providesMoviesAPIService(Retrofit retrofit) {
        return retrofit.create(MoviesAPI.class);
    }


}

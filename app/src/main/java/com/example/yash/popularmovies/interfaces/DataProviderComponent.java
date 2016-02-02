package com.example.yash.popularmovies.interfaces;

import com.example.yash.popularmovies.network.ApiModule;
import com.example.yash.popularmovies.network.DataProviderModule;
import com.example.yash.popularmovies.providers.MoviesProviderModule;
import com.example.yash.popularmovies.fragment.MovieDetailFragment;
import com.example.yash.popularmovies.fragment.MovieFavoredFragment;
import com.example.yash.popularmovies.fragment.MovieListSortedFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Yash on 01/28/2016.
 */
@Singleton
@Component(modules={ApiModule.class, DataProviderModule.class, MoviesProviderModule.class})
public interface DataProviderComponent {
    void inject(MovieListSortedFragment fragment);
    void inject(MovieDetailFragment fragment);
    void inject(MovieFavoredFragment fragment);
}
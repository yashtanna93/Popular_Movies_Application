package com.example.yash.popularmovies.fragment;

import android.content.ContentResolver;
import android.support.annotation.IdRes;
import android.util.Log;

import com.example.yash.popularmovies.models.Movie;
import com.example.yash.popularmovies.utils.MovieRepositoryImplement;
import com.example.yash.popularmovies.interfaces.MoviesAPI;
import com.example.yash.popularmovies.providers.PopularMoviesApplication;
import com.squareup.sqlbrite.BriteContentResolver;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

/**
 * Created by Yash on 01/28/2016.
 */

public class MovieFavoredFragment extends MovieListFragment {

    private Subscription mFavoredSubscription = Subscriptions.empty();

    @Inject
    MoviesAPI apiService;

    @Inject
    ContentResolver mContentResolver;

    @Inject
    BriteContentResolver mBriteContentResolver;

    @Override
    public void onStop() {
        super.onStop();
        mFavoredSubscription.unsubscribe();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((PopularMoviesApplication) getActivity().getApplication()).getDataProviderComponent().inject(this);
        subscribeToMovies();
    }

    private void subscribeToMovies() {
        MovieRepositoryImplement mMoviesRepository = new MovieRepositoryImplement(apiService, mContentResolver, mBriteContentResolver);
        mViewAnimator.setDisplayedChildId(ANIMATOR_VIEW_LOADING);
        mFavoredSubscription.unsubscribe();

        mFavoredSubscription = mMoviesRepository.savedMovies().
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movieDBs) {
                        Log.i("Popular Movies App", Integer.valueOf(movieDBs.size()).toString());
                        for(Movie movies : movieDBs) {
                            Log.i("movie name",movies.getTitle());
                        }
                        newAdapter.add(movieDBs);
                        newAdapter.notifyDataSetChanged();
                        mViewAnimator.setDisplayedChildId(getContentView());


                    }
                });
    }

    @IdRes
    protected final int getContentView() {
        return  ANIMATOR_VIEW_CONTENT;
    }
}

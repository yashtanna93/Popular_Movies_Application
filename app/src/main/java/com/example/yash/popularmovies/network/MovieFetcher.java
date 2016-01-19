package com.example.yash.popularmovies.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yash.popularmovies.BuildConfig;
import com.example.yash.popularmovies.adapters.RecyclerViewAdapter;
import com.example.yash.popularmovies.interfaces.MovieAPI;
import com.example.yash.popularmovies.models.Movies;

import java.util.ArrayList;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Yash on 12/12/2015.
 */
public class MovieFetcher {

    GridLayoutManager gridLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> movieImages = new ArrayList<String>();
    RecyclerView recyclerView;
    Context context;
    Movies movieList = new Movies();

    public MovieFetcher(Context context) {
        this.context = context;
    }

    public void setGridLayoutManager(int spansize) {
        this.gridLayoutManager = new GridLayoutManager(context, spansize);
    }

    public void setRecyclerViewAndAdapter(View view) {
        recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context, movieImages);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void changeSortOrder(String sortOrder, int pageNumber) {
        recyclerViewAdapter.clear();
        fetchMovies(sortOrder, pageNumber);
    }

    public void fetchMovies(String sortOrder, int pageNumber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        Observable<Movies> observable = movieAPI.loadMovies(sortOrder,
                BuildConfig.TMDB_API_KEY, Integer.toString(pageNumber));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Movies>() {
                    @Override
                    public void onCompleted() {
                        recyclerViewAdapter.notifyDataSetChanged();
                        recyclerViewAdapter.updateMovieList(movieList);
                    }

                    @Override

                    public void onError(Throwable e) {
                        Log.v("FetcherError", e.getLocalizedMessage().toString());
                    }

                    @Override
                    public void onNext(Movies movies) {
                        for (Movies.Movie m : movies.getMovies()) {
                            movieImages.add(m.getPosterPath());
//                            Log.v("MovieAdded:", m.getMovieTitle());
                        }
                        movieList.movies.addAll(movies.getMovies());
//                        Log.v("MovieSize", Integer.toString(movieList.getSize()));
                    }
                });
    }

    public void setOnClickOnScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int pagecount = 1;
            SharedPreferences sharedpreferences = PreferenceManager
                    .getDefaultSharedPreferences
                            (context);
            String sortOrder = sharedpreferences
                    .getString("SortOrder", "popular");

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.v("OnScrollTest", Integer.toString(gridLayoutManager.findLastVisibleItemPosition()));
                Log.v("MovieImages", Integer.toString(movieImages.size()));
                if (gridLayoutManager.findLastVisibleItemPosition() >
                        movieImages.size() - 5) {
                    pagecount += 1;
                    fetchMovies(sortOrder, pagecount);
                    Log.v("Fetching Movies", Integer.toString(pagecount));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

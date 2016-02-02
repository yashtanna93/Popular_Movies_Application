package com.example.yash.popularmovies.providers;

import android.app.Application;
import android.content.Context;

import com.example.yash.popularmovies.interfaces.DaggerDataProviderComponent;
import com.example.yash.popularmovies.interfaces.DataProviderComponent;
import com.example.yash.popularmovies.network.ApiModule;
import com.example.yash.popularmovies.network.DataProviderModule;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Created by Yash on 01/28/2016.
 */
public class PopularMoviesApplication extends Application {

    private DataProviderComponent dataProviderComponent;
    private RefWatcher refWatcher;


    public static PopularMoviesApplication get(Context context) {
        return (PopularMoviesApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = installLeakCanary();

        Timber.plant(new Timber.DebugTree());

        initializeInjector();
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    protected RefWatcher installLeakCanary() {
        //return LeakCanary.install(this);
        return RefWatcher.DISABLED;
    }

    private void initializeInjector() {
        dataProviderComponent = DaggerDataProviderComponent.builder()
                .dataProviderModule(new DataProviderModule())
                .apiModule(new ApiModule())
                .moviesProviderModule(new MoviesProviderModule(this))
                .build();
    }

    public DataProviderComponent getDataProviderComponent() {
        return dataProviderComponent;
    }

}

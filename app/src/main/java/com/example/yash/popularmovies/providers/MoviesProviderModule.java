package com.example.yash.popularmovies.providers;

import android.app.Application;
import android.content.ContentResolver;

import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by Yash on 01/28/2016.
 */
@Module
public class MoviesProviderModule {

    Application mApplication;

    public MoviesProviderModule(Application app) {
        this.mApplication = app;
    }

    @Provides
    @Singleton
    protected SqlBrite provideSqlBrite() {
        return SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Timber.tag("Database").v(message);
            }
        });
    }

    @Provides
    @Singleton
    protected ContentResolver provideContentResolver() {
        return this.mApplication.getContentResolver();
    }

    @Provides
    @Singleton
    protected BriteContentResolver provideBrideContentResolver
            (SqlBrite sqlBrite, ContentResolver contentResolver) {
        return sqlBrite.wrapContentProvider(contentResolver);
    }
}

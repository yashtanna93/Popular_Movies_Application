package com.example.yash.popularmovies.network;

import com.example.yash.popularmovies.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Yash on 01/28/2016.
 */
@Module
public class ApiModule {

    @Provides @Singleton
    public OkHttpClient provideClient() {

        return new OkHttpClient();

    }

    @Provides @Singleton
    public Retrofit providesRetrofit(OkHttpClient client) {

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}

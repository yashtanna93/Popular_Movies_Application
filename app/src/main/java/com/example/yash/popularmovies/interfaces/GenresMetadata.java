package com.example.yash.popularmovies.interfaces;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;

import com.example.yash.popularmovies.utils.Helper;
import com.example.yash.popularmovies.providers.MoviesContract;
import com.example.yash.popularmovies.models.Genres;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by Yash on 01/28/2016.
 */
public interface GenresMetadata {

    //possible projections for genres
    String[] PROJECTION = {
            MoviesContract.GenresDBHelper._ID,
            MoviesContract.GenresDBHelper.GENRE_ID,
            MoviesContract.GenresDBHelper.GENRE_NAME
    };

    Func1<SqlBrite.Query, List<Genres>> PROJECTION_LIST = new Func1<SqlBrite.Query, List<Genres>>() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public List<Genres> call(SqlBrite.Query query) {
            try (Cursor cursor = query.run()) {
                List<Genres> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    values.add(new Genres((int)
                            (Helper.getLong(cursor, MoviesContract.GenresDBHelper.GENRE_ID)),
                            Helper.getString(cursor, MoviesContract.GenresDBHelper.GENRE_NAME)));
                }
                return values;
            }
        }
    };

    final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(int id) {

            values.put(MoviesContract.GenresColumns.GENRE_ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(MoviesContract.GenresColumns.GENRE_NAME, name);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}

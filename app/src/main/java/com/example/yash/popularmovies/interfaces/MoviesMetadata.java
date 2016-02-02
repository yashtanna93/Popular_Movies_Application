package com.example.yash.popularmovies.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.yash.popularmovies.utils.Helper;
import com.example.yash.popularmovies.providers.MoviesContract;
import com.example.yash.popularmovies.models.Movie;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by Yash on 01/28/2016.
 */
public interface MoviesMetadata {

    String [] PROJECTION = {
            MoviesContract.MoviesDBHelper._ID,
            MoviesContract.MoviesDBHelper.MOVIE_ID,
            MoviesContract.MoviesDBHelper.MOVIE_TITLE,
            MoviesContract.MoviesDBHelper.MOVIE_OVERVIEW,
            MoviesContract.MoviesDBHelper.MOVIE_GENRE_IDS,
            MoviesContract.MoviesDBHelper.MOVIE_POSTER_PATH,
            MoviesContract.MoviesDBHelper.MOVIE_POPULARITY,
            MoviesContract.MoviesDBHelper.MOVIE_BACKDROP_PATH,
            MoviesContract.MoviesDBHelper.MOVIE_RELEASE_DATE,
            MoviesContract.MoviesDBHelper.MOVIE_FAVORED,
            MoviesContract.MoviesDBHelper.MOVIE_VOTE_AVERAGE,
            MoviesContract.MoviesDBHelper.MOVIE_VOTE_COUNT
    };

    Func1<SqlBrite.Query, List<Movie>> PROJECTION_MAP = new Func1<SqlBrite.Query, List<Movie>>() {
        @Override
        public List<Movie> call(SqlBrite.Query query) {

            Cursor cursor = query.run();

            List<Movie> values = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {


                values.add(new Movie().setId(Helper.getLong(cursor, MoviesContract.MoviesDBHelper.MOVIE_ID))
                        .setTitle(Helper.getString(cursor, MoviesContract.MoviesDBHelper.MOVIE_TITLE))
                        .setOverview(Helper.getString(cursor, MoviesContract.MoviesDBHelper.MOVIE_OVERVIEW))
                        .putGenreIdsList(Helper.getString(cursor, MoviesContract.MoviesDBHelper.MOVIE_GENRE_IDS))
                        .setPosterPath(Helper.getString(cursor, MoviesContract.MoviesDBHelper.MOVIE_POSTER_PATH))
                        .setBackdropPath(Helper.getString(cursor, MoviesContract.MoviesDBHelper.MOVIE_BACKDROP_PATH))
                        .setFavored(Helper.getBoolean(cursor, MoviesContract.MoviesDBHelper.MOVIE_FAVORED))
                        .setPopularity(Helper.getDouble(cursor, MoviesContract.MoviesDBHelper.MOVIE_POPULARITY))
                        .setVoteCount(Helper.getInt(cursor, MoviesContract.MoviesDBHelper.MOVIE_VOTE_COUNT))
                        .setVoteAverage(Helper.getDouble(cursor, MoviesContract.MoviesDBHelper.MOVIE_VOTE_AVERAGE))
                        .setReleaseDate(Helper.getString(cursor, MoviesContract.MoviesDBHelper.MOVIE_RELEASE_DATE)));
            }
            return values;

        }
    };

    final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_ID, id);
            return this;
        }

        public Builder title(String title) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_TITLE, title);
            return this;
        }

        public Builder overview(String overview) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_OVERVIEW, overview);
            return this;
        }

        public Builder genreIds(String genreIds) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_GENRE_IDS, genreIds);
            return this;
        }

        public Builder backdropPath(String backdropPath) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_BACKDROP_PATH, backdropPath);
            return this;
        }

        public Builder posterPath(String posterPath) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_POSTER_PATH, posterPath);
            return this;
        }

        public Builder voteCount(long voteCount) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_VOTE_COUNT, voteCount);
            return this;
        }

        public Builder voteAverage(double voteCount) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_VOTE_AVERAGE, voteCount);
            return this;
        }

        public Builder popularity(double popularity) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_POPULARITY, popularity);
            return this;
        }

        public Builder favored(boolean favored) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_FAVORED, favored);
            return this;
        }

        public Builder releaseDate(String date) {
            values.put(MoviesContract.MoviesDBHelper.MOVIE_RELEASE_DATE, date);
            return this;
        }

        public Builder movie(Movie movie) {
            return id(movie.getId())
                    .title(movie.getTitle())
                    .overview(movie.getOverview())
                    .genreIds(movie.makeGenreIdsList())
                    .backdropPath(movie.getBackdropPath())
                    .posterPath(movie.getPosterPath())
                    .popularity(movie.getPopularity())
                    .voteCount(movie.getVoteCount())
                    .voteAverage(movie.getVoteAverage())
                    .voteAverage(movie.getVoteAverage())
                    .releaseDate(movie.getReleaseDate())
                    .favored(movie.isFavored());
        }

        public ContentValues build() {
            return values;
        }
    }

}

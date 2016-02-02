package com.example.yash.popularmovies.activity;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.example.yash.popularmovies.models.Movie;
import com.example.yash.popularmovies.R;
import com.example.yash.popularmovies.fragment.MovieDetailFragment;

/**
 * Created by Yash on 01/28/2016.
 */
public class MovieDetailActivity extends BaseTemplateActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private static final String MOVIE_FRAGMENT_TAG = "fragment_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        if (mToolbar != null) {
            ViewCompat.setElevation(mToolbar, getResources().getDimension(R.dimen.toolbar_elevation));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieDetailActivity.this.finish();
                }
            });

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setDisplayShowHomeEnabled(true);
            }
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (savedInstanceState == null) {
            MovieDetailFragment fragment = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, fragment, MOVIE_FRAGMENT_TAG)
                    .commit();
        }
    }
}

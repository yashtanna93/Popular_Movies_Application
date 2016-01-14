package com.example.yash.popularmovies.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yash.popularmovies.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    String MOVIE_MESSAGE = "Passing Movie Data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String API_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = this.getIntent().getExtras();
        String[] movieDetails = b.getStringArray(MOVIE_MESSAGE);
        TextView movieName = ButterKnife.findById(this, R.id.moviename);
        movieName.setText(movieDetails[0]);
        TextView overview = ButterKnife.findById(this, R.id.overview);
        overview.setText(movieDetails[2]);
        TextView rating = ButterKnife.findById(this, R.id.rating);
        rating.setText(movieDetails[3] + "/10");
        TextView year = ButterKnife.findById(this, R.id.year);
        year.setText(movieDetails[4].substring(0, 4));
        Uri moviePosterURI = Uri.parse(API_BASE_IMAGE_URL + movieDetails[1])
                .buildUpon().build();

        ImageView imageView = ButterKnife.findById(this, R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(getApplicationContext()).load(moviePosterURI.toString())
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

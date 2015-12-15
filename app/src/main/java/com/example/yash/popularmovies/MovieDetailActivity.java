package com.example.yash.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String API_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle b = this.getIntent().getExtras();
        String[] movieDetails = b.getStringArray(MainActivity.MOVIE_MESSAGE);
//        for(int i=0; i<movieDetails.length; i++) {
//            Log.v("Data1234", movieDetails[i]);
//        }
        TextView movieName = (TextView) findViewById(R.id.moviename);
        movieName.setText(movieDetails[0]);
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(movieDetails[2]);
        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText(movieDetails[3] + "/10");
        TextView year = (TextView) findViewById(R.id.year);
        year.setText(movieDetails[4].substring(0, 4));
        Uri moviePosterURI = Uri.parse(API_BASE_IMAGE_URL + movieDetails[1])
                .buildUpon().build();
        //Log.v("POSTERURI", moviePosterURI.toString());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
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

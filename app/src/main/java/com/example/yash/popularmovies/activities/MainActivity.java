package com.example.yash.popularmovies.activities;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yash.popularmovies.network.MovieFetcher;
import com.example.yash.popularmovies.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String sortOrder;
    MovieFetcher movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popular Movies");
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences
                (getApplicationContext());
        sortOrder = sharedpreferences.getString("SortOrder", "popular");
        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int spansize = width / 540;
        movie = new MovieFetcher(this);
        movie.setGridLayoutManager(spansize);
        movie.setRecyclerViewAndAdapter(findViewById(R.id.recycler_view));
        movie.fetchMovies(sortOrder, 1);
        movie.setOnClickOnScrollListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menuSortNewest) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("SortOrder", "popular");
            editor.commit();
            sortOrder = "popular";
            movie.changeSortOrder(sortOrder, 1);
        }

        if (id == R.id.menuSortRating) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("SortOrder", "top_rated");
            editor.commit();
            sortOrder = "top_rated";
            movie.changeSortOrder(sortOrder, 1);
        }

        return super.onOptionsItemSelected(item);
    }
}

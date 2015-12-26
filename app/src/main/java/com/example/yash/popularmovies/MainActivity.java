package com.example.yash.popularmovies;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_MESSAGE = "Passing Movie Data";
    private RecyclerView.LayoutManager gLayout;
    public static MovieFetcher movie;
    void setView(String order) {
        movie = new MovieFetcher(this, order);
        movie.execute();
        String[] movieImages = movie.getMovieImages();
        String[] movieNames = movie.getMovieNames();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int spansize = (int) width / 540;

        gLayout = new GridLayoutManager(MainActivity.this, spansize);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity
                .this, movieImages, movieNames);
        rView.setAdapter(rcAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popular Movies");

        setView("popular");
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.menuSortNewest) {
            setView("popular");
        }

        if (id == R.id.menuSortRating) {
            setView("top_rated");
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.yash.popularmovies;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.OnScrollListener;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    static final String MOVIE_MESSAGE = "Passing Movie Data";
    public static ArrayList<String[]> result = new ArrayList<String[]>();
    static MovieFetcher movie;
    static ArrayList<String> movieImages = new ArrayList<String>();
    static ArrayList<String> movieNames = new ArrayList<String>();
    static int pagecount = 1;
    static RecyclerViewAdapter rcAdapter;
    RecyclerView rView;
    SharedPreferences sharedpreferences;
    String sortOrder;
    GridLayoutManager gLayout;
    int scrollposition = 0;

    public void setView(String order) throws InterruptedException {
        movieImages = new ArrayList<String>();
        movieNames = new ArrayList<String>();
        result = new ArrayList<String[]>();
        for (int i = 1; i <= pagecount; i++) {
            movie = new MovieFetcher(order, i);
            movie.execute();
            while (movie.getStatus() == AsyncTask.Status.PENDING) {
                Thread.sleep(100);
            }
        }
        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int spansize = (int) width / 540;

        gLayout = new GridLayoutManager(MainActivity.this, spansize);

        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gLayout);

        rcAdapter = new RecyclerViewAdapter(MainActivity.this, movieImages,
                movieNames);
        rView.setAdapter(rcAdapter);

        rView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollposition += dy;
                if (gLayout.findLastCompletelyVisibleItemPosition() ==
                        movieImages.size() - 5) {
                    Log.v("Scroller", "This is called");
                    pagecount += 1;
                    MovieFetcher movie = new MovieFetcher(sortOrder, pagecount);
                    movie.execute();
                    Log.v("MovieImagesize", Integer.toString(movieImages.size()));
                    rcAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popular Movies");
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences
                (getApplicationContext());
        // Log.v("OnCreate", sharedpreferences.getString("SortOrder",
        // "popular"));
        sortOrder = sharedpreferences.getString("SortOrder", "popular");
        try {
            setView(sortOrder);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        if (id == R.id.new_page) {
            if (movie.getStatus() == AsyncTask.Status.FINISHED) {
                pagecount += 1;
                MovieFetcher movie = new MovieFetcher(sortOrder, pagecount);
                movie.execute();
                Log.v("MovieImagesize", Integer.toString(movieImages.size()));
                rcAdapter.notifyDataSetChanged();
            }
        }
        if (id == R.id.menuSortNewest) {
            pagecount = 1;
            try {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("SortOrder", "popular");
                editor.commit();
                Log.v("popular", sharedpreferences.getString("SortOrder",
                        null));
                sortOrder = "popular";
                setView(sortOrder);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (id == R.id.menuSortRating) {
            pagecount = 1;
            try {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("SortOrder", "top_rated");
                editor.commit();
                Log.v("top_rated", sharedpreferences.getString("SortOrder",
                        null));
                sortOrder = "top_rated";
                setView(sortOrder);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

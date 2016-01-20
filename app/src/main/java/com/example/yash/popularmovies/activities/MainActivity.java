package com.example.yash.popularmovies.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.yash.popularmovies.R;
import com.example.yash.popularmovies.fragments.MovieDetailFragment;
import com.example.yash.popularmovies.fragments.MovieListFragment;
import com.example.yash.popularmovies.network.MovieFetcher;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        MovieListFragment.OnDataPass{

    SharedPreferences sharedpreferences;
    String sortOrder = "popular";
    MovieFetcher movie;
    int spansize = 2;
    boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popular Movies");
        twoPane = findViewById(R.id.fragment_container_detail) != null;
        Log.v("Two_Pane", Boolean.toString(twoPane));
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences
                (getApplicationContext());
        sortOrder = sharedpreferences.getString("SortOrder", "popular");
        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            Log.v("LargeScreen", "True");
        } else {
            Log.v("LargeScreen","False");
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        if(twoPane) {
            MovieListFragment movieListFragment = new MovieListFragment();
            fragmentTransaction.replace(R.id.fragment_list,
                    movieListFragment);
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            fragmentTransaction.replace(R.id.fragment_container_detail,
                    movieDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                /**
                 * Landscape mode of the device
                 */
                spansize = 3;
            } else {
                /**
                 * Portrait mode of the device
                 */
                spansize = 2;
            }
            MovieListFragment movieListFragment = new MovieListFragment();
            fragmentTransaction.replace(R.id.fragment_container,
                    movieListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
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

    public int getSpanSize() {
        return spansize;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public View getRecyclerView() {
        return findViewById(R.id.recycler_view);
    }

    @Override
    public void onDataPass(MovieFetcher data) {
        movie = data;
    }
}

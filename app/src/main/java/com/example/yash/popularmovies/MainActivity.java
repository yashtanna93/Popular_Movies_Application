package com.example.yash.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_MESSAGE = "Passing Movie Data";
    void setGrid(String order) {
        final MovieFetcher movie = new MovieFetcher(this, order);
        movie.execute();
        String[] movieImages = movie.getMovieImages();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, movieImages));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String[] movieData = movie.getMovieData(position);
                Bundle b = new Bundle();
                b.putStringArray(MOVIE_MESSAGE, movieData);
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "" + position,
                //        Toast.LENGTH_SHORT).show();
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

        setGrid("popularity.desc");
//        final MovieFetcher movie = new MovieFetcher(this, "popularity.desc");
//        movie.execute();
//        String[] movieImages = movie.getMovieImages();
//
//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this, movieImages));
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                String[] movieData = movie.getMovieData(position);
//                Bundle b = new Bundle();
//                b.putStringArray(MOVIE_MESSAGE, movieData);
//                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
//                intent.putExtras(b);
//                startActivity(intent);
//                //Toast.makeText(MainActivity.this, "" + position,
//                //        Toast.LENGTH_SHORT).show();
//            }
//        });
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
            setGrid("popularity.desc");
        }

        if (id == R.id.menuSortRating) {
            setGrid("vote_average.desc");
        }

        return super.onOptionsItemSelected(item);
    }
}

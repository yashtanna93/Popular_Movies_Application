package com.example.yash.popularmovies.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.yash.popularmovies.R;
import com.example.yash.popularmovies.activities.MovieDetailActivity;
import com.example.yash.popularmovies.models.Movies;

import butterknife.ButterKnife;

/**
 * Created by Yash on 12/25/2015.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView moviePoster;
    Movies movieList;
    String MOVIE_MESSAGE = "Passing Movie Data";
    public RecyclerViewHolders(View itemView, Movies movieList) {
        super(itemView);
        itemView.setOnClickListener(this);
        moviePoster = ButterKnife.findById(itemView, R.id.movie_poster);
        this.movieList = movieList;
    }

    @Override
    public void onClick(View view) {
        CreateMovieData createMovieData = new CreateMovieData(movieList,
                getAdapterPosition());
        String[] movieData = createMovieData.getMovieData();
        Bundle b = new Bundle();
        b.putStringArray(MOVIE_MESSAGE, movieData);
        Intent intent = new Intent(view.getContext(),
                MovieDetailActivity.class);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}

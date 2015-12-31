package com.example.yash.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
        moviePoster = (ImageView)itemView.findViewById(R.id.movie_poster);
        this.movieList = movieList;
    }

    @Override
    public void onClick(View view) {
        Log.v("Size", movieList.getEachMovie(getAdapterPosition()).getMovieTitle());
        String[] movieData = new String[5];
        movieData[0] = movieList.getEachMovie(getAdapterPosition()).getMovieTitle();
        movieData[1] = movieList.getEachMovie(getAdapterPosition()).getPosterPath();
        movieData[2] = movieList.getEachMovie(getAdapterPosition()).getMovieOverview();
        movieData[3] = Double.toString(movieList.getEachMovie(getAdapterPosition())
                .getMovieVoteAverage());
        movieData[4] = movieList.getEachMovie(getAdapterPosition()).getReleaseDate();
        Bundle b = new Bundle();
        b.putStringArray(MOVIE_MESSAGE, movieData);
        Intent intent = new Intent(view.getContext(), MovieDetailActivity
                .class);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}

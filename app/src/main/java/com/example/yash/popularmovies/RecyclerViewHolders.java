package com.example.yash.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Yash on 12/25/2015.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView movieName;
    public ImageView moviePoster;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        movieName = (TextView)itemView.findViewById(R.id.movie_name);
        moviePoster = (ImageView)itemView.findViewById(R.id.movie_poster);
    }

    @Override
    public void onClick(View view) {
        String[] movieData = MainActivity.movie.getMovieData(getAdapterPosition());
        Bundle b = new Bundle();
        b.putStringArray(MainActivity.MOVIE_MESSAGE, movieData);
        Intent intent = new Intent(view.getContext(), MovieDetailActivity
                .class);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}

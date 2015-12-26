package com.example.yash.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

/**
 * Created by Yash on 12/25/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private String[] movieImages;
    private String[] movieNames;
    private Context context;
    String API_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    public RecyclerViewAdapter(Context context, String[] movieImages,
                               String[] movieNames) {
        this.movieImages = movieImages;
        this.movieNames = movieNames;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R
                .layout.content_cards, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        String movieImage = movieImages[position];
        Uri imageUri = Uri.parse(API_BASE_IMAGE_URL + movieImage)
                .buildUpon().build();
        if("null".equals(movieImage)) {
            Picasso.with(context).load(R.drawable.notavailable).into(holder
                    .moviePoster);
            holder.movieName.setText(movieNames[position]);
        } else {
            Picasso.with(context).load(imageUri.toString()).resize(185,630)
                    .into(holder.moviePoster);
            holder.movieName.setText(movieNames[position]);
        }
    }

    @Override
    public int getItemCount() {
        return this.movieImages.length;
    }
}

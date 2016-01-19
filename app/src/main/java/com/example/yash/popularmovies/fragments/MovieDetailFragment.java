package com.example.yash.popularmovies.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yash.popularmovies.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

/**
 * Created by Yash on 01/19/2016.
 */
public class MovieDetailFragment extends Fragment {
    String API_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    String MOVIE_MESSAGE = "Passing Movie Data";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle b = getActivity().getIntent().getExtras();
        String[] movieDetails = b.getStringArray(MOVIE_MESSAGE);
        TextView movieName = ButterKnife.findById(getActivity(), R.id.moviename);
        movieName.setText(movieDetails[0]);
        TextView overview = ButterKnife.findById(getActivity(), R.id.overview);
        overview.setText(movieDetails[2]);
        TextView rating = ButterKnife.findById(getActivity(), R.id.rating);
        rating.setText(movieDetails[3] + "/10");
        TextView year = ButterKnife.findById(getActivity(), R.id.year);
        year.setText(movieDetails[4].substring(0, 4));
        Uri moviePosterURI = Uri.parse(API_BASE_IMAGE_URL + movieDetails[1])
                .buildUpon().build();

        ImageView imageView = ButterKnife.findById(getActivity(), R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(getContext()).load(moviePosterURI.toString())
                .into(imageView);
    }
}

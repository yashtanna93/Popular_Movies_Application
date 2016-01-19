package com.example.yash.popularmovies.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yash.popularmovies.R;
import com.example.yash.popularmovies.activities.MainActivity;
import com.example.yash.popularmovies.network.MovieFetcher;

public class MovieListFragment extends Fragment {

    String sortOrder;
    MovieFetcher movie;

    public MovieListFragment() {
        Log.v("FragmentLog", "FragmentCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("FragmentLog", "FragmentEntered");
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("FragmentLog", "ViewCreated");
        MainActivity mainActivity = (MainActivity) getActivity();
        int spansize = mainActivity.getSpanSize();
        sortOrder = mainActivity.getSortOrder();
        movie = new MovieFetcher(getActivity());
        movie.setGridLayoutManager(spansize);
        Log.v("RecyclerView", Integer.toString(R.id.recycler_view));
        movie.setRecyclerViewAndAdapter(view.findViewById(R.id.recycler_view));
        movie.fetchMovies(sortOrder, 1);
        movie.setOnClickOnScrollListener();
        dataPasser.onDataPass(movie);
    }

    OnDataPass dataPasser;

    public interface OnDataPass {
        public void onDataPass(MovieFetcher data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            dataPasser  = (OnDataPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}

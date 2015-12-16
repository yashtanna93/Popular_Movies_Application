package com.example.yash.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Yash on 12/12/2015.
 */
public class ImageAdapter extends BaseAdapter {

    String[] movieImages;
    String API_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    private Context mContext;
    //String[] movieTitles = movie.getMovieTitles();
    //private Integer[] mThumbIds = {
    //        R.drawable.antman,
    //        R.drawable.jurassicworld,
    //        R.drawable.minions,
    //        R.drawable.starwarsepisodeviitheforceawakens,
    //        R.drawable.terminatorgenisys
    //};

    public ImageAdapter(Context c, String[] images) {
        mContext = c;
        movieImages = images;
    }

    public int getCount() {
        return movieImages.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(-1, -1));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }
        String movieImage = movieImages[position];
        Uri imageUri = Uri.parse(API_BASE_IMAGE_URL + movieImage)
                .buildUpon().build();
        //Log.v("ImageURI - ", imageUri.toString());
        if("null".equals(movieImage)) {
            Picasso.with(mContext).load(R.drawable.notavailable).resize(185, 278)
                    .into(imageView);
        } else {
            Picasso.with(mContext).load(imageUri.toString()).resize(185, 278)
                    .into(imageView);
        }
        return imageView;
    }
}

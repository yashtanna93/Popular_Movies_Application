package com.example.yash.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yash.popularmovies.R;
import com.example.yash.popularmovies.customview.InfiniteRecylcerView;
import com.example.yash.popularmovies.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by Yash on 01/28/2016.
 */


public class MoviesAdapter extends InfiniteRecylcerView<Movie, MoviesAdapter.MovieHolder> {

    private List<Movie> movies;
    private Context mContext;


    //interface to implement on click listener for movies
    public interface OnMovieClickListener {
        void onContentClicked(@NonNull final Movie movie, View view, int position);

        OnMovieClickListener DUMMY = new OnMovieClickListener() {
            @Override public void onContentClicked(@NonNull Movie movie, View view, int position) {}
        };
    }

    @NonNull private OnMovieClickListener mListener = OnMovieClickListener.DUMMY;


    public void setListener(@NonNull OnMovieClickListener listener) {
        this.mListener = listener;
    }

    //constructor
    public MoviesAdapter(List<Movie> movies, Context c) {
        super(c,movies);
        this.movies = movies;
        this.mContext = c;
    }

    //override get item id
    @Override
    public long getItemId(int position) {
        return (!isLoadMore(position)) ? mItems.get(position).getId() : -1;
    }

    @Override
    protected MovieHolder onCreateItemHolder(ViewGroup parent, int viewType) {
        return new MovieHolder(mInflater.inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            ((MovieHolder) holder).bind(mItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    final class MovieHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.movie_item_container) View mContentContainer;
        @Bind(R.id.movie_item_image)ImageView mImageView;
//        @Bind(R.id.movie_item_title)
//        TextView mTitleView;
//        @Bind(R.id.movie_item_genres) TextView mGenresView;
//        @Bind(R.id.movie_item_footer) View mFooterView;
//        @Bind(R.id.movie_item_btn_favorite)
//        ImageButton mFavoriteButton;

        @BindColor(R.color.theme_primary) int mColorBackground;
        @BindColor(R.color.body_text_white) int mColorTitle;
        @BindColor(R.color.body_text_1_inverse) int mColorSubtitle;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(@NonNull final Movie movie) {
            mContentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onContentClicked(movie, view, MovieHolder.this.getAdapterPosition());
                }
            });

//            mTitleView.setText(movie.getTitle());
            //mGenresView.setText(UiUtils.joinGenres(movie.getGenres(), ", ", mBuilder));

//            // prevents unnecessary color blinking
//            if (mMovieId != movie.getId()) {
//                resetColors();
//                mMovieId = movie.getId();
//            }
            String baseImageUrl = "http://image.tmdb.org/t/p/w342/";
            Picasso.with(mContext).load(baseImageUrl +movie.getPosterPath()
            ).into(mImageView, new Callback.EmptyCallback() {

//When the image is loaded, palette will take the color and apply it to the other imageView

//                @Override public void onSuccess() {
//                    final Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();// Ew!
//
//                    new Palette.Builder(bitmap).generate( new Palette
//                            .PaletteAsyncListener() {
//                        public void onGenerated(Palette palette) {
//
//                            if (palette != null) {
//
//                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//
//                                if (vibrantSwatch != null) {
//                                    applyColors(vibrantSwatch);
//
//                                }
//                            }
//                        }
//                    });
//                }
            });
        }

//        private void applyColors(Palette.Swatch swatch) {
//            if (swatch != null) {
//                mFooterView.setBackgroundColor(swatch.getRgb());
//                mTitleView.setTextColor(swatch.getBodyTextColor());
//                mGenresView.setTextColor(swatch.getTitleTextColor());
//                //mFavoriteButton.setColorFilter(swatch.getBodyTextColor(), PorterDuff.Mode.MULTIPLY);
//            }
//        }
    }
}

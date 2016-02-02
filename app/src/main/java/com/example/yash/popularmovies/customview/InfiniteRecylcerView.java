package com.example.yash.popularmovies.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.yash.popularmovies.R;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Yash on 01/28/2016.
 */
public abstract class InfiniteRecylcerView<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements Action1<List<T>> {

    private static final int VIEW_TYPE_LOAD_MORE = 1;
    protected static final int VIEW_TYPE_ITEM = 2;

    @NonNull
    protected final LayoutInflater mInflater;
    @NonNull protected List<T> mItems;

    private boolean showLodingProgress = false;

    public InfiniteRecylcerView(@NonNull Context context, @NonNull List<T> items) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = items;
    }

    public void setLoadMore(boolean enabled) {
        if (showLodingProgress != enabled) {
            if (showLodingProgress) {
                notifyItemRemoved(getItemCount());
                showLodingProgress = false;
            } else {
                notifyItemInserted(getItemCount());
                showLodingProgress = true;
            }
        }
    }

    public boolean isLoadMore() {
        return showLodingProgress;
    }

    public boolean isLoadMore(int position) {
        return showLodingProgress && (position == (getItemCount() - 1));
    }

    private int countLoadMore() {
        return showLodingProgress ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size() + countLoadMore();
    }

    @Override
    public int getItemViewType(int position) {
        return isLoadMore(position) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }

    @Override
    public void call(@NonNull List<T> newItems) {
        add(newItems);
    }

    public void set(@NonNull List<T> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void add(@NonNull List<T> newItems) {
        if (!newItems.isEmpty()) {
            int currentSize = mItems.size();
            int amountInserted = newItems.size();

            mItems.addAll(newItems);
            notifyItemRangeInserted(currentSize, amountInserted);
        }
    }

    @NonNull
    public List<T> getItems() {
        return mItems;
    }

    public void clear() {
        if (!mItems.isEmpty()) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_LOAD_MORE
                ? new RecyclerView.ViewHolder(mInflater.inflate(R.layout.movie_item_load_more, parent, false)) {}
                : onCreateItemHolder(parent, viewType);
    }

    protected abstract VH onCreateItemHolder(ViewGroup parent, int viewType);
}

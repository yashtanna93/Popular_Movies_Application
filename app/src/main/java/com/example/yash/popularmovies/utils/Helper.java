package com.example.yash.popularmovies.utils;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Yash on 01/28/2016.
 */
public final class Helper {

    private static final int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public  static double getDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    private Helper() {
        throw new AssertionError("no Instances");
    }

    /**
     * Created by Yash on 01/28/2016.
     */
    public abstract static class InfiniteScrollListener extends RecyclerView.OnScrollListener {

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 5;
        // The current offset index of data you have loaded
        private int currentPage = 1;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        public interface OnLoadMoreCallback {
            void onLoadMore(int page, int totalItemsCount);
        }

        private OnLoadMoreCallback mCallback;

        private InfiniteScrollListener(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        public InfiniteScrollListener setCallback(OnLoadMoreCallback callback) {
            mCallback = callback;
            return this;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override public abstract void onScrolled(RecyclerView recyclerView, int dx, int dy);

        public void onScrolled(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) { this.loading = true; }
            }
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                onLoadMore(currentPage + 1, totalItemCount);
                loading = true;
            }
        }

        // Defines the process for actually loading more data based on page
        public void onLoadMore(int page, int totalItemsCount) {
            if (mCallback != null) {
                mCallback.onLoadMore(page, totalItemsCount);
            }
        }

        public static InfiniteScrollListener fromGridLayoutManager(
                @NonNull final GridLayoutManager layoutManager,
                int visibleThreshold,
                int startPage) {

            return new InfiniteScrollListener(visibleThreshold, startPage) {
                @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy <= 0) return;

                    final int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                    final int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    final int totalItemCount = layoutManager.getItemCount();

                    onScrolled(firstVisibleItem, lastVisibleItem - firstVisibleItem, totalItemCount);
                }
            };
        }
    }
}

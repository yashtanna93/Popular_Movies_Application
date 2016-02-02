package com.example.yash.popularmovies.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

/**
 * Created by Yash on 01/28/2016.
 */
public final class CustomViewAnimator extends ViewAnimator {

    public CustomViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChildId(int id) {
        if (getDisplayedChildId() == id) {
            return;
        }
        for (int i = 0, count = getChildCount(); i < count; i++) {
            if (getChildAt(i).getId() == id) {
                setDisplayedChild(i);
                return;
            }
        }
        String name = getResources().getResourceEntryName(id);
        throw new IllegalArgumentException("No view with ID " + name);
    }

    private int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }
}
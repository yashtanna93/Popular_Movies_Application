package com.example.yash.popularmovies.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.yash.popularmovies.R;

/**
 * Created by Yash on 01/28/2016.
 */
public final class ResizableImageView extends ImageView{

    private float aspectRatio = 0;
    private AspectRatioSource aspectRatioSource = null;

    public ResizableImageView(Context context) {
        super(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectLockedImageView);
        aspectRatio = a.getFloat(R.styleable.AspectLockedImageView_imageAspectRatio, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        float localRatio = aspectRatio;

        if (localRatio == 0.0 && aspectRatioSource != null
                && aspectRatioSource.getHeight() > 0) {
            localRatio =
                    (float) aspectRatioSource.getWidth()
                            / (float) aspectRatioSource.getHeight();
        }

        if (localRatio == 0.0) {
            super.onMeasure(widthSpec, heightSpec);
        } else {
            int lockedWidth = MeasureSpec.getSize(widthSpec);
            int lockedHeight = MeasureSpec.getSize(heightSpec);

//            if (lockedWidth == 0 && lockedHeight == 0) {
//                throw new IllegalArgumentException(
//                        "Both width and height cannot be zero -- watch out for scrollable containers");
//            }

            // Get the padding of the border background.
            int hPadding = getPaddingLeft() + getPaddingRight();
            int vPadding = getPaddingTop() + getPaddingBottom();

            // Resize the preview frame with correct aspect ratio.
            lockedWidth -= hPadding;
            lockedHeight -= vPadding;

            if (lockedHeight > 0 && (lockedWidth > lockedHeight * localRatio)) {
                lockedWidth = (int) (lockedHeight * localRatio + .5);
            } else {
                lockedHeight = (int) (lockedWidth / localRatio + .5);
            }

            // Add the padding of the border.
            lockedWidth += hPadding;
            lockedHeight += vPadding;

            // Ask children to follow the new preview dimension.
            super.onMeasure(MeasureSpec.makeMeasureSpec(lockedWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(lockedHeight, MeasureSpec.EXACTLY));
        }
    }

    // from com.android.camera.PreviewFrameLayout, with slight
    // modifications

    public interface AspectRatioSource {
        int getWidth();

        int getHeight();
    }

}

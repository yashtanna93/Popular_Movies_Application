package com.example.yash.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.example.yash.popularmovies.providers.PopularMoviesApplication;

import butterknife.ButterKnife;

/**
 * Created by Yash on 01/28/2016.
 */
public class BaseFragment extends Fragment {


    private Toast mToast;

    @CallSuper
    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @CallSuper
    @Override public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @CallSuper
    @Override public void onDestroy() {

        super.onDestroy();
        PopularMoviesApplication.get(getActivity()).getRefWatcher().watch(this);
    }

    protected void showToast(@StringRes int resId) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
        mToast.show();
    }
}

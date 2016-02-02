package com.example.yash.popularmovies.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.yash.popularmovies.providers.PopularMoviesApplication;
import com.example.yash.popularmovies.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Yash on 01/28/2016.
 */
public class BaseTemplateActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @CallSuper
    @Override protected void onDestroy() {
        super.onDestroy();
        PopularMoviesApplication.get(this).getRefWatcher().watch(this);
    }

    @CallSuper
    @Override public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        configureToolbar();
    }

    @Nullable
    public final Toolbar getToolbar() {
        return mToolbar;
    }

    private void configureToolbar() {
        if (mToolbar == null) {
            Timber.w("Could not find the Required Toolbar");
            return;
        }

        //set elevation for toolbar
        ViewCompat.setElevation(mToolbar, getResources().getDimension(R.dimen.toolbar_elevation));
        setSupportActionBar(mToolbar);

        //setting up the action bar for adding menu items later
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
    }
}

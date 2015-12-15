package com.example.yash.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yash on 12/12/2015.
 */
public class MovieFetcher extends AsyncTask<Void, Void, String[][]> {
//    int[] movieImages = {
//            R.drawable.antman,
//            R.drawable.jurassicworld,
//            R.drawable.minions,
//            R.drawable.starwarsepisodeviitheforceawakens,
//            R.drawable.terminatorgenisys
//    };

    Activity mActivity;
    String[] movieImages = new String[20];
    String[][] result;
    MovieFetcher(Activity a) {
        mActivity = a;
    }

    public String[] getMovieImages() {
        return movieImages;
    }

    public String[] getMovieData(int i) {
        return result[i];
    }

    private String[][] getMoviesDataFromJson(String moviesDataJsonStr) throws
            JSONException {
        String RESULT = "results";
        String ORIGINAL_TITLE = "original_title";
        String POSTER_PATH = "poster_path";
        String OVERVIEW = "overview";
        String USER_RATING = "vote_average";
        String RELEASE_DATA = "release_date";

        JSONObject moviesJson = new JSONObject(moviesDataJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULT);

        String[][] resultStrs = new String[20][5];

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieData = moviesArray.getJSONObject(i);
            resultStrs[i][0] = movieData.getString(ORIGINAL_TITLE);
            resultStrs[i][1] = movieData.getString(POSTER_PATH);
            resultStrs[i][2] = movieData.getString(OVERVIEW);
            resultStrs[i][3] = String.valueOf(movieData.getDouble(USER_RATING));
            resultStrs[i][4] = movieData.getString(RELEASE_DATA);
        }
        return resultStrs;
    }

    @Override
    protected String[][] doInBackground(Void... params) {
        try {
            String API_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            String ORDER_PARAM = "sort_by";
            String API_KEY_PARAM = "api_key";
            Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
                    .appendQueryParameter(ORDER_PARAM, "popularity.desc" +
                            "")
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.TMDB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());


            //Log.v("Built URI ", builtUri.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0) {
                return null;
            }

            String moviesData = buffer.toString();
            //Log.v("Data", moviesData);

            result = getMoviesDataFromJson(moviesData);
            for (int i = 0; i < 20; i++) {
                movieImages[i] = result[i][1];
                //Log.v("Title" + String.valueOf(i), result[i][0]);
                //Log.v("Poster" + String.valueOf(i), result[i][1]);
                //Log.v("Overview" + String.valueOf(i), result[i][2]);
                //Log.v("Rating" + String.valueOf(i), result[i][3]);
                //Log.v("ReleaseDate" + String.valueOf(i), result[i][4]);
            }

            return result;
        } catch (Exception e) {
            Log.v("Exception", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[][] result) {
        GridView gridview = (GridView) mActivity.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(mActivity.getApplicationContext
                (), movieImages));
        int i = 0;
        //Log.v("Call number", String.valueOf(i++));
    }
}

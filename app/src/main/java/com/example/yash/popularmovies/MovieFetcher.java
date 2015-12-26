package com.example.yash.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Yash on 12/12/2015.
 */
public class MovieFetcher extends AsyncTask<Void, Void, ArrayList<String[]>> {
//    int[] movieImages = {
//            R.drawable.antman,
//            R.drawable.jurassicworld,
//            R.drawable.minions,
//            R.drawable.starwarsepisodeviitheforceawakens,
//            R.drawable.terminatorgenisys
//    };
    String sortOrder;
    String pageNumber;
    MovieFetcher(String sOrder, int page) {
        sortOrder = sOrder;
        pageNumber = Integer.toString(page);
    }

    public String[] getMovieData(int i) {
        return MainActivity.result.get(i);
    }

    private ArrayList<String[]> getMoviesDataFromJson(String moviesDataJsonStr)
            throws JSONException {
        String RESULT = "results";
        String ORIGINAL_TITLE = "original_title";
        String POSTER_PATH = "poster_path";
        String OVERVIEW = "overview";
        String USER_RATING = "vote_average";
        String RELEASE_DATA = "release_date";

        JSONObject moviesJson = new JSONObject(moviesDataJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULT);

        ArrayList<String[]> resultStrs = new ArrayList<String[]>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieData = moviesArray.getJSONObject(i);
            String[] resultpermovie = new String[5];
            resultpermovie[0] = movieData.getString(ORIGINAL_TITLE);
            resultpermovie[1] = movieData.getString(POSTER_PATH);
            resultpermovie[2] = movieData.getString(OVERVIEW);
            resultpermovie[3] = String.valueOf(movieData.getDouble(USER_RATING));
            resultpermovie[4] = movieData.getString(RELEASE_DATA);
            //Log.v("JSON_TITLE", resultpermovie[0]);
            resultStrs.add(i, resultpermovie);
        }
        return resultStrs;
    }

    @Override
    protected ArrayList<String[]> doInBackground(Void... params) {
        try {
            String API_BASE_URL = "http://api.themoviedb.org/3/movie";
            String API_KEY_PARAM = "api_key";
            String PAGE_PARAM = "page";
            Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
                    .appendPath(sortOrder)
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.TMDB_API_KEY)
                    .appendQueryParameter(PAGE_PARAM, pageNumber)
                    .build();

            URL url = new URL(builtUri.toString());


            //Log.v("Built URI ", url.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.connect();

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

            urlConnection.disconnect();
            String moviesData = buffer.toString();
            //Log.v("Data", moviesData);

            MainActivity.result.addAll(getMoviesDataFromJson(moviesData));
            for (int i = ((Integer.parseInt(pageNumber) * 20) - 20);
                 i < MainActivity.result.size(); i++) {
                String[] movie = MainActivity.result.get(i);
                MainActivity.movieNames.add(movie[0]);
                MainActivity.movieImages.add(movie[1]);
                //Log.v("Title" + String.valueOf(i), movie[0]);
                //Log.v("Poster" + String.valueOf(i), movie[1]);
                //Log.v("Overview" + String.valueOf(i), result[i][2]);
                //Log.v("Rating" + String.valueOf(i), result[i][3]);
                //Log.v("ReleaseDate" + String.valueOf(i), result[i][4]);
            }

            return MainActivity.result;
        } catch (Exception e) {
            Log.v("Exception", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> result) {
        MainActivity.rcAdapter.notifyDataSetChanged();
        //Log.v("Call number", String.valueOf(i++));
    }
}

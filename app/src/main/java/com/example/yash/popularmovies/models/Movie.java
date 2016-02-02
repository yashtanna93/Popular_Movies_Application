package com.example.yash.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.yash.popularmovies.interfaces.MoviesMetadata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash on 01/28/2016.
 */
public class Movie implements Parcelable, MoviesMetadata {

    @Expose
    long id;

    @Expose @SerializedName("genre_ids")
    List<Integer> genreIds = new ArrayList<>();

    @Expose @SerializedName("backdrop_path")
    String backdropPath;

    @Expose @SerializedName("original_language")
    String originalLanguage;

    @Expose
    String overview;

    @Expose @SerializedName("original_title")
    String originalTitle;

    @Expose @SerializedName("poster_path")
    String posterPath;

    @Expose
    String title;

    @Expose @SerializedName("vote_average")
    double voteAverage;

    @Expose @SerializedName("vote_count")
    long voteCount;

    @Expose @SerializedName("release_date")
    String releaseDate;

    @Expose
    double popularity;

    boolean favored = false;


    public boolean isFavored() {
        return favored;
    }

    public Movie setFavored(boolean favored) {
        this.favored = favored;
        return this;
    }

    public double getPopularity() {
        return popularity;
    }

    public Movie setPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public long getId() {
        return id;
    }

    public Movie setId(long id) {
        this.id = id;
        return this;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Movie setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Movie setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getVoteAverage() {
        return voteAverage;
    }


    public String makeGenreIdsList() {
        if (genreIds.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(genreIds.get(0));
        for (int i = 1; i < genreIds.size(); i++) {
            sb.append(",").append(genreIds.get(i));
        }
        return sb.toString();
    }


    public Movie putGenreIdsList(String ids) {
        if (!TextUtils.isEmpty(ids)) {
            genreIds = new ArrayList<>();
            String[] strs = ids.split(",");
            for (String s : strs)
                genreIds.add(Integer.parseInt(s));
        }
        return this;
    }

    public Movie setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public Movie setVoteCount(long voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public static final class Response {

        @Expose
        public int page;

        @Expose @SerializedName("total_pages")
        public int totalPages;

        @Expose @SerializedName("total_results")
        public int totalMovies;

        @Expose @SerializedName("results")
        public List<Movie> movies = new ArrayList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeList(this.genreIds);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeDouble(this.popularity);
        dest.writeString(this.title);
        dest.writeDouble(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeByte(favored ? (byte) 1 : (byte) 0);
    }

    public Movie(Parcel in) {
        this.id = in.readLong();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, List.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readLong();
        this.favored = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {return new Movie(source);}

        public Movie[] newArray(int size) {return new Movie[size];}
    };

    public Movie() {

    }
}

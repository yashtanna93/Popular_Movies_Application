package com.example.yash.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.yash.popularmovies.interfaces.GenresMetadata;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash on 01/28/2016.
 */

public class Genres implements Parcelable, GenresMetadata {

    @Expose
    long id;

    @Expose
    String name;

    public Genres(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Genres(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Genres> CREATOR = new Creator<Genres>() {
        @Override
        public Genres createFromParcel(Parcel in) {
            return new Genres(in);
        }

        @Override
        public Genres[] newArray(int size) {
            return new Genres[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    public static class Response {
        @Expose
        public List<Genres> genres = new ArrayList<>();

    }

}

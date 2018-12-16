package org.chokh.boostcamphw.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieItem {

    @SerializedName("lastBuildDate")
    public String lastBuildDate;
    @SerializedName("total")
    public int total;
    @SerializedName("start")
    public int start;
    @SerializedName("display")
    public int display;
    @SerializedName("errorMessage")
    String errorMessage;
    @SerializedName("errorCode")
    String errorCode;
    @SerializedName("items")
    public List<MovieDetail> items;

    public class MovieDetail {
        @SerializedName("title")
        public String title;
        @SerializedName("link")
        public String link;
        @SerializedName("image")
        public String image;
        @SerializedName("subtitle")
        public String subtitle;
        @SerializedName("pubDate")
        public String pubDate;
        @SerializedName("director")
        public String director;
        @SerializedName("actor")
        public String actor;
        @SerializedName("userRating")
        public float userRating;

    }

}

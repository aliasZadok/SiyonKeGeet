package com.judah.songsofzion.ui;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Judah on 12/21/2016.
 */
public class Track {
    @SerializedName("title")
    private String mTitle;

    @SerializedName("id")
    private int mID;

    @SerializedName("stream_url")
    private String mStreamURL;

    @SerializedName("artwork_url")
    private String mArtworkURL;

    @SerializedName("duration")
    private long mDuration;

    @SerializedName("description")
    private String mDescription;

    public String getTitle() {
        return mTitle;
    }

    public int getID() {
        return mID;
    }

    public String getStreamURL() {
        return mStreamURL;
    }

    public String getArtworkURL() {
        return mArtworkURL;
    }

    public String getDuration() {
        return "" + (int) ((mDuration / (1000 * 60)) % 60) + ":" + (int) ((mDuration % (1000 * 60 * 60)) % (1000 * 60) / 1000);
    }

    public String getDescription() {
        return mDescription;
    }

}

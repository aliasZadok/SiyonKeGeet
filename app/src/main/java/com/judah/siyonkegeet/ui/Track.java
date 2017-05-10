package com.judah.siyonkegeet.ui;


/**
 * Created by Judah on 12/21/2016.
 */
public class Track {
    private long id;
    private String title;
    private String artworkUrl;
    private long duration;
    private String streamUrl;
    private String description;

    public Track(long id, String title, String artworkUrl, long duration, String streamUrl, String description) {
        this.id = id;
        this.title = title;
        this.artworkUrl = artworkUrl;
        this.duration = duration;
        this.streamUrl = streamUrl;
        this.description = description;
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getDuration() {
        return duration;
    }


    public String getArtworkUrl() {
        return artworkUrl;
    }


    public String getStreamUrl() {
        return streamUrl;
    }


    public String getDescription() {
        return description;
    }

}

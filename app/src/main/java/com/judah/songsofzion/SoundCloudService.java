package com.judah.songsofzion;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Judah on 12/21/2016.
 */
public interface SoundCloudService {
    @GET("/users/266303759/tracks?client_id=" + Configuration.CLIENT_ID)
    Call<List<Track>> getRecentTracks(@Query("created_at") String date);
}

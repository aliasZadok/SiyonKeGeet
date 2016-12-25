package com.judah.songsofzion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Judah on 12/21/2016.
 */
public class SoundCloud {
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(Configuration.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static final SoundCloudService SERVICE = RETROFIT.create(SoundCloudService.class);
    public static SoundCloudService getService(){
        return SERVICE;
    }
}

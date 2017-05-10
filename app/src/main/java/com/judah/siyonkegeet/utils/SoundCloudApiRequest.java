package com.judah.siyonkegeet.utils;

/**
 * Created by Judah on 4/22/2017.
 */
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.judah.siyonkegeet.ui.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SoundCloudApiRequest {

    public interface SoundCloudInterface {
        void onSuccess(ArrayList<Track> songs);
        void onError(String message);
    }

    private RequestQueue queue;
    private static final String URL = Configuration.API_URL + "/users/266303759/tracks?client_id="+ Configuration.CLIENT_ID;
    private static final String TAG = "APP";

    public SoundCloudApiRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSongList(String query, final SoundCloudInterface callback){

        String url = URL;
        if(query.length() > 0){
            try {
                query = URLEncoder.encode(query, "UTF-8");
                url = URL + "&q=" + query;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "getSongList: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response);

                ArrayList<Track> songs = new ArrayList<>();
                if(response.length() > 0){
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            JSONObject songObject = response.getJSONObject(i);
                            long id = songObject.getLong("id");
                            String title = songObject.getString("title");
                            String artworkUrl = songObject.getString("artwork_url");
                            String streamUrl = songObject.getString("stream_url");
                            String description = songObject.getString("description");
                            long duration = songObject.getLong("duration");

                            Track song = new Track(id, title, artworkUrl, duration, streamUrl, description);
                            songs.add(song);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("An error has occurred");
                            e.printStackTrace();
                        }
                    }

                    callback.onSuccess(songs);

                }else{
                    callback.onError("No song found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("An error has occurred");
            }
        });

        queue.add(request);

    }
}
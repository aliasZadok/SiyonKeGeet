package com.judah.siyonkegeet.utils;

/**
 * Created by Judah on 4/26/2017.
 */

public class DurationConverter {
    public static String convertDuration(long duration){

        long minutes = (duration / 1000 ) / 60;
        long seconds = (duration / 1000 ) % 60;

        String converted = String.format("%d:%02d", minutes, seconds);
        return converted;


    }
}

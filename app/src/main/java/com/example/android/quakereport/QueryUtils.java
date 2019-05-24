package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthquakeEntry} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthquakeEntry> extractEarthquakeEntrys(String apiResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakeEntry> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            if(apiResponse == null) return null;

            JSONObject jsonRoot = new JSONObject(apiResponse);
            JSONArray featuresArray = jsonRoot.getJSONArray("features");
            for (int i=0; i<featuresArray.length(); i++) {
                JSONObject properties = featuresArray.getJSONObject(i).getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");


                EarthquakeEntry earthquakeEntry = new EarthquakeEntry(mag, place, time, url);
                earthquakes.add(earthquakeEntry);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
        // build up a list of EarthquakeEntry objects with the corresponding data.

        // Return the list of earthquakes
        return earthquakes;
    }

}
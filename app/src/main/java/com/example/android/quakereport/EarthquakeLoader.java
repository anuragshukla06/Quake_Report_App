package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeEntry>> {

    private String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    public EarthquakeLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<EarthquakeEntry> loadInBackground() {

        // Below code simulates slow internet connection
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        URL url = makeURL(USGS_URL);
        String apiResponse = makeHttpRequest(url);
        ArrayList<EarthquakeEntry> earthquakeEntryResponse = QueryUtils.extractEarthquakeEntrys(apiResponse);
        return earthquakeEntryResponse;
    }

    private URL makeURL(String urlString) {

        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private String makeHttpRequest(URL url) {

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setRequestMethod("GET");

            inputStream = httpURLConnection.getInputStream();
            result = readInputStream(inputStream);

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // some error occured

    }


    private String readInputStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        String result;

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String data = reader.readLine();

            while (data !=null) {
                builder.append(data);
                data = reader.readLine();
            }

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // when some error occured, handle this case.
    }

}

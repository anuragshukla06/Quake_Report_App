/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<EarthquakeEntry>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    ListView earthquakeListView;
    TextView emptyStateTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.

//        earthquakes.add(new EarthquakeEntry(5.2, "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new EarthquakeEntry(4.2, "London", "July 13, 2016"));
//        earthquakes.add(new EarthquakeEntry(3.4, "Tokyo", "August 7, 2019"));
//        earthquakes.add(new EarthquakeEntry(6.5, "Mexico City", "Feb 2, 2016"));
//        earthquakes.add(new EarthquakeEntry(4.7, "Moscow", "Feb 2, 2020"));
//        earthquakes.add(new EarthquakeEntry(2.8, "Paris", "Feb 2, 2016"));
//        earthquakes.add(new EarthquakeEntry(4.3, "Rio de Janeiro", "Feb 2, 2016"));

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = findViewById(R.id.list);
        emptyStateTextView = findViewById(R.id.emptyStateTextView);
        progressBar = findViewById(R.id.progressBar);
//        DataDownloader downloader = new DataDownloader();
//        downloader.execute(USGS_URL);

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            emptyStateTextView.setText("No Internet Connection");
            progressBar.setVisibility(View.INVISIBLE);
        } else {

            getLoaderManager().initLoader(0, null, this).forceLoad();
        }


    }

    @Override
    public Loader<ArrayList<EarthquakeEntry>> onCreateLoader(int i, Bundle bundle) {
        Log.i("loader", "loader called");
        return new EarthquakeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeEntry>> loader, ArrayList<EarthquakeEntry> earthquakeEntries) {
        EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(getApplicationContext(), earthquakeEntries);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        progressBar.setVisibility(View.GONE);
        Log.i("finish", "onLoadFinished called");
        if (earthquakeEntries == null || earthquakeEntries.size() == 0) {
            emptyStateTextView.setText("No earthquakes to display");
        } else {
            earthquakeListView.setAdapter(earthquakeListAdapter);
            earthquakeListView.setEmptyView(emptyStateTextView);
            setItemOnListener(earthquakeEntries);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthquakeEntry>> loader) {
        progressBar.setVisibility(View.VISIBLE);
        Log.i("reset", "onLoaderReset called");

    }

//    private class DataDownloader extends AsyncTask<String, Void, ArrayList<EarthquakeEntry>> {
//        @Override
//        protected ArrayList<EarthquakeEntry> doInBackground(String... urls) {
//            URL url = makeURL(urls[0]);
//            String apiResponse = makeHttpRequest(url);
//            ArrayList<EarthquakeEntry> earthquakeEntryResponse = QueryUtils.extractEarthquakeEntrys(apiResponse);
//            return earthquakeEntryResponse;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<EarthquakeEntry> earthquakeEntries) {
//            super.onPostExecute(earthquakeEntries);
//            // Create a new {@link ArrayAdapter} of earthquakes
////            EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(getApplicationContext(), earthquakeEntries);
////
////            // Set the adapter on the {@link ListView}
////            // so the list can be populated in the user interface
////            earthquakeListView.setAdapter(earthquakeListAdapter);
////            setItemOnListener(earthquakeEntries);
//        }
//    }

    private void setItemOnListener (final ArrayList<EarthquakeEntry> earthquakeEntries) {

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EarthquakeEntry entry = earthquakeEntries.get(position);
                String url = entry.getURL();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                Log.i("url", url);
                startActivity(intent);
            }
        });

    }


//    private URL makeURL(String urlString) {
//
//        URL url = null;
//
//        try {
//            url = new URL(urlString);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        return url;
//    }
//
//    private String makeHttpRequest(URL url) {
//
//        HttpURLConnection httpURLConnection = null;
//        InputStream inputStream = null;
//        String result = null;
//
//        try {
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setConnectTimeout(1000);
//            httpURLConnection.setReadTimeout(1000);
//            httpURLConnection.setRequestMethod("GET");
//
//            inputStream = httpURLConnection.getInputStream();
//            result = readInputStream(inputStream);
//
//            return result;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null; // some error occured
//
//    }
//
//
//    private String readInputStream(InputStream inputStream) {
//        StringBuilder builder = new StringBuilder();
//        String result;
//
//        InputStreamReader inputStreamReader = null;
//        try {
//            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            String data = reader.readLine();
//
//            while (data !=null) {
//                builder.append(data);
//                data = reader.readLine();
//            }
//
//        return builder.toString();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null; // when some error occured, handle this case.
//    }

}

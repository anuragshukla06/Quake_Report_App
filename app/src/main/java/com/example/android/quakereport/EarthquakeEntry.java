package com.example.android.quakereport;

public class EarthquakeEntry {

    private double mMagnitude;
    private String mPlace;
    private long mTimeInMilliseconds;
    private String mUrl;

    public EarthquakeEntry(double magnitude, String place, long date, String url) {
        mMagnitude = magnitude;
        mPlace = place;
        mTimeInMilliseconds = date;
        mUrl = url;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public long getTimeInMillisecons() {
        return mTimeInMilliseconds;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getURL() { return mUrl; }
}

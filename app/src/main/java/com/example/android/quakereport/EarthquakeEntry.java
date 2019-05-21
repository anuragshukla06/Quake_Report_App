package com.example.android.quakereport;

public class EarthquakeEntry {

    private double mMagnitude;
    private String mPlace;
    private long mTimeInMilliseconds;

    public EarthquakeEntry(double magnitude, String place, long date) {
        mMagnitude = magnitude;
        mPlace = place;
        mTimeInMilliseconds = date;
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
}

package com.example.android.quakereport;

public class EarthquakeEntry {

    private double mMagnitude;
    private String mPlace;
    private String mDate;

    public EarthquakeEntry(double magnitude, String place, String date) {
        mMagnitude = magnitude;
        mPlace = place;
        mDate = date;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getDate() {
        return mDate;
    }

    public String getPlace() {
        return mPlace;
    }
}

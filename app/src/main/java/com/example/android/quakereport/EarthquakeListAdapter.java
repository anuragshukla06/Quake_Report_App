package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.EarthquakeEntry;
import com.example.android.quakereport.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeListAdapter extends ArrayAdapter {

    public EarthquakeListAdapter(@NonNull Context context, @NonNull ArrayList<EarthquakeEntry> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);
        }

        EarthquakeEntry entry = (EarthquakeEntry) getItem(position);

        TextView magnitudeTextView = itemView.findViewById(R.id.magnitudeTextView);
        TextView primaryPlaceTextView = itemView.findViewById(R.id.primaryPlaceTextView);
        TextView offsetTextView = itemView.findViewById(R.id.offsetPlaceTextView);
        TextView dateTextView = itemView.findViewById(R.id.dateTextView);
        TextView timeTextView = itemView.findViewById(R.id.timeTextView);

        double magnitude = entry.getMagnitude();
        magnitudeTextView.setText(String.valueOf(magnitude));
        String place = entry.getPlace();
        long time = entry.getTimeInMillisecons();

        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM a");

        String placeExtract;
        String placeOffset;

        if (place.contains(" of ")) {
            String placeAndOffset[] = place.split("(?<= of )");
            placeOffset = placeAndOffset[0];
            placeExtract = placeAndOffset[1];
        } else {
            placeOffset = "Near the";
            placeExtract = place;
        }

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(ContextCompat.getColor(getContext(), magnitudeColor));

        offsetTextView.setText(placeOffset);
        primaryPlaceTextView.setText(placeExtract);

        dateTextView.setText(dateFormat.format(time));
        timeTextView.setText(timeFormat.format(time));

        return itemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int color = 0;

        switch ((int) magnitude){
            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            case 10:
                color = R.color.magnitude10plus;
                break;
            default:
                color = R.color.magnitude10plus;


        }

        return color;
    }
}

package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.EarthquakeEntry;
import com.example.android.quakereport.R;


import java.util.ArrayList;
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
        TextView placeTextView = itemView.findViewById(R.id.placeTextView);
        TextView dateTextView = itemView.findViewById(R.id.dateTextView);

        magnitudeTextView.setText(String.valueOf(entry.getMagnitude()));
        placeTextView.setText(entry.getPlace());
        dateTextView.setText(entry.getDate());

        return itemView;
    }
}

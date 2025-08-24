package com.s92075608.medical_app_s92075608;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// MyAdapterwalkCount is a custom adapter for the Walk Count feature.
// It extends ArrayAdapter and is used to show distances and calories in a ListView.
public class MyAdapterwalkCount extends ArrayAdapter<String> {

    Context context;       // activity or screen where this adapter is used
    String[] distances;    // array to store distances walked
    String[] calories;     // array to store calories burnt

    // Constructor for adapter → gets context, date array, distance array, and calorie array
    public MyAdapterwalkCount(Context context, String[] array, String[] distances, String[] calories){
        // Pass layout and default textview id to the ArrayAdapter
        super(context, R.layout.view_list_singlewalkcount, R.id.listDate, array);
        this.context = context;
        this.distances = distances;
        this.calories = calories;
    }

    // getView() method creates each row in the list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate (create) the row layout from XML
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.view_list_singlewalkcount, parent, false);

        // Find distance TextView in row and set its value
        TextView distance = row.findViewById(R.id.listDistance);
        distance.setText(distances[position]);

        // Find calorie TextView in row and set its value
        TextView calorie = row.findViewById(R.id.listCalorie);
        calorie.setText(calories[position]);

        // Return the row to display in ListView
        return row;
    }
}

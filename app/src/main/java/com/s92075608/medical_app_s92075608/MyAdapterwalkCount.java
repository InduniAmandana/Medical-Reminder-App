package com.s92075608.medical_app_s92075608;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// for a list view in the walk count feature
public class MyAdapterwalkCount extends ArrayAdapter<String> {//that extends ArrayAdapter to populate a list view with distances and calories for each item
    Context context;
    String[] distances;
    String[] calories;
    public MyAdapterwalkCount(Context context, String[] array, String[] distances, String[] calories){
        super(context,R.layout.view_list_singlewalkcount,R.id.listDate,array);
        this.context=context;
        this.distances=distances;
        this.calories=calories;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.view_list_singlewalkcount,parent,false);

        TextView distance=row.findViewById(R.id.listDistance);
        distance.setText(distances[position]);

        TextView calorie=row.findViewById(R.id.listCalorie);
        calorie.setText(calories[position]);
        return row;
    }
}

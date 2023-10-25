package com.example.score.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.score.R;

public class CustomNumCircuitsSpinnerAdapter extends ArrayAdapter<Integer> {
    private final Activity context;
    private final Integer[] circuitNums;

    public CustomNumCircuitsSpinnerAdapter(Activity context, int textViewResID, Integer[] circuitNums) {
        super(context, textViewResID, circuitNums);
        this.context = context;
        this.circuitNums = circuitNums;
    }

    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View view, ViewGroup parent) {
        // Set up the layout of the list
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("InflateParams") View rowView = inflater.inflate(R.layout.custom_num_circuits_spinner, null, true);
        // Set the number
        TextView spinnerNumberTextView = rowView.findViewById(R.id.spinnerNumberTextView);
        spinnerNumberTextView.setText(String.valueOf(circuitNums[position]));
        // Return the view of the row
        return rowView;
    }
}
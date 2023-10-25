package com.example.score.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.score.R;

public class CustomTeamsListViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] teamNames;
    private final boolean deleting;

    public CustomTeamsListViewAdapter(Activity context, String[] teamNames, boolean deleting) {
        super(context, R.layout.custom_teams_list_view, teamNames);
        this.context = context;
        this.teamNames = teamNames;
        this.deleting = deleting;
    }

    public View getView(int position, View view, ViewGroup parent) {
        // Set up the layout of the list
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.custom_teams_list_view, null, true);
        // Set up the names of the teams
        TextView txtTitle = rowView.findViewById(R.id.txt);
        txtTitle.setText(teamNames[position]);
        // If we are in deleting mode, change the indicator
        if (deleting) {
            ImageView indicator = rowView.findViewById(R.id.indicatorImageView);
            indicator.setImageResource(R.drawable.delete_x);
        }
        // Return the view of the row
        return rowView;
    }
}
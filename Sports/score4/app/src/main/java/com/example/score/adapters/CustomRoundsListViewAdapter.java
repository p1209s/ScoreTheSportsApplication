package com.example.score.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.score.R;

public class CustomRoundsListViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] roundNames;
    private final int tournamentFinished;

    public CustomRoundsListViewAdapter(Activity context, String[] roundNames, int tournamentFinished) {
        super(context, R.layout.custom_teams_list_view, roundNames);
        this.context = context;
        this.roundNames = roundNames;
        this.tournamentFinished = tournamentFinished;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        // Set up the layout of the list
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.custom_rounds_list_view, null, true);
        // Set up the names of the rounds
        TextView roundNameIndividualTextView = rowView.findViewById(R.id.roundNameIndividualTextView);
        roundNameIndividualTextView.setText(roundNames[position]);
        // If we are setting up the last round, give it the "In Progress" status
        if (position == roundNames.length - 1 && tournamentFinished != 3) {
            TextView roundStatusTextView = rowView.findViewById(R.id.roundStatusTextView);
            roundStatusTextView.setText("In Progress");
            roundNameIndividualTextView.setTextColor(Color.parseColor("#FF000000"));
            roundStatusTextView.setTextColor(Color.parseColor("#FF000000"));
            ImageView tournamentsListViewRightArrow = rowView.findViewById(R.id.tournamentsListViewRightArrow);
            tournamentsListViewRightArrow.setAlpha(1f);
        }
        // Return the view of the row
        return rowView;
    }
}
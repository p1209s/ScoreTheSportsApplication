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
import com.example.score.database.DBAdapter;
import java.util.ArrayList;

public class CustomMatchesListViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] team1Names;
    private final String[] team2Names;
    private final ArrayList<Integer> matchIDs;

    public CustomMatchesListViewAdapter(Activity context, ArrayList<Integer> matchIDs, String[] team1Names,
                                        String[] team2Names ) {

        super(context, R.layout.custom_teams_list_view, team1Names);
        this.context = context;
        this.matchIDs = matchIDs;
        this.team1Names = team1Names;
        this.team2Names = team2Names;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        // Set up the layout of the list
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.custom_matches_list_view, null, true);
        // If the match is a bye
        TextView teamName1TextView = rowView.findViewById(R.id.teamName1TextView);
        teamName1TextView.setText(team1Names[position]);
        if(team1Names[position].equals(team2Names[position])) {
            // Set up the name of the team
            teamName1TextView.setTextColor(Color.parseColor("#80000000"));
            // Remove the name of second team
            TextView teamName2TextView = rowView.findViewById(R.id.teamName2TextView);
            teamName2TextView.setText("");
            // Set up the text saying the match is a bye
            TextView vsTextView = rowView.findViewById(R.id.vsTextView);
            vsTextView.setText("Bye");
            vsTextView.setTextColor(Color.parseColor("#80000000"));
            // Remove the arrow
            ImageView arrow = rowView.findViewById(R.id.arrowImageView);
            arrow.setImageResource(-1);
        }
        // If the match is not a bye
        else {
            // Set up the names of the first teams
            // Set up the names of the second teams
            TextView teamName2TextView = rowView.findViewById(R.id.teamName2TextView);
            teamName2TextView.setText(team2Names[position]);
            // If the match had already been updated, reduce opacity
            if (DBAdapter.getMatchUpdated(context, matchIDs.get(position)) == 1) {
                teamName1TextView.setTextColor(Color.parseColor("#80000000"));
                teamName2TextView.setTextColor(Color.parseColor("#80000000"));

                TextView vsTextView = rowView.findViewById(R.id.vsTextView);
                vsTextView.setTextColor(Color.parseColor("#80000000"));
                ImageView arrow = rowView.findViewById(R.id.arrowImageView);
                arrow.setAlpha(0.5f);
            }
        }
        // Return the view of the row
        return rowView;
    }
}
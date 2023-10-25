package com.example.score.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.score.R;
import com.example.score.database.DBAdapter;

public class CustomTeamsListViewNotClickableWithScoreAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final int formatType;
    private final int tournament_id;
    private final String[] teamNames;
    private final Integer[] teamWins;

    public CustomTeamsListViewNotClickableWithScoreAdapter(Activity context, int formatType, String[] teamNames,
                                                           Integer[] teamWins, int tournament_id) {
        super(context, R.layout.custom_teams_list_view, teamNames);
        this.context = context;
        this.formatType = formatType;
        this.tournament_id = tournament_id;
        this.teamNames = teamNames;
        this.teamWins = teamWins;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        // Set up the layout of the list
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.custom_teams_list_view_with_scores, null, true);
        // Set up the names of the teams
        TextView teamNameForStandingsTextView = rowView.findViewById(R.id.teamNameForStandingsTextView);
        teamNameForStandingsTextView.setText(teamNames[position]);
        // Set up the number of wins for each team
        TextView numberOfWinsTextView = rowView.findViewById(R.id.numberOfWinsTextView);
        numberOfWinsTextView.setText(String.valueOf(teamWins[position]));
        // If the format is Knockout, change "Score:" to "Wins:"
        if (formatType == 2) {
            TextView scoreIndicatorTextView = rowView.findViewById(R.id.scoreIndicatorTextView);
            scoreIndicatorTextView.setText("Wins:");
        }
        // If a team has been eliminated, reduce its opacity
        if (DBAdapter.getTeamFormatPosition(context, teamNames[position], tournament_id) == - 1) {
            teamNameForStandingsTextView.setAlpha(0.5f);
            numberOfWinsTextView.setAlpha(0.5f);
            TextView scoreIndicatorTextView = rowView.findViewById(R.id.scoreIndicatorTextView);
            scoreIndicatorTextView.setAlpha(0.5f);

        }
        return rowView;
    }
}
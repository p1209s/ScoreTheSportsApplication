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

public class CustomTournamentsListViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] tournamentNames;
    private final String[] tournamentStatus; // Under Creation (1) or Started (2)
    private final boolean deleting;
    public CustomTournamentsListViewAdapter(Activity context, String[] tournamentNames,
                                            String[] tournamentStatus, boolean deleting) {
        super(context, R.layout.custom_teams_list_view, tournamentNames);
        this.context = context;
        this.tournamentNames = tournamentNames;
        this.tournamentStatus = tournamentStatus;
        this.deleting = deleting;
    }
    public View getView(int position, View view, ViewGroup parent) {
        // Set up the layout of the list
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.custom_tournaments_list_view, null, true);
        // Set up the names of the tournaments
        TextView tournamentNameTextView = rowView.findViewById(R.id.tournamentNameTextView);
        tournamentNameTextView.setText(tournamentNames[position]);
        // Set up the statuses of the tournaments
        TextView tournamentStatusTextView = rowView.findViewById(R.id.statusTextView);
        tournamentStatusTextView.setText(tournamentStatus[position]);
        // If we are in deleting mode, change the indicator
        if (deleting) {
            @SuppressLint("CutPasteId") ImageView indicator = rowView.findViewById(R.id.tournamentsModeIndicator);
            indicator.setImageResource(R.drawable.delete_x);
        }
        // If the tournament is finished, reduce opacity
        if (tournamentStatus[position].equals("Finished")) {
            tournamentNameTextView.setAlpha(0.5f);
            tournamentStatusTextView.setAlpha(0.5f);
            @SuppressLint("CutPasteId") ImageView tournamentsModeIndicator = rowView.findViewById(R.id.tournamentsModeIndicator);
            tournamentsModeIndicator.setAlpha(0.5f);
        }
        return rowView;
    }
}
package com.example.score.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.score.R;
import com.example.score.adapters.CustomTournamentsListViewAdapter;
import com.example.score.database.DBAdapter;

import java.util.ArrayList;

public class LoadTournamentActivity extends Activity {
    private String[] tournamentsNames;
    private String[] tournamentsStatus;
    private boolean deletingTournaments = false;
    private ListView tournamentsListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_load_tournament);
        setUpTournamentsList();
        // If there are no tournaments left, disable the button
        if (DBAdapter.getTournaments(getApplicationContext()).isEmpty()) {
            disableButton();
        }
    }

    @SuppressLint("SetTextI18n")
    public void deleteAndDoneTournamentOnClick(View view) {
        // If we are switching to the tournament deleting mode
        if (!deletingTournaments) {
            // Set the deleting tournament mode flag to true
            deletingTournaments = true;
            // Reset the list of tournaments
            setUpTournamentsList();
            // Set up on the onClick for the tournaments list for deleting
            tournamentsListView.setOnItemClickListener((arg0, view1, pos, arg3) -> {
                // Get the tournament name
                TextView tournamentNameTextView = view1.findViewById(R.id.tournamentNameTextView);
                String tournamentName = tournamentNameTextView.getText().toString();
                // Get the tournament id
                int tournament_id = DBAdapter.getTournamentId(getApplicationContext(), tournamentName);
                // Delete the tournament from the database
                DBAdapter.deleteTournament(getApplicationContext(), tournament_id);
                // Set the deleting tournaments flag to false
                deletingTournaments = false;
                // If there are no tournaments left, disable the button
                if (DBAdapter.getTournaments(getApplicationContext()).isEmpty()) {
                    disableButton();
                }
                // Otherwise
                else {
                    // Reset the tournaments list by pressing the button dynamically
                    Button deleteAndDoneTournamentButton = findViewById(R.id.deleteAndDoneTournamentButton);
                    deleteAndDoneTournamentButton.performClick();
                }
            });
            // Rename the button
            Button deleteAndDoneTournamentButton = findViewById(R.id.deleteAndDoneTournamentButton);
            deleteAndDoneTournamentButton.setText("Done");
        }
        // If we are switching back to the normal mode
        else {
            // Set the deleting tournament mode flag to true
            deletingTournaments = false;
            // Reset the list of tournaments
            setUpTournamentsList();
            // Rename the button
            Button deleteAndDoneTournamentButton = findViewById(R.id.deleteAndDoneTournamentButton);
            deleteAndDoneTournamentButton.setText("Delete");
        }
    }


    private void setUpTournamentsList() {
        // Get the information from the database
        ArrayList<String> tournamentsNamesArray = DBAdapter.getTournamentNames(this.getApplicationContext());
        ArrayList<String> tournamentsStatusArray = DBAdapter.getTournamentStatuses(this.getApplicationContext());
        // Convert the tournament names to a String array
        tournamentsNames = new String[tournamentsNamesArray.size()];
        tournamentsNames = tournamentsNamesArray.toArray(tournamentsNames);
        // Convert the tournament statuses to a String array
        tournamentsStatus = new String[tournamentsStatusArray.size()];
        tournamentsStatus = tournamentsStatusArray.toArray(tournamentsStatus);
        // Convert the statuses to their respective String
        String[] tournamentsStatusConverted = new String[tournamentsStatus.length];
        for(int i = 0; i < tournamentsStatusConverted.length; i++) {
            if(tournamentsStatus[i].equals("" + 1))
                tournamentsStatusConverted[i] = "Under Creation";
            else if(tournamentsStatus[i].equals("" + 2))
                tournamentsStatusConverted[i] = "Started";
            else
                tournamentsStatusConverted[i] = "Finished";
        }
        // Create the tournaments list adapter and set it
        CustomTournamentsListViewAdapter adapter = new CustomTournamentsListViewAdapter(LoadTournamentActivity.this,
                tournamentsNames, tournamentsStatusConverted, deletingTournaments);
        tournamentsListView = findViewById(R.id.tournamentsListView);
        tournamentsListView.setAdapter(adapter);
        // Set up on the onClick for the tournaments list
        tournamentsListView.setOnItemClickListener((arg0, view, pos, arg3) -> {
            // Get the tournament name
            TextView tournamentNameTextView = view.findViewById(R.id.tournamentNameTextView);
            String tournamentName = tournamentNameTextView.getText().toString();
            // Get the tournament id
            int tournament_id = DBAdapter.getTournamentId(getApplicationContext(), tournamentName);
            // Get the tournament status
            int tournamentStatus = DBAdapter.getTournamentStatus(getApplicationContext(), tournament_id);
            // If the tournament is under creation
            if (tournamentStatus == 1) {
                // Put the information in the intent
                Intent intent = new Intent(getApplicationContext(), CreateTournamentActivity.class);
                intent.putExtra("tournamentName", tournamentName);
                intent.putExtra("tournament_id", tournament_id);
                // Start the create tournament activity
                startActivityForResult(intent, 1);
                // If the tournament is started
            }else {
                // Put the information in the intent
                Intent intent = new Intent(getApplicationContext(), StandingsActivity.class);
                intent.putExtra("tournament_id", tournament_id);
                intent.putExtra("cameFromLoadActivity", true);
                // Start the standings activity
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setUpTournamentsList();
    }

    public void onBackPressed() {
        // Go back to the Home page
        Intent intent = new Intent(getApplicationContext(), Schedule.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void disableButton() {
        Button deleteAndDoneTournamentButton = findViewById(R.id.deleteAndDoneTournamentButton);
        deleteAndDoneTournamentButton.setClickable(false);
        deleteAndDoneTournamentButton.getBackground().setAlpha(100);
        deleteAndDoneTournamentButton.setText("Delete");
        setUpTournamentsList();
    }
}

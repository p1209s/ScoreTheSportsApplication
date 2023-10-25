package com.example.score.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;
import com.example.score.adapters.CustomNumCircuitsSpinnerAdapter;
import com.example.score.adapters.CustomTeamsListViewAdapter;
import com.example.score.database.DBAdapter;

import java.util.ArrayList;

/**
 * This activity is used by the user to create, edit and start tournaments.
 */
public class CreateTournamentActivity extends Activity {
    int tournament_id;
    String tournamentName;
    String[] teamNames;
    Integer[] teamLogos; // These integer correspond to the resource IDs of the drawables
    ListView teamsList;
    boolean deletingTeams = false;
    Spinner numCircuitsSpinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_create_tournament);

        tournament_id = getIntent().getIntExtra("tournament_id", -1);
        setUpTeamsList(false);
        numCircuitsSpinner = findViewById(R.id .numCircuitsSpinner);
        Integer[] numCircuits = new Integer[]{1, 2, 3, 4, 5};
        CustomNumCircuitsSpinnerAdapter adapter = new CustomNumCircuitsSpinnerAdapter(this, R.layout.custom_num_circuits_spinner, numCircuits);
        numCircuitsSpinner.setAdapter(adapter);
        /* If the tournament was already created and is now being edited */
        if (getIntent().hasExtra("tournamentName")) { // If the intent passed a name, then the tournament is being edited
            // Set the tournament name
            EditText tournamentNameTextView = findViewById(R.id.tournamentNameEditText);
            tournamentName = getIntent().getStringExtra("tournamentName");
            tournamentNameTextView.setText(tournamentName);
            // Set the format type in the radio group
            int formatType = DBAdapter.getTournamentFormatType(getApplicationContext(), tournament_id);
            RadioGroup formatTypeRadioGroup = findViewById(R.id.formatTypeRadioGroup);
            if (formatType == 1) {
                formatTypeRadioGroup.check(R.id.roundRobinRadioButton);
            } else if (formatType == 2) {
                formatTypeRadioGroup.check(R.id.knockoutRadioButton);
                numCircuitsSpinner.setEnabled(false);
            }
            // Set the number of Round Robin circuits
            numCircuitsSpinner.setSelection((DBAdapter.getTournamentNumCircuits(getApplicationContext(), tournament_id)) - 1);
        }
        // If there are no teams, disable the button
        if (DBAdapter.getTeamNames(getApplicationContext(), tournament_id).isEmpty()) {

            disableDeleteTeamButton();
        }
    }

    public void addTeamTournamentOnClick(View view) {
        Intent intent = new Intent(this, EditTeamActivity.class);
        intent.putExtra("tournament_id", tournament_id);
        startActivityForResult(intent, 0);
    }

    @SuppressLint("SetTextI18n")
    public void deleteAndDoneTeamOnClick(View view) {
        /* If we are setting up the teams list for deletion */
        if (!deletingTeams) {
            deletingTeams = true;
            setUpTeamsList(true);
            // Set up on the onClick for the teams list for deleting
            teamsList.setOnItemClickListener((arg0, view1, pos, arg3) -> {
                // Get the team name
                TextView teamNameTextView = view1.findViewById(R.id.txt);
                String teamName = teamNameTextView.getText().toString();
                // Delete the team from the database
                DBAdapter.deleteTeam(getApplicationContext(), teamName, tournament_id);
                // Set the deleting teams flag to false
                deletingTeams = false;
                // If there are no teams left, disable the button
                if (DBAdapter.getTeamNames(getApplicationContext(), tournament_id).isEmpty()) {
                    disableDeleteTeamButton();
                }
                // Otherwise
                else {
                    // Reset the teams list by pressing the button dynamically
                    Button deleteAndDoneTeamButton = findViewById(R.id.deleteAndDoneTeamButton);
                    deleteAndDoneTeamButton.performClick();
                }
            });
            // Rename the button
            Button deleteAndDoneTeamButton = findViewById(R.id.deleteAndDoneTeamButton);
            deleteAndDoneTeamButton.setText("Done");
        }
        /* If we are setting up the teams list back to normal */
        else {
            // Set the deleting teams flag to true
            deletingTeams = false;
            // Set up the list of teams
            setUpTeamsList(false);
            // Rename the button
            Button deleteAndDoneTeamButton = findViewById(R.id.deleteAndDoneTeamButton);
            deleteAndDoneTeamButton.setText("Delete");
        }
    }

    @SuppressLint("SetTextI18n")
    public void saveOnClick(View view) {
        // Save all the user entered data
        if (saveData()) {
            // If the data was saved successfully
            // Display a toast informing the user the data has been saved
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    findViewById(R.id.custom_toast_layout_id));
            // Set the message
            TextView text = layout.findViewById(R.id.text);
            text.setText("Tournament Saved");
            // Set up the toast
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean saveData() {
        // Get the tournament name from the EditText
        EditText tournamentNameEditText = findViewById(R.id.tournamentNameEditText);
        String tournamentName = tournamentNameEditText.getText().toString();
        try {
            // Save the tournament name in the database
            DBAdapter.changeTournamentName(getApplicationContext(), tournament_id, tournamentName);
            // Save the format type
            RadioGroup radioGroup = findViewById(R.id.formatTypeRadioGroup);
            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(radioButtonID);
            int formatType = radioGroup.indexOfChild(radioButton);
            formatType++; // 1: RoundRobin, 2: Knockout, 3: Combination
            DBAdapter.saveTournamentFormatType(getApplicationContext(), formatType, tournament_id);
            // Save the number of round robin circuits
            int numCircuits = numCircuitsSpinner.getSelectedItemPosition() + 1;
            // If the format is Knockout, set that number to be 0
            if (formatType == 2)
                numCircuits = 0;
            DBAdapter.saveTournamentNumCircuits(getApplicationContext(), numCircuits, tournament_id);
            // Return true because the data was saved successfully
            return true;
            // If the tournament name is already in use
            }catch(IllegalArgumentException e){
                // Pop up a dialog
                final Dialog alertTournamentNameAlreadyInUse = new Dialog(CreateTournamentActivity.this);
                alertTournamentNameAlreadyInUse.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertTournamentNameAlreadyInUse.setContentView(R.layout.custom_alert_ok);
                // Set the message
                TextView messageTournamentNameAlreadyInUse = alertTournamentNameAlreadyInUse.findViewById(R.id.messageOkTextView);
                messageTournamentNameAlreadyInUse.setText("Tournament name already in use.");
                Button okButton = alertTournamentNameAlreadyInUse.findViewById(R.id.okButton);
                okButton.setOnClickListener(v -> alertTournamentNameAlreadyInUse.dismiss());
                alertTournamentNameAlreadyInUse.show();
                // Return false because the data was not saved
                return false;
                // If the tournament name is empty
            }catch(NullPointerException e){
                // Pop up a dialog
                final Dialog alertTournamentNameEmpty = new Dialog(CreateTournamentActivity.this);
                alertTournamentNameEmpty.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertTournamentNameEmpty.setContentView(R.layout.custom_alert_ok);
                // Set the message
                TextView messageTournamentNameAlreadyInUse = alertTournamentNameEmpty.findViewById(R.id.messageOkTextView);
                messageTournamentNameAlreadyInUse.setText("Please enter a tournament name.");
                Button okButton = alertTournamentNameEmpty.findViewById(R.id.okButton);
                okButton.setOnClickListener(v -> alertTournamentNameEmpty.dismiss());
                alertTournamentNameEmpty.show();
                return false;
            }
    }

    public void deleteOnClick(View view) {
        // Ask the user for confirmation on deleting the tournament
        // Pop up a dialog
        final Dialog alertConfirmDeletion = new Dialog(CreateTournamentActivity.this);
        alertConfirmDeletion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertConfirmDeletion.setContentView(R.layout.custom_alert_yes_not);
        Button yesDeleteButton = alertConfirmDeletion.findViewById(R.id.yesDeleteButon);
        Button noDeleteButton = alertConfirmDeletion.findViewById(R.id.noDeleteButon);
        /* If the user clicks Yes, then delete the tournament */
        yesDeleteButton.setOnClickListener(v -> {
            // If we came from the Load Tournament Activity
            if (getIntent().hasExtra("tournamentName")) {
                // Go back to the Load Tournament activity
                Intent intent = new Intent(getApplicationContext(), LoadTournamentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear current activities
                finish();
                startActivity(intent);
                // If we came from the Home Activity
            } else {
                Intent intent = new Intent(getApplicationContext(), Schedule.class);
                alertConfirmDeletion.dismiss();
                finish();
                startActivity(intent);
            }
            // Delete the tournament from the database
            DBAdapter.deleteTournament(getApplicationContext(), tournament_id);
        });
        /* If the user clicks No, then do not delete the tournament */
        // Dismiss the alert
        noDeleteButton.setOnClickListener(v -> alertConfirmDeletion.dismiss());
        alertConfirmDeletion.show();
    }

    @SuppressLint("SetTextI18n")
    public void startOnClick(View view) {
        /* Check if the tournament has less than three teams.
           If it does, do not start and inform the user */
        int numTeams = DBAdapter.getNumTeamsForTournament(getApplicationContext(), tournament_id);
        // If the tournament has less than three teams
        if (numTeams < 3) {
            // Pop up a dialog to inform the user
            final Dialog alertNotEnoughTeams = new Dialog(CreateTournamentActivity.this);
            alertNotEnoughTeams.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertNotEnoughTeams.setContentView(R.layout.custom_alert_ok);
            Button okButton = alertNotEnoughTeams.findViewById(R.id.okButton);
            okButton.setOnClickListener(v -> alertNotEnoughTeams.dismiss());
            alertNotEnoughTeams.show();
        /* If the tournament has at least three teams,
            then proceed with starting it. */
        } else {
            // Ask the user for confirmation on starting the tournament
            // Pop up a dialog
            final Dialog alertConfirmStart = new Dialog(CreateTournamentActivity.this);
            alertConfirmStart.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertConfirmStart.setContentView(R.layout.custom_alert_yes_not);
            // Set the correct message
            TextView confirmationMessage = alertConfirmStart.findViewById(R.id.confirmMessageTextView);
            confirmationMessage.setText("Are you sure you want to start the tournament now?");
            // Initialize the buttons
            Button yesDeleteButton = alertConfirmStart.findViewById(R.id.yesDeleteButon);
            Button noDeleteButton = alertConfirmStart.findViewById(R.id.noDeleteButon);
            /* If the user clicks Yes to start */
            // Start the tournament
            yesDeleteButton.setOnClickListener(v -> {
                alertConfirmStart.dismiss();
                // Before starting, save the tournament data
                if (saveData()) {
                    // If saving is successful, start the tournament and go to the Standings page
                    Intent intent = new Intent(getApplicationContext(), StandingsActivity.class);
                    intent.putExtra("tournament_id", tournament_id);
                    finish();
                    startActivity(intent);
                }
            });
            /* If if the user clicks No, then do not start */
            // Dismiss the alert
            noDeleteButton.setOnClickListener(v -> alertConfirmStart.dismiss());
            alertConfirmStart.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setUpTeamsList(deletingTeams);
        if (deletingTeams) {
            // Reset the teams list
            Button deleteAndDoneTeamOnClick = findViewById(R.id.deleteAndDoneTeamButton);
            deleteAndDoneTeamOnClick.performClick();
        }
        // If there are teams, activate the Delete teams button
        if (!DBAdapter.getTeamNames(getApplicationContext(), tournament_id).isEmpty()) {
            enableDeleteTeamButton();
        }
        // If there are no teams, disable the Delete teams button
        else {
            disableDeleteTeamButton();
        }
    }

    public void roundRobinOnClick(View view) {
        numCircuitsSpinner.setEnabled(true); // Enables the user to change the number of circuits
    }

    public void knockoutRobinOnClick(View view) {
        numCircuitsSpinner.setEnabled(false); // Disables the user from changing the number of circuits
    }

    public void combinationRobinOnClick(View view) {
        numCircuitsSpinner.setEnabled(true); // Enables the user to change the number of circuits
    }

    private void setUpTeamsList(boolean deleting) {
        /* Set up the list of teams */
        // Get the information from the database
        ArrayList<String> teamNamesArray = DBAdapter.getTeamNames(this.getApplicationContext(), tournament_id);
        ArrayList<String> teamLogosArray = DBAdapter.getTeamLogos(this.getApplicationContext(), tournament_id);
        // Convert the team names to a String array
        teamNames = new String[teamNamesArray.size()];
        teamNames = teamNamesArray.toArray(teamNames);
        // Convert the team logos to an integer array of the resource ids
        teamLogos = new Integer[teamLogosArray.size()];
        for (int i = 0; i < teamLogos.length; i++) {
            teamLogos[i] = this.getResources().getIdentifier(teamLogosArray.get(i), "drawable", this.getPackageName());
        }
        // Create the teams list adapter and set it
        CustomTeamsListViewAdapter adapter = new CustomTeamsListViewAdapter(CreateTournamentActivity.this,
                teamNames, deleting);
        teamsList = findViewById(R.id.teamsListView);
        teamsList.setAdapter(adapter);
        // Set up on the onClick for the teams list
        teamsList.setOnItemClickListener((arg0, view, pos, arg3) -> {
            // Get the team name
            TextView teamNameTextView = view.findViewById(R.id.txt);
            String teamName = teamNameTextView.getText().toString();

            // Put the information in the intent
            Intent intent = new Intent(getApplicationContext(), EditTeamActivity.class);
            intent.putExtra("teamName", teamName);
            intent.putExtra("teamLogo", DBAdapter.getTeamLogo(getApplicationContext(), teamName, tournament_id));
            intent.putExtra("tournament_id", tournament_id);

            // Start the edit team activity
            startActivityForResult(intent, 0);
        });
    }

    @SuppressLint("SetTextI18n")
    private void disableDeleteTeamButton() {
        Button deleteAndDoneTeamButton = findViewById(R.id.deleteAndDoneTeamButton);
        deleteAndDoneTeamButton.setEnabled(false);
        deleteAndDoneTeamButton.setAlpha(0);
        deleteAndDoneTeamButton.setText("Delete");
        setUpTeamsList(false);

    }

    @SuppressLint("SetTextI18n")
    private void enableDeleteTeamButton() {
        Button deleteAndDoneTeamButton = findViewById(R.id.deleteAndDoneTeamButton);
        deleteAndDoneTeamButton.setEnabled(true);
        deleteAndDoneTeamButton.setAlpha(1f);
        deleteAndDoneTeamButton.setText("Delete");
        setUpTeamsList(false);
    }

}
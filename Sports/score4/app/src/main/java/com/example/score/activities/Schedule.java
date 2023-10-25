package com.example.score.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.score.HomeActivity;
import com.example.score.R;
import com.example.score.database.DB;
import com.example.score.database.DBAdapter;

public class Schedule extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_schedule);

        DB dbHelper = new DB(this.getApplicationContext());
    }

    public void newTournamentOnClick(View view) {
        // Create a new tournament in the database.
        DBAdapter.newTournament(getApplicationContext());
        int tournament_id = DBAdapter.getMostRecentTournamentId(getApplicationContext());
        Intent intent = new Intent(this, CreateTournamentActivity.class);
        intent.putExtra("tournament_id", tournament_id);
        startActivityForResult(intent, 1);
    }

    public void loadTournamentOnClick(View view) {
        Intent intent = new Intent(this, LoadTournamentActivity.class);
        startActivityForResult(intent, 1);
    }
    public void onBackPressed() {
        // Go back to the Home page
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

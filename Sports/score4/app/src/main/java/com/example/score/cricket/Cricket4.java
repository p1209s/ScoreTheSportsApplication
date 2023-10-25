package com.example.score.cricket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.score.R;

public class Cricket4 extends AppCompatActivity implements OnClickListener {
    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";

    private static final String GET_SCORE_T1 = "getScoreT1";
    private static final String GET_OVER_T1 = "getOverT1";
    private static final String GET_BALLS_T1 = "getBallsT1";
    private static final String GET_WICKET_T1 = "getWicketT1";
    private static final String GET_SCORE_T2 = "getScoreT2";
    private static final String GET_OVER_T2 = "getOverT2";
    private static final String GET_BALLS_T2 = "getBallsT2";
    private static final String GET_WICKET_T2 = "getWicketT2";

    private static final String GET_TEAM1STATE = "getTeam1State";
    private static final String GET_TEAM2STATE = "getTeam2State";

    private SharedPreferences mPrefs;
    private TextView nameT1, nameT2, scoreT1, scoreT2, ballOverT1, ballOverT2, maxPlayer, maxOver;
    private Button btnTeamOne, btnTeamTwo, newMatch;
    private String[] mOversInningT1, mOversInningT2;
    private int[] mRunsOfOverT1, mRunsOfOverT2, mRunsAfterOverT1, mRunsAfterOverT2;

    private int mOvers;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket4);
        setTitle("Cricket Scorecard");


        btnTeamOne = findViewById(R.id.Team1_btn);
        btnTeamOne.setOnClickListener(this);
        btnTeamTwo = findViewById(R.id.Team2_btn);
        btnTeamTwo.setOnClickListener(this);
        newMatch = findViewById(R.id.new_match);
        newMatch.setOnClickListener(this);
        newMatch.setVisibility(View.INVISIBLE);
        nameT1 = this.findViewById(R.id.Team1_name);
        nameT2 = this.findViewById(R.id.Team2_name);
        scoreT1 = this.findViewById(R.id.Team1_score);
        scoreT2 = this.findViewById(R.id.Team2_score);
        ballOverT1 = this.findViewById(R.id.Team1_ball);
        ballOverT2 = this.findViewById(R.id.Team2_ball);
        maxPlayer = this.findViewById(R.id.max_players);
        maxOver = this.findViewById(R.id.max_over);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int team1Score, team2Score, team1Wickets, team2Wickets, team1Over, team2Over,
                team1Ball, team2Ball;
        boolean team1State, team2State;

        final int MAX_OVER = mPrefs.getInt(GET_MAX_OVER, 0);
        final int MAX_PLAYER = mPrefs.getInt(GET_MAX_PLAYER, 0);

        String team1Name = mPrefs.getString(GET_TEAM1_NAME, "Inning 1");
        String team2Name = mPrefs.getString(GET_TEAM2_NAME, "Inning 2");
        team1State = mPrefs.getBoolean(GET_TEAM1STATE, false);
        team2State = mPrefs.getBoolean(GET_TEAM2STATE, false);

        team1Score = mPrefs.getInt(GET_SCORE_T1, 0);
        team1Ball = mPrefs.getInt(GET_BALLS_T1, 0);
        team1Wickets = mPrefs.getInt(GET_WICKET_T1, 0);
        team1Over = mPrefs.getInt(GET_OVER_T1, 0);
        team2Score = mPrefs.getInt(GET_SCORE_T2, 0);
        team2Ball = mPrefs.getInt(GET_BALLS_T2, 0);
        team2Wickets = mPrefs.getInt(GET_WICKET_T2, 0);
        team2Over = mPrefs.getInt(GET_OVER_T2, 0);

        mOversInningT1 = new String[MAX_OVER];
        mRunsOfOverT1 = new int[MAX_OVER];
        mRunsAfterOverT1 = new int[MAX_OVER];
        for (int x = 0; x < MAX_OVER; x++) {
            mOversInningT1[x] = mPrefs.getString("overviewT1_" + x, "");
            mRunsOfOverT1[x] = mPrefs.getInt("runsOfOverT1_" + x, 0);
            mRunsAfterOverT1[x] = mPrefs.getInt("runsAfterOverT1_" + x, 0);
        }

        mOversInningT2 = new String[MAX_OVER];
        mRunsOfOverT2 = new int[MAX_OVER];
        mRunsAfterOverT2 = new int[MAX_OVER];
        //mOversInning2 = new String[mPrefs.getInt("overOverview_sizeT2", 0)];
        for (int x = 0; x < MAX_OVER; x++) {
            mOversInningT2[x] = mPrefs.getString("overviewT2_" + x, "");
            mRunsOfOverT2[x] = mPrefs.getInt("runsOfOverT2_" + x, 0);
            mRunsAfterOverT2[x] = mPrefs.getInt("runsAfterOverT2_" + x, 0);
        }

        maxPlayer.setText("Players Playing: " + MAX_PLAYER);
        maxOver.setText("Max Overs: " + MAX_OVER);
        nameT1.setText(team1Name + " (Inning 1)");
        nameT1.setPaintFlags(nameT1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        nameT2.setText(team2Name + " (Inning 2)");
        nameT2.setPaintFlags(nameT1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if (team1State && !team2State) {
            scoreT1.setText(team1Score + "/" + team1Wickets);
            ballOverT1.setText(team1Over + "." + team1Ball);

            scoreT2.setText("DNP");
            ballOverT2.setText("DNP");
            btnTeamTwo.setEnabled(false);
        } else if (!team1State && team2State) {
            scoreT1.setText(team1Score + "/" + team1Wickets);
            ballOverT1.setText(team1Over + "." + team1Ball);

            scoreT2.setText(team2Score + "/" + team2Wickets);
            ballOverT2.setText(team2Over + "." + team2Ball);
            btnTeamTwo.setEnabled(true);
        }
        if(getIntent().getBooleanExtra("showNextBtn", false)){
            newMatch.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View v) {

        if (v.getId() == R.id.Team1_btn) {
            ScrollView s = new ScrollView(this);
            s.setPadding(75, 0, 75, 0);
            LinearLayout vh = new LinearLayout(this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mOversInningT1.length; i++) {
                TextView t = new TextView(this);
                String message;
                if (mOversInningT1[i].isEmpty()) {
                    message = "OVER NOT PLAYED YET";
                } else {
                    message = " " + mOversInningT1[i] + " = " + mRunsOfOverT1[i] + "  > " +
                            mRunsAfterOverT1[i] + " total Runs";
                }
                t.setText("Over " + (i + 1) + ": " + message);
                t.setTextSize(14);
                vh.addView(t);
            }
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Ok", (dialog, which) -> dialog.dismiss()).show();
        }
        if (v.getId() == R.id.Team2_btn) {
            ScrollView s = new ScrollView(this);
            s.setPadding(75, 0, 75, 0);
            LinearLayout vh = new LinearLayout(this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mOversInningT2.length; i++) {
                TextView t = new TextView(this);
                String message;
                if (mOversInningT2[i].isEmpty()) {
                    message = "OVER NOT PLAYED YET";
                } else {
                    message = " " + mOversInningT2[i] + " = " + mRunsOfOverT2[i] + "  > " +
                            mRunsAfterOverT2[i] + " total Runs";
                }
                t.setText("Over " + (i + 1) + ": " + message);
                t.setTextSize(14);
                vh.addView(t);
            }
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Ok", (dialog, which) -> dialog.dismiss()).show();
        }

        if (v.getId() == R.id.new_match){
            Intent i = new Intent(this, Cricket1.class);
            i.putExtra("newStart", true);
            startActivity(i);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(getIntent().getBooleanExtra("showNextBtn", false)) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                new AlertDialog.Builder(Cricket4.this).setTitle("Match Complete")
                        .setMessage("Both Innings are complete")
                        .setPositiveButton("Ok", (dialog, which) -> {
                        }).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
package com.example.score.cricket;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CricketWinnerFinder {
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private SharedPreferences mPrefs;
    private int teamARuns, teamBRuns, teamAWickets, teamBWickets, teamABalls, teamBBalls;
    private String send, teamAName, teamBName;
    private int MAX_OVER, MAX_PLAYER;

    public CricketWinnerFinder(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        MAX_OVER = mPrefs.getInt(GET_MAX_OVER, 0);
        MAX_PLAYER = mPrefs.getInt(GET_MAX_PLAYER, 0);
        teamAName = mPrefs.getString(GET_TEAM1_NAME, " ");
        teamBName = mPrefs.getString(GET_TEAM2_NAME, " ");
    }

    public void setTeamARuns(int runs) {
        this.teamARuns = runs;
    }

    public void setTeamBRuns(int runs) {
        this.teamBRuns = runs;
    }

    public void setTeamAWickets(int wickets) {
        this.teamAWickets = wickets;
    }

    public void setTeamBWickets(int wickets) {
        this.teamBWickets = wickets;
    }

    public void setTeamABalls(int balls) {this.teamABalls = balls;}

    public void setTeamBBalls(int balls) {this.teamBBalls = balls;}

    public int getTeamAWickets() {
        return teamAWickets;
    }

    public int getTeamBWickets() {
        return teamBWickets;
    }

    public int getTeamARuns() {
        return teamARuns;
    }

    public int getTeamBRuns() {
        return teamBRuns;
    }

    public int getTeamABalls(){ return teamABalls;}

    public int getTeamBBalls(){ return teamBBalls;}

    public String whoWon() {

        if (teamARuns > teamBRuns) {
            send = "Team " + teamAName + " won by: " + String.valueOf(getTeamARuns() - getTeamBRuns()) + " runs.";
        } else if (teamARuns < teamBRuns) {
            send = "Team " + teamBName + " won by: " + String.valueOf(MAX_PLAYER - getTeamBWickets() - 1) + " wickets";
        }else {
            send = "Both teams tied.";
        }
        return send;
    }
}

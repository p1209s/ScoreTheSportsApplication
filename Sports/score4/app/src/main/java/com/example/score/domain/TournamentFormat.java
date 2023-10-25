package com.example.score.domain;

import android.content.Context;

import com.example.score.database.DBAdapter;

import java.util.ArrayList;

public abstract class TournamentFormat {

    public int size;
    public int currentRound;
    protected int[] rounds;
    protected int tournament_id;
	protected ArrayList<String> formatTeams;
    protected ArrayList<String> orderedTeams;
    protected boolean isRR;
    protected boolean justSwitched = false;

	public TournamentFormat(Context context, int tournament_id) {

		formatTeams = new ArrayList<>();

        isRR = false;

        this.tournament_id = tournament_id;

		currentRound = DBAdapter.getCurrentRound(context, tournament_id); // Used for offsetting match-ups and for preventing null pointers on empty round list
	}

    public void setCurrentRound(Context context) {

        currentRound = DBAdapter.getCurrentRound(context, tournament_id);
    }

    public boolean getJustSwitched() {

        return justSwitched;
    }
    public void setJustSwitched(boolean value) {

        justSwitched = value;
    }

    public void setUpFormat(Context context, int tournament_id, int totalCircuits, ArrayList<String> teams) {

        int size = teams.size();

        // Set up the format in the database
        DBAdapter.setUpFormat(context, tournament_id, totalCircuits, size);

        // Randomize the teams list
        for(int i = 0; i < size; i++) {

            // Remove a random team and save it
            int rand = (int) (Math.random() * (teams.size() - 1));
            String removedTeam = teams.remove(rand);

            // Add it back to the end of the list
            teams.add(i, removedTeam);
        }

        // Set up the format positions for each team
        for(int j = 1; j < size; j++) {

            DBAdapter.setTeamFormatPosition(context, tournament_id, teams.get(j), j);
        }
    }

    public void createNextRound(Context context, int tournament_id) {

        // Initialize variables
        int format_id = DBAdapter.getFormatId(context, tournament_id);
        currentRound = DBAdapter.getCurrentRound(context, tournament_id);

        // Create the round
        DBAdapter.insertRound(context, tournament_id, size, format_id);

        // See algorithm: http://assets.usta.com/assets/650/USTA_Import/Northern/dps/doc_37_1812.pdf

        ArrayList<String> leftTeams= new ArrayList<>();
        ArrayList<String> rightTeams= new ArrayList<>();

        // If Number of teams is ODD, add a flag for the bye match
        if(orderedTeams.size()%2 == 1) {

            orderedTeams.add("BYE");
        }

        // Remove first teamName
        leftTeams.add(orderedTeams.remove(0));

        // Shift remaining teams counter-clockwise according to round (i.e.: first round 0 times, second 1 time...)
        // Removes last element and adds to front
        int last = orderedTeams.size() - 1;
        for(int c = 0; c < currentRound; c++ ){

            String temp = orderedTeams.remove(last);
            orderedTeams.add(0,temp);
        }

        // Split teams into left and right (except for last team as teams will be odd-numbered at that point)
        int counter = (orderedTeams.size()/2);
        for (int c = 0; c < (counter);c++) {

            // Remove first
            String tempRightTeam= orderedTeams.remove(0);

            // Remove first
            String tempLeftTeam = orderedTeams.remove(0);

            // Adjusting for BYE (i.e.: in a BYE, team wins against self (cannot use bye as team name since it would require a full team object in DB)
            if (tempLeftTeam.equals("BYE")) {

                tempLeftTeam = orderedTeams.get(0);
            }
            else if (tempRightTeam.equals("BYE")) {

                tempRightTeam = leftTeams.get(leftTeams.size() - 1);
            }

            rightTeams.add(tempRightTeam);
            leftTeams.add(tempLeftTeam);
        }

        String tempRightTeam = orderedTeams.remove(0);

        // Add the remaining teams to rightTeams
        // In case of a bye
        if (tempRightTeam.equals("BYE")) {

            rightTeams.add(leftTeams.get(leftTeams.size()-1));
        }
        else {
            rightTeams.add(tempRightTeam);
        }

        // Set up every match
        for(int c = 0; c < leftTeams.size(); c++) {

            String leftTeam = leftTeams.get(c);
            String rightTeam = rightTeams.get(c);

           new Match(context, DBAdapter.getRoundID(context, currentRound, tournament_id), tournament_id, leftTeam, rightTeam);
        }

		// Increment the current round
        currentRound = currentRound + 1;
        DBAdapter.setCurrentRound(context, format_id, currentRound);
    }

    public boolean checkIsRoundComplete(Context context) {

        // Get the list of updated values for the latest round
        int currentRoundID = DBAdapter.getCurrentRoundID(context, tournament_id);
        ArrayList<Integer> matchesUpdatedValues = DBAdapter.getMatchesUpdatedValues(context, currentRoundID, tournament_id);

        // Go through the list and check if a match has not yet been updated
        for(int i = 0; i < matchesUpdatedValues.size(); i++) {

            if(matchesUpdatedValues.get(i) == 0)
                return false;
        }

        return true;
    }

    public abstract boolean isTournamentComplete(Context context);
}

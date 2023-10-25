package com.example.score.database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DBAdapter {

    public static void newTournament(Context context){
        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Insert the tournament
        db.execSQL("INSERT INTO tournaments (type, numCircuits, status) VALUES (1, 1, 1)");
        try {
            changeTournamentName(context, getMostRecentTournamentId(context), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close the database
        db.close();
    }

    public static void changeTournamentName(Context context, int tournament_id, String name) throws IllegalArgumentException, NullPointerException {
        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Used to generate names automatically without user input
        if(name == null){
            name = "New Tournament " + getMostRecentTournamentId(context);
            db.execSQL("UPDATE tournaments SET name = '" + name +"' WHERE tournament_id = " + tournament_id);
        // If the name is empty
        }else if(name.equals("")){
            throw new NullPointerException();
        }
        // If the name is already used
        else {
            try {
                ContentValues dataToInsert = new ContentValues();
                dataToInsert.put("name", name);
                String[] whereArgs = new String[] {String.valueOf(tournament_id)};
                if(db.updateWithOnConflict("tournaments", dataToInsert, "tournament_id=?", whereArgs, SQLiteDatabase.CONFLICT_IGNORE) == 0)
                    throw new IllegalArgumentException();
            }catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        }
        // Close the database
        db.close();
    }

    public static int getTournamentId(Context context, String name) {
        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Get the information
        int tournament_id = -1;
        // If the given name is null, then return the id of the most recent tournament created
        if(name == null){
            tournament_id = getMostRecentTournamentId(context);
        }else
            try {
                @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments WHERE name = '" + name + "'", null);
                c.moveToFirst();
                try {
                    tournament_id = c.getInt(c.getColumnIndex("tournament_id"));
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                // Do nothing
            }
        // Close the database
        db.close();
        // Return the tournament id
        return tournament_id;
    }

    public static int getMostRecentTournamentId(Context context) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the information
        int tournament_id = -1;

        try {

            final SQLiteStatement stmt = db.compileStatement("SELECT MAX(tournament_id) FROM tournaments");

            tournament_id = (int) stmt.simpleQueryForLong();
        } catch (SQLiteException se ) {

            // Do nothing
        }

        // If a tournament was not found, throw an exception
        if(tournament_id == -1)
            throw new IllegalArgumentException();

        // Close the database
        db.close();

        // Return the tournament id
        return tournament_id;
    }

    public static void insertTournament(Context context, String name, int type, int numCircuits) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the tournament
        db.execSQL("INSERT INTO tournaments (name, type, numCircuits, status) VALUES "
                + "('" + name + "', " + type + ", " + numCircuits + ", 1)");

        // Close the database
        db.close();
    }

    public static void deleteTournament(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the tournament
        db.execSQL("DELETE FROM tournaments WHERE tournament_id = " + tournament_id);

        // Delete the teams associated with that tournament
        db.execSQL("DELETE FROM teams WHERE team_tournament_id = " + tournament_id);

        // Delete the formats associated with that tournament
        db.execSQL("DELETE FROM formats WHERE format_tournament_id = " + tournament_id);

        // Delete the rounds associated with that tournament
        db.execSQL("DELETE FROM rounds WHERE round_tournament_id = " + tournament_id);

        // Delete the matches associated with that tournament
        db.execSQL("DELETE FROM matches WHERE match_tournament_id = " + tournament_id);

        // Delete the match_team_scores associated with that tournament
        db.execSQL("DELETE FROM match_team_scores WHERE match_team_score_tournament_id = " + tournament_id);
        // Close the database
        db.close();
    }

    public static void saveTournamentNumCircuits(Context context, int numCircuits, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // If the number of circuits is

        // Save the number of rounds
        db.execSQL("UPDATE tournaments SET numCircuits = " + numCircuits + " WHERE tournament_id = " + tournament_id);

        // Close the database
        db.close();
    }

    public static void updateTournamentStatus(Context context, int tournament_id, int status) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Save the number of rounds
        db.execSQL("UPDATE tournaments SET status = " + status + " WHERE tournament_id = " + tournament_id);

        // Close the database
        db.close();
    }

    public static String getTournamentName(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        String name = "";

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments WHERE tournament_id = " + tournament_id, null);

            c.moveToFirst();
            {
                try {
                    name = c.getString(c.getColumnIndex("name"));
                }
                catch (Exception e) {
                    throw new IllegalArgumentException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the name
        return name;
    }

    public static int getTournamentNumCircuits(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the information
        int numCircuits = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments WHERE tournament_id = " + tournament_id, null);

            c.moveToFirst();
            {
                try {
                    numCircuits = c.getInt(c.getColumnIndex("numCircuits"));
                }
                catch (Exception e) {
                    throw new IllegalArgumentException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the number of rounds
        return numCircuits;
    }

    public static int getTournamentFormatType(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int formatType = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments WHERE tournament_id = " + tournament_id, null);

            c.moveToFirst();
            {
                try {

                    formatType = c.getInt(c.getColumnIndex("type"));
                }
                catch (Exception e) {

                    throw new Exception();
                }

            }
        } catch (Exception e ) {

            // If an error is encountered, set the default format to Round Robin
            formatType = 1;
        }

        // Close the database
        db.close();

        // Return the format type
        return formatType;
    }

    public static void saveTournamentFormatType(Context context, int formatType, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the tournament format type
        ContentValues dataToPutIn = new ContentValues();
        dataToPutIn.put("type", formatType);
        String[] whereArgs = new String[]{String.valueOf(tournament_id)};
        db.update("tournaments", dataToPutIn, "tournament_id = ?", whereArgs);

        // Close the database
        db.close();
    }

    public static void insertTeam(Context context, String name, String logo, int team_tournament_id) throws IllegalArgumentException, NullPointerException {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // If the name is empty
         if(name.equals("")){

            throw new NullPointerException();
         }

        // Check if the team name already exists
        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE name = '" + name + "' AND team_tournament_id = " + team_tournament_id, null);

            if (c.moveToFirst())
                throw new IllegalArgumentException();

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException();
        }

        // Insert the team
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("name", name);
        dataToInsert.put("logo", logo);
        dataToInsert.put("format_position", 0);
        dataToInsert.put("score", 0);
        dataToInsert.put("team_tournament_id", team_tournament_id);
        String[] whereArgs = new String[]{String.valueOf(team_tournament_id)};

        db.insert("teams", null, dataToInsert);

        // Close the database
        db.close();
    }

    public static void modifyTeam(Context context, String originalTeamName, String name, String logo, int team_tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // If the name is empty
        if(name.equals("")){

            throw new NullPointerException();
        }
        try {

            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put("name", name);
            dataToInsert.put("logo", logo);

            String[] whereArgs = new String[] {originalTeamName, String.valueOf(team_tournament_id)};

            if(db.updateWithOnConflict("teams", dataToInsert, "name =? AND team_tournament_id=?", whereArgs, SQLiteDatabase.CONFLICT_IGNORE) != 1)
                throw new IllegalArgumentException();

            // If the team name is already in use
        }catch (IllegalArgumentException e) {

            throw new IllegalArgumentException();
        }

        // Close the database
        db.close();
    }

    public static void modifyTeamName(Context context, String originalTeamName, String name,  int team_tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // If the name is empty
        if(name.equals("")){

            throw new NullPointerException();
        }
        try {

            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put("name", name);

            String[] whereArgs = new String[] {originalTeamName, String.valueOf(team_tournament_id)};

            if(db.updateWithOnConflict("teams", dataToInsert, "name =? AND team_tournament_id=?", whereArgs, SQLiteDatabase.CONFLICT_IGNORE) == 0)
                throw new IllegalArgumentException();

            // If the team name is already in use
        }catch (IllegalArgumentException e) {

            throw new IllegalArgumentException();
        }

        // Close the database
        db.close();
    }

    public static void modifyTeamLogo(Context context, String name, String logo, int team_tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Update the team logo
        db.execSQL("UPDATE teams SET logo = '" + logo +"' WHERE name = '" + name
                + "' AND team_tournament_id = " + team_tournament_id);

        // Close the database
        db.close();
    }

    public static void deleteTeam(Context context, String name, int team_tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the team
        db.execSQL("DELETE FROM teams WHERE name = '" + name + "' AND team_tournament_id = " + team_tournament_id);

        // Close the database
        db.close();
    }

    public static int getNumTeamsForTournament(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int numTeams = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_tournament_id = " + tournament_id, null);

            c.moveToFirst();
            {
                numTeams = c.getCount();
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the number of teams
        return numTeams;
    }

    public static ArrayList<String> getTournaments(Context context) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<String> tournaments = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments", null);

            while (c.moveToNext())
            {
                String name = c.getString(c.getColumnIndex("name"));

                try
                {
                    tournaments.add(name);
                }
                catch (Exception e) {
                    throw new NullPointerException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of tournaments
        return tournaments;
    }

    public static String getTeamName(Context context, int team_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        String teamName = "";

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_id = " + team_id, null);

            c.moveToFirst();

            try {
                teamName = c.getString(c.getColumnIndex("name"));
            }
            catch (Exception e) {

                throw new NullPointerException();
            }
        } catch (Exception e) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the logo
        return teamName;
    }

    public static String getTeamLogo(Context context, int team_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        String teamLogo = "";

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_id = " + team_id, null);

            c.moveToFirst();

            try {
                teamLogo = c.getString(c.getColumnIndex("logo"));
            }
            catch (Exception e) {

                throw new NullPointerException();
            }
        } catch (Exception e) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the logo
        return teamLogo;
    }

    public static ArrayList<String> getTeamNames(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<String> teamNames = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_tournament_id = " + tournament_id, null);

            while (c.moveToNext())
            {
                String name = c.getString(c.getColumnIndex("name"));

                try
                {
                    teamNames.add(name);
                }
                catch (Exception e) {

                    throw new NullPointerException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of team names
        return teamNames;
    }

    public static ArrayList<String> getTeamLogos(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<String> teamLogos = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_tournament_id = " + tournament_id, null);

            while (c.moveToNext())
            {
                String logo = c.getString(c.getColumnIndex("logo"));

                try
                {
                    teamLogos.add(logo);
                }
                catch (Exception e) {
                    throw new NullPointerException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of team logos
        return teamLogos;
    }

    public static String getTeamLogo(Context context, String teamName, int team_tournament_id){

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        String logo = teamName;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE name = '" + teamName +"' AND team_tournament_id = " + team_tournament_id, null);
            c.moveToFirst();

            try {
                logo = c.getString(c.getColumnIndex("logo"));
            }
            catch (Exception e) {

                throw new NullPointerException();
            }
        } catch (Exception e) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the logo
        return logo;
    }

    public static ArrayList<String> getTournamentNames(Context context) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<String> tournamentNames = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments", null);

            while (c.moveToNext())
            {
                String name = c.getString(c.getColumnIndex("name"));

                try
                {
                    tournamentNames.add(name);
                }
                catch (Exception e) {

                    throw new NullPointerException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the name of the tournaments
        return tournamentNames;
    }

    public static int getTournamentStatus(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int status = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments WHERE tournament_id = " + tournament_id, null);
            c.moveToFirst();

            try {
                status = c.getInt(c.getColumnIndex("status"));
            }
            catch (Exception e) {

                throw new NullPointerException();
            }
        } catch (Exception e) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the logo
        return status;
    }

    public static ArrayList<String> getTournamentStatuses(Context context) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<String> tournamentStatus = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM tournaments", null);

            while (c.moveToNext())
            {
                String name = c.getString(c.getColumnIndex("status"));

                try
                {
                    tournamentStatus.add(name);
                }
                catch (Exception e) {
                    throw new NullPointerException();
                }

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return tournamentStatus;
    }

    public static void setUpFormat(Context context, int tournament_id, int totalCircuits, int size) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("INSERT INTO formats (currentRound, totalCircuits, size, format_tournament_id) VALUES "
                + "(0, " + totalCircuits + ", " + size + ", " + tournament_id + ")");

        // Close the database
        db.close();
    }

    public static void setTeamFormatPosition(Context context, int team_tournament_id, String teamName, int formatPosition ) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("UPDATE teams SET format_position = " + formatPosition + " WHERE "
                + " team_tournament_id = " + team_tournament_id + " AND name = '" + teamName + "'");

        // Close the database
        db.close();
    }

    public static int getFormatId(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
       int format_id = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM formats WHERE format_tournament_id = " + tournament_id, null);

            c.moveToFirst();
                format_id = c.getInt(c.getColumnIndex("format_id"));

        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return format_id;
    }

    public static ArrayList<String> getFormatOrderedTeams(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<String> orderedTeams = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_tournament_id = " + tournament_id + " ORDER BY format_position", null);

                while (c.moveToNext())
                {
                    String name = c.getString(c.getColumnIndex("name"));

                    try
                    {
                        orderedTeams.add(name);
                    }
                    catch (Exception e) {
                        throw new NullPointerException();
                    }

                }
            } catch (Exception e ) {

                // Do nothing
            }

        // Close the database
        db.close();

        // Return the list of the status
        return orderedTeams;
    }

    public static int getCurrentRound(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int currentRound = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM formats WHERE format_tournament_id = " + tournament_id, null);

            while (c.moveToNext())
            {
                currentRound = c.getInt(c.getColumnIndex("currentRound"));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return currentRound;
    }

    public static void insertRound(Context context, int tournament_id, int size, int round_format_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("INSERT INTO rounds (size, round_format_id, round_tournament_id) VALUES "
                + "(" + size + ", " + round_format_id + ", " + tournament_id + ")");

        // Close the database
        db.close();
    }

    public static void insertMatch(Context context, int round_id, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("INSERT INTO matches (updated, match_round_id, match_tournament_id) VALUES "
                + "(0, " + round_id + ", " + tournament_id + ")");

        // Close the database
        db.close();
    }

    public static int getLatestMatchId(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int match_id = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM matches WHERE match_tournament_id = " + tournament_id, null);

            while(c.moveToNext())
            {
                match_id = c.getInt(c.getColumnIndex("match_id"));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return match_id;
    }

    public static int getTeamId(Context context, String teamName, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int team_id = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_tournament_id = " + tournament_id + " AND name = '" + teamName + "'", null);

            c.moveToFirst();
            {
                team_id = c.getInt(c.getColumnIndex("team_id"));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return team_id;
    }

    public static void insertMatchTeamScore(Context context, int team_id, int match_id, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("INSERT INTO match_team_scores (score, match_team_score_team_id, "
                + "match_team_score_match_id, match_team_score_tournament_id) VALUES "
                + "(0, " + team_id + ", " + match_id + ", " + tournament_id + ")");

        // Close the database
        db.close();
    }

    public static void updateMatchTeamScore(Context context, int team_id, int match_id, int score, int isWinner, int formatType) {


        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            // Insert the team
            db.execSQL("UPDATE match_team_scores SET score = " + score + ", isWinner = " + isWinner + ", formatType = " + formatType
                    + " WHERE match_team_score_team_id = " + team_id + " AND match_team_score_match_id = " + match_id);
        }catch(Exception e) {
            throw new IllegalArgumentException();
        }
        // Close the database
        db.close();
    }

    public static void updateMatch(Context context, int match_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("UPDATE matches SET updated = 1 WHERE match_id = " + match_id);

        // Close the database
        db.close();
    }

    public static void setCurrentRound(Context context, int format_id, int currentRound) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the team
        db.execSQL("UPDATE formats SET currentRound = " + currentRound + " WHERE format_id = " + format_id);

        // Close the database
        db.close();
    }

    public static int getTeamNumWins(Context context, String teamName, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        /* Get the information */
        // Get the list of team ids
        int team_id = getTeamId(context, teamName, tournament_id);

        // Initialize the variable that will hold the number of wins for the team to 0
        int teamWins = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT sum(isWinner) FROM match_team_scores WHERE match_team_score_team_id = " + team_id, null);

            c.moveToFirst();
            {
                teamWins = c.getInt(0);

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return teamWins;
    }

    public static int getTeamNumKnockoutWins(Context context, String teamName, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        /* Get the information */
        // Get the list of team ids
        int team_id = getTeamId(context, teamName, tournament_id);

        // Initialize the variable that will hold the number of wins for the team to 0
        int teamWins = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT sum(isWinner) FROM match_team_scores WHERE match_team_score_team_id = " + team_id
                    + " AND formatType = 2", null);

            c.moveToFirst();
            {
                teamWins = c.getInt(0);

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return teamWins;
    }

    public static int getTournamentNumCurrentRound(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int numCurrentRound = -1;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT count(*) FROM rounds WHERE round_tournament_id = " + tournament_id, null);

            c.moveToFirst();
            {
                numCurrentRound = c.getInt(0);
            }
        } catch (Exception e ) {

            numCurrentRound = 0;
        }

        // Close the database
        db.close();

        // Return the list of the status
        return numCurrentRound;
    }

    public static ArrayList<Integer> getMatchesUpdatedValues(Context context, int currentRound, int match_tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<Integer> matchesUpdatedValues = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM matches WHERE match_round_id = " + currentRound
                    + " AND match_tournament_id = " + match_tournament_id, null);

            while (c.moveToNext())
            {
                matchesUpdatedValues.add(c.getInt(c.getColumnIndex("updated")));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return matchesUpdatedValues;
    }

    public static ArrayList<Integer> getRoundMatchIDs(Context context, int round_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<Integer> matchIDs = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM matches WHERE match_round_id = " + round_id, null);

            while (c.moveToNext())
            {
                matchIDs.add(c.getInt(c.getColumnIndex("match_id")));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return matchIDs;
    }

    public static ArrayList<Integer> getMatchTeamIDs(Context context, int match_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<Integer> teamIDs = new ArrayList<>();

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM match_team_scores WHERE match_team_score_match_id = " + match_id, null);

            while (c.moveToNext())
            {

                teamIDs.add(c.getInt(c.getColumnIndex("match_team_score_team_id")));
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the list of the status
        return teamIDs;
    }

    public static int getRoundID(Context context, int roundNum, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<Integer> roundIDs = new ArrayList<>();
        int currentRoundID = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM rounds WHERE round_tournament_id = " + tournament_id, null);

            while (c.moveToNext())
            {
                roundIDs.add(c.getInt(c.getColumnIndex("round_id")));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Get the current round id
        currentRoundID = roundIDs.get(roundNum);

        // Close the database
        db.close();

        // Return the list of the status
        return currentRoundID;
    }

    public static int getCurrentRoundID(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        ArrayList<Integer> roundIDs = new ArrayList<>();
        int currentRoundID = -1;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM rounds WHERE round_tournament_id = " + tournament_id, null);

            while (c.moveToNext())
            {
                roundIDs.add(c.getInt(c.getColumnIndex("round_id")));

            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Get the current round id
        currentRoundID = roundIDs.get(roundIDs.size() - 1);

        // Close the database
        db.close();

        // Return the list of the status
        return currentRoundID;
    }

    public static int getNumMatchTeamScores(Context context) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int numMatchTeamScores = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM match_team_scores", null);

            c.moveToFirst();
            {
                numMatchTeamScores = c.getCount();
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the number of teams
        return numMatchTeamScores;
    }

    public static int getTournamentId(Context context, int match_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int tournament_id = 0;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM matches WHERE match_id = " + match_id, null);

            c.moveToFirst();
            {
                tournament_id = c.getInt(c.getColumnIndex("match_tournament_id"));
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the number of teams
        return tournament_id;
    }

    public static int getMatchUpdated(Context context, int match_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int updated = -1;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM matches WHERE match_id = " + match_id, null);

            c.moveToFirst();
            {
                updated = c.getInt(c.getColumnIndex("updated"));
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the number of teams
        return updated;
    }

    public static int getMatchTeamScore(Context context, int team_id, int match_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int score = -1;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM match_team_scores WHERE "
                    + "match_team_score_team_id = " + team_id + " AND match_team_score_match_id = " + match_id, null);

            c.moveToFirst();
            {
                score = c.getInt(c.getColumnIndex("score"));
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the number of teams
        return score;
    }

    public static void giveTeamWin(Context context, int team_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Give the team +3 to score
        db.execSQL("UPDATE teams SET score = score + 3 WHERE team_id = " + team_id);

        // Close the database
        db.close();
    }

    public static void giveTeamTie(Context context, int team_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Give the team +3 to score
        db.execSQL("UPDATE teams SET score = score + 1 WHERE team_id = " + team_id);

        // Close the database
        db.close();
    }

    public static int getTeamScore(Context context, int team_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int score = -1;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE team_id = " + team_id, null);

            c.moveToFirst();
            {
                score = c.getInt(c.getColumnIndex("score"));
            }
        } catch (Exception e ) {

            // Do nothing
        }

        // Close the database
        db.close();

        // Return the score
        return score;
    }

    public static void removeFormatPositions(Context context, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Set all the format positions to -1
        db.execSQL("UPDATE teams SET format_position = -1 WHERE team_tournament_id = " + tournament_id);

        // Close the database
        db.close();
    }

    public static int getTeamFormatPosition(Context context, String teamName, int tournament_id) {

        // Open the database
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the information
        int formatPosition = -5;

        try {

            @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM teams WHERE name  = '" + teamName
                    + "' AND team_tournament_id = " + tournament_id, null);

            c.moveToFirst();
            {
                formatPosition = c.getInt(c.getColumnIndex("format_position"));
            }
        } catch (Exception e ) {

            throw new IllegalArgumentException();
        }

        // Close the database
        db.close();

        // Return the score
        return formatPosition;
    }
}

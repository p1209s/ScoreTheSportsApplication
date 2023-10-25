package com.example.score.volleyball;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.score.R;

public class Volleyball2 extends AppCompatActivity {

    static final String STATE_SCORETEAMA = "scoreTeamA";
    static final String STATE_SCORETEAMB = "scoreTeamB";
    static final String STATE_SCORESETSWONTEAMA = "setsWonTeamA";
    static final String STATE_SCORESETSWONTEAMB = "setsWonTeamB";
    static final String STATE_ATTACKTEAMA = "attackTeamA";
    static final String STATE_ATTACKTEAMB = "attackTeamB";
    static final String STATE_BLOCKTEAMA = "blockTeamA";
    static final String STATE_BLOCKTEAMB = "blockTeamB";
    static final String STATE_ERRORTEAMA = "errorTeamA";
    static final String STATE_ERRORTEAMB = "errorTeamB";
    static final String STATE_ACETEAMA = "aceTeamA";
    static final String STATE_ACETEAMB = "aceTeamB";

    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int setsWonTeamA = 0;
    int setsWonTeamB = 0;
    int attackTeamA = 0;
    int attackTeamB = 0;
    int blockTeamA = 0;
    int blockTeamB = 0;
    int errorTeamA = 0;
    int errorTeamB = 0;
    int aceTeamA = 0;
    int aceTeamB = 0;

    Chronometer chronometer;
    ImageButton btStart,btStop;
    private boolean running;
    private long pauseoffset;

    private TextView scoreViewTeamA,scoreViewTeamB;
    private TextView scoreViewSetsWonTeamA;
    private TextView scoreViewSetsWonTeamB;
    private TextView scoreViewAttackA,scoreViewAttackB;
    private TextView scoreViewBlockA,scoreViewBlockB;
    private TextView scoreViewErrorA,scoreViewErrorB;
    private TextView scoreViewAceA,scoreViewAceB;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volleyball2);
        setTitle("Volleyball Score");

        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);

        scoreViewTeamA = findViewById(R.id.team_a_score);
        scoreViewTeamB = findViewById(R.id.team_b_score);
        scoreViewSetsWonTeamA = findViewById(R.id.team_a_sets_won);
        scoreViewSetsWonTeamB = findViewById(R.id.team_b_sets_won);
        scoreViewAttackA = findViewById(R.id.attack_teamA);
        scoreViewAttackB = findViewById(R.id.attack_teamB);
        scoreViewBlockA = findViewById(R.id.block_teamA);
        scoreViewBlockB = findViewById(R.id.block_teamB);
        scoreViewErrorA = findViewById(R.id.error_teamA);
        scoreViewErrorB = findViewById(R.id.error_teamB);
        scoreViewAceA = findViewById(R.id.service_teamA);
        scoreViewAceB = findViewById(R.id.service_teamB);

        TextView view1 = findViewById(R.id.team_a);
        TextView view2 = findViewById(R.id.team_b);
        TextView view3 = findViewById(R.id.view);
        TextView view4 = findViewById(R.id.chosen);

        String s = getIntent().getStringExtra("keyn1");
        String s1 = getIntent().getStringExtra("keyn2");
        String s4 = getIntent().getStringExtra("keyn3");
        String s5 = getIntent().getStringExtra("keyn4");
        view1.setText(s);
        view2.setText(s1);
        view3.setText(s4);
        view4.setText(s5);

        button = findViewById(R.id.end_match);
        button.setOnClickListener(v -> {

            @SuppressLint("CutPasteId") TextView o1= findViewById(R.id.attack_teamA);
            @SuppressLint("CutPasteId") TextView o2= findViewById(R.id.attack_teamB);
            @SuppressLint("CutPasteId") TextView o3= findViewById(R.id.block_teamA);
            @SuppressLint("CutPasteId") TextView o4= findViewById(R.id.block_teamB);
            @SuppressLint("CutPasteId") TextView o5= findViewById(R.id.error_teamA);
            @SuppressLint("CutPasteId") TextView o6= findViewById(R.id.error_teamB);
            @SuppressLint("CutPasteId") TextView o7= findViewById(R.id.service_teamA);
            @SuppressLint("CutPasteId") TextView o8= findViewById(R.id.service_teamB);
            @SuppressLint("CutPasteId") TextView o9= findViewById(R.id.team_a_sets_won);
            @SuppressLint("CutPasteId") TextView o10= findViewById(R.id.team_b_sets_won);
            @SuppressLint("CutPasteId") TextView o11= findViewById(R.id.team_a_score);
            @SuppressLint("CutPasteId") TextView o12= findViewById(R.id.team_b_score);
            String s6=view1.getText().toString();
            String s7=view2.getText().toString();
            String s8=o1.getText().toString();
            String s9=o2.getText().toString();
            String s10=o3.getText().toString();
            String s11=o4.getText().toString();
            String s12=o5.getText().toString();
            String s13=o6.getText().toString();
            String s14=o7.getText().toString();
            String s15=o8.getText().toString();
            String s16=o9.getText().toString();
            String s17=o10.getText().toString();
            String s18=o11.getText().toString();
            String s19=o12.getText().toString();

            Intent inten = new Intent(Volleyball2.this, com.example.score.volleyball.Volleyball3.class);
            inten.putExtra("keyn5", s6);
            inten.putExtra("keyn6", s7);
            inten.putExtra("keyn7", s8);
            inten.putExtra("keyn8", s9);
            inten.putExtra("keyn9", s10);
            inten.putExtra("keyn10", s11);
            inten.putExtra("keyn11", s12);
            inten.putExtra("keyn12", s13);
            inten.putExtra("keyn13", s14);
            inten.putExtra("keyn14", s15);
            inten.putExtra("keyn15", s16);
            inten.putExtra("keyn16", s17);
            inten.putExtra("keyn17", s18);
            inten.putExtra("keyn18", s19);
            startActivity(inten);
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current score state
        savedInstanceState.putInt(STATE_SCORETEAMA, scoreTeamA);
        savedInstanceState.putInt(STATE_SCORETEAMB, scoreTeamB);
        savedInstanceState.putInt(STATE_SCORESETSWONTEAMA, setsWonTeamA);
        savedInstanceState.putInt(STATE_SCORESETSWONTEAMB, setsWonTeamB);
        savedInstanceState.putInt(STATE_ATTACKTEAMA, attackTeamA);
        savedInstanceState.putInt(STATE_ATTACKTEAMB, attackTeamB);
        savedInstanceState.putInt(STATE_BLOCKTEAMA, blockTeamA);
        savedInstanceState.putInt(STATE_BLOCKTEAMB, blockTeamB);
        savedInstanceState.putInt(STATE_ERRORTEAMA, errorTeamA);
        savedInstanceState.putInt(STATE_ERRORTEAMB, errorTeamB);
        savedInstanceState.putInt(STATE_ACETEAMA, aceTeamA);
        savedInstanceState.putInt(STATE_ACETEAMB, aceTeamB);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void start_chronometer(View view){
            if (!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffset);
                chronometer.start();
                running = true;
            }
    }
    public void stop_chronometer(View view){
                if(running) {
                    chronometer.stop();
                    pauseoffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
    }
    public void reset_chronometer(View view){
            chronometer.setBase(SystemClock.elapsedRealtime());

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        scoreTeamA = savedInstanceState.getInt(STATE_SCORETEAMA);
        scoreTeamB = savedInstanceState.getInt(STATE_SCORETEAMB);
        setsWonTeamA = savedInstanceState.getInt(STATE_SCORESETSWONTEAMA);
        setsWonTeamB = savedInstanceState.getInt(STATE_SCORESETSWONTEAMB);
        attackTeamA = savedInstanceState.getInt(STATE_ATTACKTEAMA);
        attackTeamB = savedInstanceState.getInt(STATE_ATTACKTEAMB);
        blockTeamA = savedInstanceState.getInt(STATE_BLOCKTEAMA);
        blockTeamB = savedInstanceState.getInt(STATE_BLOCKTEAMB);
        errorTeamA = savedInstanceState.getInt(STATE_ERRORTEAMA);
        errorTeamB = savedInstanceState.getInt(STATE_ERRORTEAMB);
        aceTeamA = savedInstanceState.getInt(STATE_ACETEAMA);
        aceTeamB = savedInstanceState.getInt(STATE_ACETEAMB);

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displaySetsWonForTeamA(setsWonTeamA);
        displaySetsWonForTeamB(setsWonTeamB);
        displayAttackTeamA(attackTeamA);
        displayAttackTeamB(attackTeamB);
        displayBlockTeamA(blockTeamA);
        displayBlockTeamB(blockTeamB);
        displayErrorTeamA(errorTeamA);
        displayErrorTeamB(errorTeamB);
        displayAceTeamA(aceTeamA);
        displayAceTeamB(aceTeamB);
    }

    public void addOneToSetsWonForTeamA(View v) {
        setsWonTeamA++;
        displaySetsWonForTeamA(setsWonTeamA);
    }
    public void minusOneToSetsWonForTeamA(View v) {
        setsWonTeamA--;
        if(setsWonTeamA<0){
            setsWonTeamA = 0;
        }
        displaySetsWonForTeamA(setsWonTeamA);
    }
    public void addOneToSetsWonForTeamB(View v) {
        setsWonTeamB = setsWonTeamB + 1;
        displaySetsWonForTeamB(setsWonTeamB);
    }
    public void minusOneToSetsWonForTeamB(View v) {
        setsWonTeamB--;
        if(setsWonTeamB<0){
            setsWonTeamB = 0;
        }
        displaySetsWonForTeamB(setsWonTeamB);
    }
    public void addOneToAttackForTeamA(View v) {
        attackTeamA = attackTeamA + 1;
        displayAttackTeamA(attackTeamA);
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }
    public void minusOneToAttackForTeamA(View v) {
        attackTeamA--;
        if(attackTeamA<0){
            attackTeamA = 0;
        }
        displayAttackTeamA(attackTeamA);
        scoreTeamA--;
        if(scoreTeamA<0){
            scoreTeamA = 0;
        }
        displayForTeamA(scoreTeamA);
    }
    public void addOneToAttackForTeamB(View v) {
        attackTeamB = attackTeamB + 1;
        displayAttackTeamB(attackTeamB);
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }
    public void minusOneToAttackForTeamB(View v) {
        attackTeamB--;
        if(attackTeamB<0){
            attackTeamB = 0;
        }
        displayAttackTeamB(attackTeamB);
        scoreTeamB--;
        if(scoreTeamB<0){
            scoreTeamB = 0;
        }
        displayForTeamB(scoreTeamB);
    }
    public void addOneToBlockForTeamA(View v) {
        blockTeamA = blockTeamA + 1;
        displayBlockTeamA(blockTeamA);
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }
    public void minusOneToBlockForTeamA(View v) {
        blockTeamA--;
        if(blockTeamA<0){
            blockTeamA = 0;
        }
        displayBlockTeamA(blockTeamA);
        scoreTeamA--;
        if(scoreTeamA<0){
            scoreTeamA = 0;
        }
        displayForTeamA(scoreTeamA);
    }
    public void addOneToBlockForTeamB(View v) {
        blockTeamB = blockTeamB + 1;
        displayBlockTeamB(blockTeamB);
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }
    public void minusOneToBlockForTeamB(View v) {
        blockTeamB--;
        if(blockTeamB<0){
            blockTeamB = 0;
        }
        displayBlockTeamB(blockTeamB);
        scoreTeamB--;
        if(scoreTeamB<0){
            scoreTeamB = 0;
        }
        displayForTeamB(scoreTeamB);
    }
    public void addOneToErrorForTeamA(View v) {
        errorTeamA = errorTeamA + 1;
        displayErrorTeamA(errorTeamA);
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }
    public void minusOneToErrorForTeamA(View v) {
        errorTeamA--;
        if(errorTeamA<0){
            errorTeamA = 0;
        }
        displayErrorTeamA(errorTeamA);
        scoreTeamA--;
        if(scoreTeamA<0){
            scoreTeamA = 0;
        }
        displayForTeamA(scoreTeamA);
    }
    public void addOneToErrorForTeamB(View v) {
        errorTeamB = errorTeamB + 1;
        displayErrorTeamB(errorTeamB);
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }
    public void minusOneToErrorForTeamB(View v) {
        errorTeamB--;
        if(errorTeamB<0){
            errorTeamB = 0;
        }
        displayErrorTeamB(errorTeamB);
        scoreTeamB--;
        if(scoreTeamB<0){
            scoreTeamB = 0;
        }
        displayForTeamB(scoreTeamB);
    }
    public void addOneToServiceForTeamA(View v) {
        aceTeamA = aceTeamA + 1;
        displayAceTeamA(aceTeamA);
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }
    public void minusOneToServiceForTeamA(View v) {
        aceTeamA--;
        aceTeamA--;
        if(aceTeamA<0){
            aceTeamA = 0;
        }
        displayAceTeamA(aceTeamA);
        scoreTeamA--;
        if(scoreTeamA<0){
            scoreTeamA = 0;
        }
        displayForTeamA(scoreTeamA);
    }
    public void addOneToServiceForTeamB(View v) {
        aceTeamB = aceTeamB + 1;
        displayAceTeamB(aceTeamB);
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }
    public void minusOneToServiceForTeamB(View v) {
        aceTeamB--;
        if(aceTeamB<=0){
            aceTeamB = 0;
        }
        displayAceTeamB(aceTeamB);
        scoreTeamB--;
        if(scoreTeamB<0){
            scoreTeamB = 0;
        }
        displayForTeamB(scoreTeamB);
    }


    /**
     * Displays the main score for TeamA.
     */
    public void displayForTeamA(int score) {
        scoreViewTeamA.setText(String.valueOf(score));
    }
    /**
     * Displays the main score for TeamB.
     */
    public void displayForTeamB(int score) {
        scoreViewTeamB.setText(String.valueOf(score));
    }
    /**
     * Displays sets won for Team A.
     */
    public void displaySetsWonForTeamA(int num) {
        scoreViewSetsWonTeamA.setText(String.valueOf(num));
    }
    /**
     * Displays sets won for Team B.
     */
    public void displaySetsWonForTeamB(int num) {
        scoreViewSetsWonTeamB.setText(String.valueOf(num));
    }
    /**
     * Displays attack for Team A .
     */
    public void displayAttackTeamA(int num) {
        scoreViewAttackA.setText(String.valueOf(num));
    }
    /**
     * Displays attack for Team B.
     */
    public void displayAttackTeamB(int num) {
        scoreViewAttackB.setText(String.valueOf(num));
    }
    /**
     * Displays block for Team A .
     */
    public void displayBlockTeamA(int num) {
        scoreViewBlockA.setText(String.valueOf(num));
    }
    /**
     * Displays block for Team B.
     */
    public void displayBlockTeamB(int num) {
        scoreViewBlockB.setText(String.valueOf(num));
    }
    /**
     * Displays Error for Team A .
     */
    public void displayErrorTeamA(int num) {
        scoreViewErrorA.setText(String.valueOf(num));
    }
    /**
     * Displays Error for Team B.
     */
    public void displayErrorTeamB(int num) {
        scoreViewErrorB.setText(String.valueOf(num));
    }
    /**
     * Displays ace for Team A .
     */
    public void displayAceTeamA(int num) {
        scoreViewAceA.setText(String.valueOf(num));
    }
    /**
     * Displays ace for Team B.
     */
    public void displayAceTeamB(int num) {
        scoreViewAceB.setText(String.valueOf(num));
    }

    public void reset_score(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    public void reset_all(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        setsWonTeamA = 0;
        setsWonTeamB = 0;
        attackTeamA = 0;
        attackTeamB = 0;
        blockTeamA = 0;
        blockTeamB = 0;
        errorTeamA = 0;
        errorTeamB = 0;
        aceTeamA = 0;
        aceTeamB = 0;

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displaySetsWonForTeamA(setsWonTeamA);
        displaySetsWonForTeamB(setsWonTeamB);
        displayAttackTeamA(attackTeamA);
        displayAttackTeamB(attackTeamB);
        displayBlockTeamA(blockTeamA);
        displayBlockTeamB(blockTeamB);
        displayErrorTeamA(errorTeamA);
        displayErrorTeamB(errorTeamB);
        displayAceTeamA(aceTeamA);
        displayAceTeamB(aceTeamB);
    }
    private long pressedTime;
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(getApplicationContext(), com.example.score.HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to end match", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
package com.example.score.kabaddi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;

import java.util.Locale;

public class Kabaddi2 extends AppCompatActivity {
    static final String STATE_SCORETEAMA = "scoreTeamA";
    static final String STATE_SCORETEAMB = "scoreTeamB";
    static final String STATE_PLAYERTEAMA = "playerTeamA";
    static final String STATE_PLAYERTEAMB = "playerTeamB";
    static final String STATE_RAIDTEAMA = "raidTeamA";
    static final String STATE_RAIDTEAMB = "raidTeamB";
    static final String STATE_TACKLETEAMA = "tackleTeamA";
    static final String STATE_TACKLETEAMB = "tackleTeamB";
    static final String STATE_EXTRATEAMA = "extraTeamA";
    static final String STATE_EXTRATEAMB = "extraTeamB";
    static final String STATE_ALLOUTTEAMA = "allOutTeamA";
    static final String STATE_ALLOUTTEAMB = "allOutTeamB";

    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int playerTeamA = 0;
    int playerTeamB = 0;
    int raidTeamA = 0;
    int raidTeamB = 0;
    int tackleTeamA = 0;
    int tackleTeamB = 0;
    int extraTeamA = 0;
    int extraTeamB = 0;
    int allOutTeamA = 0;
    int allOutTeamB = 0;

    Chronometer chronometer;
    ImageButton btStart,btStop;
    private boolean running;
    private long pauseoffset;

    private static final long START_TIME_IN_MILLIS =30000;
    private TextView countdown;

    private CountDownTimer countDownTimer;
    private boolean TimeRunning;
    private long TimeLeftInMillis = START_TIME_IN_MILLIS;

    private TextView scoreViewTeamA,scoreViewTeamB;
    private TextView scoreViewPlayerTeamA;
    private TextView scoreViewPlayerTeamB;
    private TextView scoreViewRaidA,scoreViewRaidB;
    private TextView scoreViewTackleA,scoreViewTackleB;
    private TextView scoreViewExtraA,scoreViewExtraB;
    private TextView scoreViewAllOutA,scoreViewAllOutB;

    Button raidA,raidB,minusRaidA,minusRaidB,tackleA,tackleB,minusTackleA,minusTackleB,end;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabaddi2);
        setTitle("Kabaddi Score");

        countdown =findViewById(R.id.countdown);
        ImageButton start = findViewById(R.id.btn_start);
        ImageButton pause = findViewById(R.id.btn_stop);
        ImageButton reset = findViewById(R.id.btn_reset);

        start.setOnClickListener(v -> {

            if (!TimeRunning) {
                startTimer();
                TimeRunning = true;
            }
        });
        pause.setOnClickListener(v -> {

            if(TimeRunning) {
                pauseTimer();
                TimeRunning = false;
            }
        });
        reset.setOnClickListener(v -> resetTimer());
        countDownTimer=null;
        updateCountDownText();

        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);

        scoreViewTeamA = findViewById(R.id.team_a_score);
        scoreViewTeamB = findViewById(R.id.team_b_score);
        scoreViewPlayerTeamA = findViewById(R.id.player_teamA);
        scoreViewPlayerTeamB = findViewById(R.id.player_teamB);
        scoreViewRaidA = findViewById(R.id.raid_teamA);
        scoreViewRaidB = findViewById(R.id.raid_teamB);
        scoreViewTackleA = findViewById(R.id.tackle_teamA);
        scoreViewTackleB = findViewById(R.id.tackle_teamB);
        scoreViewExtraA = findViewById(R.id.extra_teamA);
        scoreViewExtraB = findViewById(R.id.extra_teamB);
        scoreViewAllOutA = findViewById(R.id.all_out_teamA);
        scoreViewAllOutB = findViewById(R.id.all_out_teamB);

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

        raidA = findViewById(R.id.raidA);
        raidA.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, raidA);
            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        raidTeamA += 1;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 1;
                        displayForTeamA(scoreTeamA);
                        playerTeamB -= 1;
                        displayPlayerForTeamB(playerTeamB);
                        return true;
                    case R.id.item2:
                        raidTeamA += 2;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 2;
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item3:
                        raidTeamA += 3;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 3;
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item4:
                        raidTeamA += 4;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 4;
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item5:
                        raidTeamA += 5;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 5;
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item6:
                        raidTeamA += 6;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 6;
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item7:
                        raidTeamA += 7;
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA += 7;
                        displayForTeamA(scoreTeamA);
                        return true;
                    default:
                        return false;
                }
            });

            popup.show();
        });

        minusRaidA = findViewById(R.id.minusRaidA);
        minusRaidA.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, minusRaidA);

            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        raidTeamA -= 1;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 1;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item2:
                        raidTeamA -= 2;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 2;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item3:
                        raidTeamA -= 3;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 3;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item4:
                        raidTeamA -= 4;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 4;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item5:
                        raidTeamA -= 5;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 5;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item6:
                        raidTeamA -= 6;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 6;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item7:
                        raidTeamA -= 7;
                        if(raidTeamA<0){
                            raidTeamA = 0;
                        }
                        displayRaidTeamA(raidTeamA);
                        scoreTeamA -= 7;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    default:
                        return false;
                }
            });

            popup.show();
        });

        raidB = findViewById(R.id.raidB);
        raidB.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, raidB);

            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        raidTeamB += 1;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 1;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item2:
                        raidTeamB += 2;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 2;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item3:
                        raidTeamB += 3;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 3;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item4:
                        raidTeamB += 4;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 4;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item5:
                        raidTeamB += 5;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 5;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item6:
                        raidTeamB += 6;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 6;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item7:
                        raidTeamB += 7;
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB += 7;
                        displayForTeamB(scoreTeamB);
                        return true;
                    default:
                        return false;
                }
            });

            popup.show();
        });

        minusRaidB = findViewById(R.id.minusRaidB);
        minusRaidB.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, minusRaidB);

            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        raidTeamB -= 1;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 1;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item2:
                        raidTeamB -= 2;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 2;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item3:
                        raidTeamB -= 3;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 3;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item4:
                        raidTeamB -= 4;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 4;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item5:
                        raidTeamB -= 5;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 5;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item6:
                        raidTeamB -= 6;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 6;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item7:
                        raidTeamB -= 7;
                        if(raidTeamB<0){
                            raidTeamB = 0;
                        }
                        displayRaidTeamB(raidTeamB);
                        scoreTeamB -= 7;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    default:
                        return false;
                }
            });

            popup.show();
        });

        tackleA = findViewById(R.id.tackleA);
        tackleA.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, tackleA);

            popup.getMenuInflater().inflate(R.menu.menu1, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        tackleTeamA += 1;
                        displayTackleTeamA(tackleTeamA);
                        scoreTeamA += 1;
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item2:
                        tackleTeamA += 2;
                        displayTackleTeamA(tackleTeamA);
                        scoreTeamA += 2;
                        displayForTeamA(scoreTeamA);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });
        minusTackleA = findViewById(R.id.minusTackleA);
        minusTackleA.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, minusTackleA);

            popup.getMenuInflater().inflate(R.menu.menu1, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        tackleTeamA -= 1;
                        if(tackleTeamA<0){
                            tackleTeamA = 0;
                        }
                        displayTackleTeamA(tackleTeamA);
                        scoreTeamA -= 1;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    case R.id.item2:
                        tackleTeamA -= 2;
                        if(tackleTeamA<0){
                            tackleTeamA = 0;
                        }
                        displayTackleTeamA(tackleTeamA);
                        scoreTeamA -= 2;
                        if(scoreTeamA<0){
                            scoreTeamA = 0;
                        }
                        displayForTeamA(scoreTeamA);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

        tackleB = findViewById(R.id.tackleB);
        tackleB.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, tackleB);

            popup.getMenuInflater().inflate(R.menu.menu1, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        tackleTeamB += 1;
                        displayTackleTeamB(tackleTeamB);
                        scoreTeamB += 1;
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item2:
                        tackleTeamB += 2;
                        displayTackleTeamB(tackleTeamB);
                        scoreTeamB += 2;
                        displayForTeamB(scoreTeamB);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });
        minusTackleB = findViewById(R.id.minusTackleB);
        minusTackleB.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(Kabaddi2.this, minusTackleB);

            popup.getMenuInflater().inflate(R.menu.menu1, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.item1:
                        tackleTeamB -= 1;
                        if(tackleTeamB<0){
                            tackleTeamB = 0;
                        }
                        displayTackleTeamB(tackleTeamB);
                        scoreTeamB -= 1;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    case R.id.item2:
                        tackleTeamB -= 2;
                        if(tackleTeamB<0){
                            tackleTeamB = 0;
                        }
                        displayTackleTeamB(tackleTeamB);
                        scoreTeamB -= 2;
                        if(scoreTeamB<0){
                            scoreTeamB = 0;
                        }
                        displayForTeamB(scoreTeamB);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

        end= findViewById(R.id.end_match);
        end.setOnClickListener(view -> {
            Intent in=new Intent(Kabaddi2.this,Kabaddi3.class);

            String s2 = getIntent().getStringExtra("keyn1");
            String s11 = getIntent().getStringExtra("keyn2");

            String sva=scoreViewTeamA.getText().toString();
            String svb=scoreViewTeamB.getText().toString();

            int d1=Integer.parseInt(sva);
            int d2=Integer.parseInt(svb);

            int d3;
            if(d1>d2)
            {
                d3 = d1 - d2;
                in.putExtra("value11", String.valueOf(s2));
            }
            else
            {
                d3 = d2 - d1;
                in.putExtra("value11", String.valueOf(s11));
            }
            in.putExtra("value12", d3);


            String a1=scoreViewRaidA.getText().toString();
            String a2=scoreViewRaidB.getText().toString();

            String a3=scoreViewTackleA.getText().toString();
            String a4=scoreViewTackleB.getText().toString();

            String a5=scoreViewExtraA.getText().toString();
            String a6=scoreViewExtraB.getText().toString();

            String a7=scoreViewAllOutA.getText().toString();
            String a8=scoreViewAllOutB.getText().toString();

            in.putExtra("keyn6", s2);
            in.putExtra("keyn7", s11);

            in.putExtra("value1", sva);
            in.putExtra("value2", svb);

            in.putExtra("value3", a1);
            in.putExtra("value4", a2);

            in.putExtra("value5", a3);
            in.putExtra("value6", a4);

            in.putExtra("value7", a5);
            in.putExtra("value8", a6);

            in.putExtra("value9", a7);
            in.putExtra("value10", a8);

            startActivity(in);
        });

    }
    private void startTimer(){
        countDownTimer = new CountDownTimer(TimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                TimeRunning = false;
            }
        }.start();
    }
    private void pauseTimer(){
        countDownTimer.cancel();
    }
    private void resetTimer(){
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }
    private void updateCountDownText(){
        int minutes = (int)(TimeLeftInMillis / 1000) / 60;
        int seconds = (int)(TimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        countdown.setText(timeLeftFormatted);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current score state
        savedInstanceState.putInt(STATE_SCORETEAMA, scoreTeamA);
        savedInstanceState.putInt(STATE_SCORETEAMB, scoreTeamB);
        savedInstanceState.putInt(STATE_PLAYERTEAMA, playerTeamA);
        savedInstanceState.putInt(STATE_PLAYERTEAMB, playerTeamB);
        savedInstanceState.putInt(STATE_RAIDTEAMA, raidTeamA);
        savedInstanceState.putInt(STATE_RAIDTEAMB, raidTeamB);
        savedInstanceState.putInt(STATE_TACKLETEAMA, tackleTeamA);
        savedInstanceState.putInt(STATE_TACKLETEAMB, tackleTeamB);
        savedInstanceState.putInt(STATE_EXTRATEAMA, extraTeamA);
        savedInstanceState.putInt(STATE_EXTRATEAMB, extraTeamB);
        savedInstanceState.putInt(STATE_ALLOUTTEAMA, allOutTeamA);
        savedInstanceState.putInt(STATE_ALLOUTTEAMB, allOutTeamB);

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
        playerTeamA = savedInstanceState.getInt(STATE_PLAYERTEAMA);
        playerTeamB = savedInstanceState.getInt(STATE_PLAYERTEAMB);
        raidTeamA = savedInstanceState.getInt(STATE_RAIDTEAMA);
        raidTeamB = savedInstanceState.getInt(STATE_RAIDTEAMB);
        tackleTeamA = savedInstanceState.getInt(STATE_TACKLETEAMA);
        tackleTeamB = savedInstanceState.getInt(STATE_TACKLETEAMB);
        extraTeamA = savedInstanceState.getInt(STATE_EXTRATEAMA);
        extraTeamB = savedInstanceState.getInt(STATE_EXTRATEAMB);
        allOutTeamA = savedInstanceState.getInt(STATE_ALLOUTTEAMA);
        allOutTeamB = savedInstanceState.getInt(STATE_ALLOUTTEAMB);

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayPlayerForTeamA(playerTeamA);
        displayPlayerForTeamB(playerTeamB);
        displayRaidTeamA(raidTeamA);
        displayRaidTeamB(raidTeamB);
        displayTackleTeamA(tackleTeamA);
        displayTackleTeamB(tackleTeamB);
        displayExtraTeamA(extraTeamA);
        displayExtraTeamB(extraTeamB);
        displayAllOutTeamA(allOutTeamA);
        displayAllOutTeamB(allOutTeamB);
    }

    public void addOneToPlayerForTeamA(View v) {
        playerTeamA++;
        if(playerTeamA>6){
            playerTeamA = 7;
        }
        displayPlayerForTeamA(playerTeamA);
    }

    public void minusOneToPlayerForTeamA(View v) {
        playerTeamA--;
        if(playerTeamA<0){
            playerTeamA = 0;
        }
        displayPlayerForTeamA(playerTeamA);
    }

    public void addOneToPlayerForTeamB(View v) {
        playerTeamB++;
        if(playerTeamB>6){
            playerTeamB = 7;
        }
        displayPlayerForTeamB(playerTeamB);
    }

    public void minusOneToPlayerForTeamB(View v) {
        playerTeamB--;
        if(playerTeamB<0){
            playerTeamB = 0;
        }
        displayPlayerForTeamB(playerTeamB);
    }

    public void addOneToExtraForTeamA(View v) {
        extraTeamA++;
        displayExtraTeamA(extraTeamA);
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    public void minusOneToExtraForTeamA(View v) {
        extraTeamA--;
        if(extraTeamA<0){
            extraTeamA = 0;
        }
        displayExtraTeamA(extraTeamA);
        scoreTeamA--;
        if(scoreTeamA<0){
            scoreTeamA = 0;
        }
        displayForTeamA(scoreTeamA);
    }

    public void addOneToExtraForTeamB(View v) {
        extraTeamB++;
        displayExtraTeamB(extraTeamB);
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    public void minusOneToExtraForTeamB(View v) {
        extraTeamB--;
        if(extraTeamB<0){
            extraTeamB = 0;
        }
        displayExtraTeamB(extraTeamB);
        scoreTeamB--;
        if(scoreTeamB<0){
            scoreTeamB = 0;
        }
        displayForTeamB(scoreTeamB);
    }

    public void addOneToAllOutForTeamA(View v) {
        allOutTeamA += 2;
        displayAllOutTeamA(allOutTeamA);
        scoreTeamA += 2;
        displayForTeamA(scoreTeamA);
    }

    public void minusOneToAllOutForTeamA(View v) {
        allOutTeamA -= 2;
        if(allOutTeamA<0){
            allOutTeamA = 0;
        }
        displayAllOutTeamA(allOutTeamA);
        scoreTeamA -= 2;
        if(scoreTeamA<0){
            scoreTeamA = 0;
        }
        displayForTeamA(scoreTeamA);
    }

    public void addOneToAllOutForTeamB(View v) {
        allOutTeamB += 2;
        displayAllOutTeamB(allOutTeamB);
        scoreTeamB += 2;
        displayForTeamB(scoreTeamB);
    }

    public void minusOneToAllOutForTeamB(View v) {
        allOutTeamB -= 2;
        if(allOutTeamB<0){
            allOutTeamB = 0;
        }
        displayAllOutTeamB(allOutTeamB);
        scoreTeamB -= 2;
        if(scoreTeamB<0){
            scoreTeamB = 0;
        }
        displayForTeamB(scoreTeamB);
    }


    /**
     * Displays point for team A.
     */
    private void displayForTeamA(int score) {
        scoreViewTeamA.setText(String.valueOf(score));
    }
    /**
     * Displays point for team B.
     */
    private void displayForTeamB(int score) {
        scoreViewTeamB.setText(String.valueOf(score));
    }
    /**
     * Displays player on court point for Team A.
     */
    private void displayPlayerForTeamA(int score) {
        scoreViewPlayerTeamA.setText(String.valueOf(score));
    }
    /**
     * Displays player on court point for Team B.
     */
    private void displayPlayerForTeamB(int score) {
        scoreViewPlayerTeamB.setText(String.valueOf(score));
    }
    /**
     * Displays raid point for Team A.
     */
    private void displayRaidTeamA(int score) {
        scoreViewRaidA.setText(String.valueOf(score));
    }
    /**
     * Displays raid point for Team B.
     */
    private void displayRaidTeamB(int score) {
        scoreViewRaidB.setText(String.valueOf(score));
    }
    /**
     * Displays tackle point for Team A.
     */
    private void displayTackleTeamA(int score) {
        scoreViewTackleA.setText(String.valueOf(score));
    }
    /**
     * Displays tackle point for Team B.
     */
    private void displayTackleTeamB(int score) {
        scoreViewTackleB.setText(String.valueOf(score));
    }
    /**
     * Displays extra point for Team A.
     */
    private void displayExtraTeamA(int score) {
        scoreViewExtraA.setText(String.valueOf(score));
    }
    /**
     * Displays extra point for Team B.
     */
    private void displayExtraTeamB(int score) {
        scoreViewExtraB.setText(String.valueOf(score));
    }
    /**
     * Displays all out point for Team A.
     */
    private void displayAllOutTeamA(int score) {
        scoreViewAllOutA.setText(String.valueOf(score));
    }
    /**
     * Displays all out point for Team B.
     */
    private void displayAllOutTeamB(int score) {
        scoreViewAllOutB.setText(String.valueOf(score));
    }

    public void reset(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        playerTeamA = 0;
        playerTeamB = 0;
        raidTeamA = 0;
        raidTeamB = 0;
        tackleTeamA = 0;
        tackleTeamB = 0;
        extraTeamA = 0;
        extraTeamB = 0;
        allOutTeamA = 0;
        allOutTeamB = 0;

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayPlayerForTeamA(playerTeamA);
        displayPlayerForTeamB(playerTeamB);
        displayRaidTeamA(raidTeamA);
        displayRaidTeamB(raidTeamB);
        displayTackleTeamA(tackleTeamA);
        displayTackleTeamB(tackleTeamB);
        displayExtraTeamA(extraTeamA);
        displayExtraTeamB(extraTeamB);
        displayAllOutTeamA(allOutTeamA);
        displayAllOutTeamB(allOutTeamB);
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

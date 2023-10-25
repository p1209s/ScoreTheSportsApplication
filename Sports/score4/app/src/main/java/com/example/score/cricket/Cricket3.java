package com.example.score.cricket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.score.R;

public class Cricket3 extends AppCompatActivity implements OnClickListener {
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private static final String GET_SCORE_T2 = "getScoreT2";
    private static final String GET_OVER_T2 = "getOverT2";
    private static final String GET_BALLS_T2 = "getBallsT2";
    private static final String GET_WICKET_T2 = "getWicketT2";
    private static final String GET_TEAM1STATE = "getTeam1State";
    private static final String GET_TEAM2STATE = "getTeam2State";
    private ImageButton mZeroRunBtn, mOneRunBtn, mTwoRunBtn, mThreeRunBtn, mFourRunBtn, mSixRunBtn;
    private Button mByesBtn, mLegByesBtn, mNoBallBtn, mWidesBtn, mWicketBtn, mStatsBtn, mUndoBtn;
    private TextView mTotal;
    private TextView mOverOverview;
    private TextView mOverAndBallLeft;
    private final String[] wicketType = {CricketWicketsType.CAUGHT.value, CricketWicketsType.BOWLED.value, CricketWicketsType.LBW.value,
            CricketWicketsType.RUN_OUT.value, CricketWicketsType.STUMPED.value, CricketWicketsType.HIT_WICKET.value};
    private int byes, legByes, wideRuns, noBallRuns, wickets;
    private final String[] byeType = {String.valueOf(CricketRunsAvailable.SINGLE_RUN.value), String.valueOf(CricketRunsAvailable.DOUBLE_RUN.value),
            String.valueOf(CricketRunsAvailable.TRIPLE_RUN.value), String.valueOf(CricketRunsAvailable.FOUR_RUN.value)};
    private final String[] runsOfExtras = {String.valueOf(CricketRunsAvailable.DOT_BALL.value), String.valueOf(CricketRunsAvailable.SINGLE_RUN.value),
            String.valueOf(CricketRunsAvailable.DOUBLE_RUN.value), String.valueOf(CricketRunsAvailable.TRIPLE_RUN.value),
            String.valueOf(CricketRunsAvailable.FOUR_RUN.value)};
    private final String[] runsOfExtras1 = {String.valueOf(CricketRunsAvailable.DOT_BALL.value), String.valueOf(CricketRunsAvailable.SINGLE_RUN.value),
            String.valueOf(CricketRunsAvailable.DOUBLE_RUN.value), String.valueOf(CricketRunsAvailable.TRIPLE_RUN.value),
            String.valueOf(CricketRunsAvailable.FOUR_RUN.value),String.valueOf(CricketRunsAvailable.SIX_RUN.value)};
    private final int extraRun = 1;
    private CricketInnings mInnings;
    private boolean legalBall, lastBallWicket;
    private int MAX_OVERS, MAX_PLAYER, runMadeByT1, totalBallsBowledT1, wicketsT1;
    private String team2Name;
    private SharedPreferences mSharedPreferences;

    public Cricket3() {
    }

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket3);
        setTitle("Cricket Score");


        Intent i = getIntent();
        runMadeByT1 = i.getIntExtra("runsMadeInInning1", 0);
        totalBallsBowledT1 = i.getIntExtra("totalBallsBowledInning1", 0);
        wicketsT1 = i.getIntExtra("wicketsInning1", 0);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        MAX_OVERS = mSharedPreferences.getInt(GET_MAX_OVER, 5);
        MAX_PLAYER = mSharedPreferences.getInt(GET_MAX_PLAYER, 11);
        team2Name = mSharedPreferences.getString(GET_TEAM2_NAME, "Inning 2");

        mInnings = new CricketInnings(team2Name, MAX_OVERS, MAX_PLAYER, true);
        mInnings.setRunsNeededToWin(runMadeByT1);

        mZeroRunBtn = this.findViewById(R.id.zero_run);
        mZeroRunBtn.setOnClickListener(this);
        mOneRunBtn = this.findViewById(R.id.one_run);
        mOneRunBtn.setOnClickListener(this);
        mTwoRunBtn = this.findViewById(R.id.two_run);
        mTwoRunBtn.setOnClickListener(this);
        mThreeRunBtn = this.findViewById(R.id.three_run);
        mThreeRunBtn.setOnClickListener(this);
        mFourRunBtn = this.findViewById(R.id.four_run);
        mFourRunBtn.setOnClickListener(this);
        mSixRunBtn = this.findViewById(R.id.six_run);
        mSixRunBtn.setOnClickListener(this);
        mByesBtn = this.findViewById(R.id.byes);
        mByesBtn.setOnClickListener(this);
        mLegByesBtn = this.findViewById(R.id.leg_byes);
        mLegByesBtn.setOnClickListener(this);
        mNoBallBtn = this.findViewById(R.id.no_ball);
        mNoBallBtn.setOnClickListener(this);
        mWidesBtn = this.findViewById(R.id.wides);
        mWidesBtn.setOnClickListener(this);
        mWicketBtn = this.findViewById(R.id.wicket);
        mWicketBtn.setOnClickListener(this);
        mStatsBtn = this.findViewById(R.id.stats);
        mStatsBtn.setOnClickListener(this);
        mUndoBtn = this.findViewById(R.id.undo);
        mUndoBtn.setOnClickListener(this);
        mUndoBtn.setEnabled(false);
        mStatsBtn.setEnabled(false);

        mTotal = this.findViewById(R.id.score_overview);
        mOverOverview = this.findViewById(R.id.this_over);
        mOverAndBallLeft = this.findViewById(R.id.ball_overview);
        TextView teamName = this.findViewById(R.id.Team2_name);
        teamName.setText("Inning 2 of " + team2Name);

        setTotal();
    }

    public void onClick(View v) {
        if (!mInnings.isInningDone()) {
            if (v.getId() == R.id.zero_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(CricketRunsAvailable.DOT_BALL);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.DOT_BALL));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.one_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(CricketRunsAvailable.SINGLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.ONE));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.two_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(CricketRunsAvailable.DOUBLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.TWO));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.three_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(CricketRunsAvailable.TRIPLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.THREE));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.four_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(CricketRunsAvailable.FOUR_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.FOUR));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.six_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(CricketRunsAvailable.SIX_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.SIX));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.byes) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Bye Runs")
                            .setItems(byeType, (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        byes = 1;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.SINGLE_RUN);
                                        break;
                                    case 1:
                                        byes = 2;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.DOUBLE_RUN);
                                        break;
                                    case 2:
                                        byes = 3;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.TRIPLE_RUN);
                                        break;
                                    case 3:
                                        byes = 4;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.FOUR_RUN);
                                        break;
                                }
                                mInnings.setExtraRuns(byes);
                                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.BYES));
                                legalBall = true;
                                lastBallWicket = false;
                                setTotal();
                                mUndoBtn.setEnabled(true);
                            })
                            .show();
                }
            } else if (v.getId() == R.id.leg_byes) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Leg Bye Runs")
                            .setItems(byeType, (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        legByes = 1;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.SINGLE_RUN);
                                        break;
                                    case 1:
                                        legByes = 2;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.DOUBLE_RUN);
                                        break;
                                    case 2:
                                        legByes = 3;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.TRIPLE_RUN);
                                        break;
                                    case 3:
                                        legByes = 4;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.FOUR_RUN);
                                        break;
                                }
                                mInnings.setExtraRuns(legByes);
                                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.LEGBYES));
                                legalBall = true;
                                lastBallWicket = false;
                                setTotal();
                                mUndoBtn.setEnabled(true);
                            })
                            .show();
                }
            } else if (v.getId() == R.id.wides) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Wide + Bye Runs")
                            .setItems(runsOfExtras, (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        wideRuns = extraRun;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.DOT_BALL);
                                        break;
                                    case 1:
                                        wideRuns = extraRun + 1;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.SINGLE_RUN);
                                        break;
                                    case 2:
                                        wideRuns = extraRun + 2;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.DOUBLE_RUN);
                                        break;
                                    case 3:
                                        wideRuns = extraRun + 3;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.TRIPLE_RUN);
                                        break;
                                    case 4:
                                        wideRuns = extraRun + 4;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.FOUR_RUN);
                                        break;
                                }
                                mInnings.setExtraRuns(wideRuns);
                                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.WIDEBALL));
                                legalBall = false;
                                lastBallWicket = false;
                                setTotal();
                                mUndoBtn.setEnabled(true);
                            })
                            .show();
                }
            } else if (v.getId() == R.id.no_ball) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("No Ball + Bye Runs")
                            .setItems(runsOfExtras1, (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        noBallRuns = extraRun;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.DOT_BALL);
                                        break;
                                    case 1:
                                        noBallRuns = extraRun + 1;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.SINGLE_RUN);
                                        break;
                                    case 2:
                                        noBallRuns = extraRun + 2;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.DOUBLE_RUN);
                                        break;
                                    case 3:
                                        noBallRuns = extraRun + 3;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.TRIPLE_RUN);
                                        break;
                                    case 4:
                                        noBallRuns = extraRun + 4;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.FOUR_RUN);
                                        break;
                                    case 5:
                                        noBallRuns = extraRun + 6;
                                        mInnings.setExtraRunOfBall(CricketRunsAvailable.SIX_RUN);
                                        break;
                                }
                                mInnings.setExtraRuns(noBallRuns);
                                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.NO_BALL));
                                legalBall = false;
                                lastBallWicket = false;
                                setTotal();
                                mUndoBtn.setEnabled(true);
                            })
                            .show();
                }
            } else if (v.getId() == R.id.wicket) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Type of Wicket")
                            .setItems(wicketType, (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        mInnings.setFallenWicket(CricketWicketsType.CAUGHT);
                                        break;
                                    case 1:
                                        mInnings.setFallenWicket(CricketWicketsType.BOWLED);
                                        break;
                                    case 2:
                                        mInnings.setFallenWicket(CricketWicketsType.LBW);
                                        break;
                                    case 3:
                                        mInnings.setFallenWicket(CricketWicketsType.RUN_OUT);
                                        break;
                                    case 4:
                                        mInnings.setFallenWicket(CricketWicketsType.STUMPED);
                                        break;
                                    case 5:
                                        mInnings.setFallenWicket(CricketWicketsType.HIT_WICKET);
                                        break;
                                }
                                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(CricketTypeOfBalls.WICKET));
                                legalBall = true;
                                lastBallWicket = true;
                                setTotal();
                                mUndoBtn.setEnabled(true);
                            }).show();
                }

            } else if (v.getId() == R.id.stats) {
                String[] mOvers = new String[MAX_OVERS];
                int[] mRunsOfOver = new int[MAX_OVERS];
                int[] mRunsAfterOver = new int[MAX_OVERS];
                for (int i = 0; i < MAX_OVERS; i++) {
                    mOvers[i] = mInnings.getOverOverview(i, true);
                    mRunsOfOver[i] = mInnings.getRunsOfThatOver(i);
                    mRunsAfterOver[i] = mInnings.getRunsAfterOver(i);
                }
                int scoreAtInstant = mInnings.getTotalRunScored();
                int overAtInstant = mInnings.getCurrentOver();
                int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
                int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

                SharedPreferences.Editor sfEditor = mSharedPreferences.edit();
                sfEditor.putInt(GET_SCORE_T2, scoreAtInstant);
                sfEditor.putInt(GET_OVER_T2, overAtInstant);
                sfEditor.putInt(GET_BALLS_T2, ballsPlayedAtInstant);
                sfEditor.putInt(GET_WICKET_T2, wicketFallenAtInstant);
                sfEditor.putBoolean(GET_TEAM2STATE, true);
                sfEditor.putBoolean(GET_TEAM1STATE, false);

                for (int x = 0; x < MAX_OVERS; x++) {
                    sfEditor.putString("overviewT2_" + x, mOvers[x]);
                    sfEditor.putInt("runsOfOverT2_" + x, mRunsOfOver[x]);
                    sfEditor.putInt("runsAfterOverT2_" + x, mRunsAfterOver[x]);
                }
                sfEditor.apply();

                Intent i = new Intent(this, Cricket4.class);
                startActivity(i);
            } else if (v.getId() == R.id.undo) {
                mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                lastBallWicket = false;
                legalBall = false;
                setTotal();
                mUndoBtn.setEnabled(false);

            } else if (v.getId() == R.id.undo) {
                mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                lastBallWicket = false;
                legalBall = false;
                setTotal();
                mUndoBtn.setEnabled(false);

            }
        }
    }

    @SuppressLint({"SetTextI18n", "RtlHardcoded"})
    private void setTotal() {
        mStatsBtn.setEnabled(true);
        mTotal.setText(mInnings.getTotalRunScored() + "/" + mInnings.getCurrentNumOfWickets());
        mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver(), false));
        if (legalBall) {
            mInnings.legalBallsPlayed();
        }
        mOverAndBallLeft.setText(mInnings.getCurrentOver() + "." + mInnings.getNumOfBallsPlayed());
        if (mInnings.isInningDone() && !mInnings.isOverComplete()) {
            inningCompletionPrompt(false);
        } else if (mInnings.isOverComplete()) {
            ScrollView s = new ScrollView(Cricket3.this);
            s.setPadding(100, 0, 100, 0);
            LinearLayout vh = new LinearLayout(Cricket3.this);
            vh.setOrientation(LinearLayout.VERTICAL);
            vh.setGravity(Gravity.CENTER_VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mInnings.getHowManyBallsBowled(mInnings.getCurrentOver()); i++) {
                TextView t = new TextView(this);
                t.setText("Delivery " + (i + 1) + ": " + mInnings.getCertainBallOfOver(mInnings.getCurrentOver(), i));
                t.setTextSize(20);
                vh.addView(t);
            }
            vh.addView(new TextView(this));
            TextView x = new TextView(this);
            x.setText("Over Complete");
            x.setTextSize(20);
            x.setTypeface(null, Typeface.BOLD);
            x.setGravity(Gravity.RIGHT);
            vh.addView(x);
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Next Over", (dialog, which) -> {
                mInnings.setOverDone(true);
                mTotal.setText(mInnings.getTotalRunScored() + "/" + mInnings.getCurrentNumOfWickets());
                mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver() - 1, false));
                mOverAndBallLeft.setText(mInnings.getCurrentOver() + "." + mInnings.getNumOfBallsPlayed());
                legalBall = false;
                mUndoBtn.setEnabled(false);
                if (mInnings.isInningDone()) {
                    inningCompletionPrompt(true);
                }

            }).setNegativeButton("Undo", (dialog, which) -> {
                mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                mInnings.setOverDone(false);
                legalBall = false;
                setTotal();
                mUndoBtn.setEnabled(false);
            }).setCancelable(false).show();
        }
    }

    private void inningCompletionPrompt(boolean wasOverComplete) {
        if (wasOverComplete) {
            new AlertDialog.Builder(this)
                    .setTitle("Inning Complete")
                    .setMessage("Completion of Inning 2")
                    .setPositiveButton("Next", (dialog, which) -> showWinnerPrompt()).setCancelable(false).show();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Inning Complete")
                    .setMessage("Completion of Inning 2")
                    .setPositiveButton("Next", (dialog, which) -> showWinnerPrompt())
                    .setNegativeButton("Undo Last Delivery", (dialog, which) -> {
                        mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                        lastBallWicket = false;
                        legalBall = false;
                        setTotal();
                        mUndoBtn.setEnabled(false);
                    }).setCancelable(false).show();
        }

    }

    private void showWinnerPrompt(){
        new AlertDialog.Builder(this)
                .setTitle("Decision:")
                .setMessage(getWinner())
                .setPositiveButton("Finish", (dialog, which) -> transferData()).setCancelable(false).show();
    }

    private void setBoardVisible(boolean visible) {
        if (visible) {
            mZeroRunBtn.setEnabled(true);
            mOneRunBtn.setEnabled(true);
            mTwoRunBtn.setEnabled(true);
            mThreeRunBtn.setEnabled(true);
            mFourRunBtn.setEnabled(true);
            mSixRunBtn.setEnabled(true);
            mByesBtn.setEnabled(true);
            mLegByesBtn.setEnabled(true);
            mNoBallBtn.setEnabled(true);
            mWidesBtn.setEnabled(true);
            mWicketBtn.setEnabled(true);
            mStatsBtn.setEnabled(true);
            mUndoBtn.setEnabled(true);
        } else {
            mZeroRunBtn.setEnabled(false);
            mOneRunBtn.setEnabled(false);
            mTwoRunBtn.setEnabled(false);
            mThreeRunBtn.setEnabled(false);
            mFourRunBtn.setEnabled(false);
            mSixRunBtn.setEnabled(false);
            mByesBtn.setEnabled(false);
            mLegByesBtn.setEnabled(false);
            mNoBallBtn.setEnabled(false);
            mWidesBtn.setEnabled(false);
            mWicketBtn.setEnabled(false);
            mStatsBtn.setEnabled(false);
            mUndoBtn.setEnabled(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(Cricket3.this).setTitle("Go Back")
                    .setMessage("Inning 1 complete")
                    .setPositiveButton("Ok", (dialog, which) -> {
                    }).show();

        }
        return super.onKeyDown(keyCode, event);
    }

    public void transferData() {
        String[] mOvers = new String[MAX_OVERS];
        int[] mRunsOfOver = new int[MAX_OVERS];
        int[] mRunsAfterOver = new int[MAX_OVERS];
        for (int i = 0; i < MAX_OVERS; i++) {
            mOvers[i] = mInnings.getOverOverview(i, true);
            mRunsOfOver[i] = mInnings.getRunsOfThatOver(i);
            mRunsAfterOver[i] = mInnings.getRunsAfterOver(i);
        }
        int scoreAtInstant = mInnings.getTotalRunScored();
        int overAtInstant = mInnings.getCurrentOver();
        int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
        int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

        SharedPreferences.Editor sfEditor = mSharedPreferences.edit();
        sfEditor.putInt(GET_SCORE_T2, scoreAtInstant);
        sfEditor.putInt(GET_OVER_T2, overAtInstant);
        sfEditor.putInt(GET_BALLS_T2, ballsPlayedAtInstant);
        sfEditor.putInt(GET_WICKET_T2, wicketFallenAtInstant);
        sfEditor.putBoolean(GET_TEAM2STATE, true);
        sfEditor.putBoolean(GET_TEAM1STATE, false);

        for (int x = 0; x < MAX_OVERS; x++) {
            sfEditor.putString("overviewT2_" + x, mOvers[x]);
            sfEditor.putInt("runsOfOverT2_" + x, mRunsOfOver[x]);
            sfEditor.putInt("runsAfterOverT2_" + x, mRunsAfterOver[x]);
        }
        sfEditor.apply();

        Intent i = new Intent(this, Cricket4.class);
        i.putExtra("showNextBtn", true);
        startActivity(i);

    }

    public String getWinner() {
        CricketWinnerFinder findWin = new CricketWinnerFinder(getBaseContext());

        findWin.setTeamARuns(runMadeByT1);
        findWin.setTeamBRuns(mInnings.getTotalRunScored());
        findWin.setTeamABalls(totalBallsBowledT1);
        findWin.setTeamBBalls(mInnings.getTotalRunScored());
        findWin.setTeamAWickets(wicketsT1);
        findWin.setTeamBWickets(mInnings.getCurrentNumOfWickets());

        String msg = findWin.whoWon();
        return msg;
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
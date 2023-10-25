package com.example.score.cricket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.score.R;

public class Cricket1 extends AppCompatActivity {

    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private EditText overs, players, nameTeam1, nameTeam2;
    private Toast mToast;
    boolean noBack;
    Button btn;

    public Cricket1() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket1);
        setTitle("Cricket");

        Intent i = getIntent();
        noBack = i.getBooleanExtra("newStart", false);

        final int MAX_INPUT_LENGTH = 20;

        btn = findViewById(R.id.n);

        btn.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor sfEditor = sharedPreferences.edit();
            sfEditor.putString(GET_TEAM1_NAME, nameTeam1.getText().toString().toUpperCase());
            sfEditor.putString(GET_TEAM2_NAME, nameTeam2.getText().toString().toUpperCase());
            sfEditor.putInt(GET_MAX_OVER, Integer.parseInt(overs.getText().toString()));
            sfEditor.putInt(GET_MAX_PLAYER, Integer.parseInt(players.getText().toString()));
            sfEditor.apply();

            Intent n1 = new Intent(Cricket1.this,Cricket2.class);
            startActivity(n1);
        });

        overs = findViewById(R.id.overs);
        players = findViewById(R.id.num_total_player);
        nameTeam1 = findViewById(R.id.team1_txtF);
        nameTeam1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LENGTH)});
        nameTeam2 = findViewById(R.id.team2_txtF);
        nameTeam2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LENGTH)});


        nameTeam1.setInputType(InputType.TYPE_CLASS_TEXT);
        nameTeam2.setInputType(InputType.TYPE_CLASS_TEXT);
        overs.setInputType(InputType.TYPE_CLASS_NUMBER);
        players.setInputType(InputType.TYPE_CLASS_NUMBER);

        nameTeam1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        nameTeam2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        overs.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        players.setImeOptions(EditorInfo.IME_ACTION_DONE);
        message("");
        nameTeam1.requestFocus();


        nameTeam1.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && nameTeam1.getText().length() == 0) {
                new Handler().postAtTime(() -> {
                    mToast.cancel();
                    nameTeam1.requestFocus();
                    nameTeam2.clearFocus();
                    message("Please write the name of the team that will play Inning 1...");
                    mToast.show();
                }, 0);
            }
        });

        nameTeam2.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && nameTeam2.getText().length() == 0 && nameTeam1.getText().length() != 0) {
                new Handler().postAtTime(() -> {
                    mToast.cancel();
                    nameTeam2.requestFocus();
                    overs.clearFocus();
                    message("Please write the name of the team that will play Inning 2...");
                    mToast.show();
                }, 0);
            }
        });
        overs.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && (overs.getText().length() == 0 || Integer.parseInt(overs.getText().toString()) == 0)
                    && nameTeam2.getText().length() != 0) {

                new Handler().postAtTime(() -> {
                    mToast.cancel();
                    overs.requestFocus();
                    players.clearFocus();
                    if (overs.getText().length() == 0) {
                        message("Please enter the number of overs being played...");
                    } else if (Integer.parseInt(overs.getText().toString()) == 0) {
                        message("You can never play 0 overs");
                    }
                    mToast.show();
                }, 0);
            }

        });


        players.setOnEditorActionListener((v, actionId, event) -> {
            try {
                if (actionId == EditorInfo.IME_ACTION_DONE &&
                        Integer.parseInt(players.getText().toString()) != 0 &&
                        Integer.parseInt(players.getText().toString()) != 1 &&
                        Integer.parseInt(players.getText().toString()) <= 11) {
                    Intent i1 = new Intent(Cricket1.this, Cricket2.class);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor sfEditor = sharedPreferences.edit();

                    sfEditor.putString(GET_TEAM1_NAME, nameTeam1.getText().toString().toUpperCase());
                    sfEditor.putString(GET_TEAM2_NAME, nameTeam2.getText().toString().toUpperCase());
                    sfEditor.putInt(GET_MAX_OVER, Integer.parseInt(overs.getText().toString()));
                    sfEditor.putInt(GET_MAX_PLAYER, Integer.parseInt(players.getText().toString()));
                    sfEditor.apply();

                    startActivity(i1);

                    //onBackPressed();
                    return true;
                } else if (Integer.parseInt(players.getText().toString()) == 0 || Integer.parseInt(players.getText().toString()) == 1) {
                    mToast.cancel();
                    message("You need more people");
                    mToast.show();
                } else if (Integer.parseInt(players.getText().toString()) > 11) {
                    mToast.cancel();
                    message("MAX 11 PLAYERS");
                    mToast.show();
                }
            } catch (NumberFormatException e) {
                mToast.cancel();
                message("Fields cannot be empty");
                mToast.show();
            }
            return false;
        });
    }
    @SuppressLint("ShowToast")
    private void message(String message) {
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }
    public void onBackPressed() {
        // Go back to the Home page
        Intent intent = new Intent(getApplicationContext(), com.example.score.HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}


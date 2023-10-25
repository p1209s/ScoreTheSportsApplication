package com.example.score.football;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;

public class Football3 extends AppCompatActivity {

    TextView first, second,teama,teamb,tvr,tvr1;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football3);
        setTitle("Football Scorecard");

        teama = findViewById(R.id.tva0);
        teamb = findViewById(R.id.tvb0);
        first = findViewById(R.id.tva);
        second = findViewById(R.id.tvb);
        tvr = findViewById(R.id.tvr);
        tvr1 = findViewById(R.id.tvr1);

        TextView view3 = findViewById(R.id.tvrpa);
        TextView view4 = findViewById(R.id.tvrpb);
        TextView view5 = findViewById(R.id.tvtpa);
        TextView view6 = findViewById(R.id.tvtpb);
        TextView view7 = findViewById(R.id.tvepa);
        TextView view8 = findViewById(R.id.tvepb);
        TextView view9 = findViewById(R.id.tvapa);
        TextView view10 = findViewById(R.id.tvapb);

        teama.setText(getIntent().getExtras().getString("keyn6"));
        teamb.setText(getIntent().getExtras().getString("keyn7"));
        first.setText(getIntent().getExtras().getString("value1"));
        second.setText(getIntent().getExtras().getString("value2"));
        tvr.setText(getIntent().getExtras().getString("value11"));

        score = getIntent().getIntExtra("value12", 0);
        tvr1.setText(String.valueOf(score));
        String s8 = getIntent().getStringExtra("value15");
        String s9 = getIntent().getStringExtra("keyn8");
        String s10 = getIntent().getStringExtra("keyn9");
        String s11 = getIntent().getStringExtra("keyn10");
        String s12 = getIntent().getStringExtra("keyn11");
        String s13 = getIntent().getStringExtra("keyn12");
        String s14 = getIntent().getStringExtra("keyn13");
        String s15 = getIntent().getStringExtra("keyn14");

        view3.setText(s8);
        view4.setText(s9);
        view5.setText(s10);
        view6.setText(s11);
        view7.setText(s12);
        view8.setText(s13);
        view9.setText(s14);
        view10.setText(s15);
    }
    private long pressedTime;
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(getApplicationContext(), com.example.score.football.Football1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to end match", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}
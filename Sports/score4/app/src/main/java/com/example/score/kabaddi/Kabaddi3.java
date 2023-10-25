package com.example.score.kabaddi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;

public class Kabaddi3 extends AppCompatActivity {

    TextView first, second,teama,teamb,a1,a2,a3,a4,a5,a6,a7,a8,tvr,tvr1;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabaddi3);
        setTitle("Kabaddi Scorecard");

        teama = findViewById(R.id.tva0);
        teamb = findViewById(R.id.tvb0);
        first = findViewById(R.id.tva);
        second = findViewById(R.id.tvb);
        a1 = findViewById(R.id.tvrpa);
        a2 = findViewById(R.id.tvrpb);
        a3 = findViewById(R.id.tvtpa);
        a4 = findViewById(R.id.tvtpb);
        a5 = findViewById(R.id.tvapa);
        a6 = findViewById(R.id.tvapb);
        a7 = findViewById(R.id.tvepa);
        a8 = findViewById(R.id.tvepb);
        tvr = findViewById(R.id.tvr);
        tvr1 = findViewById(R.id.tvr1);


        teama.setText(getIntent().getExtras().getString("keyn6"));
        teamb.setText(getIntent().getExtras().getString("keyn7"));
        first.setText(getIntent().getExtras().getString("value1"));
        second.setText(getIntent().getExtras().getString("value2"));
        a1.setText(getIntent().getExtras().getString("value3"));
        a2.setText(getIntent().getExtras().getString("value4"));
        a3.setText(getIntent().getExtras().getString("value5"));
        a4.setText(getIntent().getExtras().getString("value6"));
        a5.setText(getIntent().getExtras().getString("value9"));
        a6.setText(getIntent().getExtras().getString("value10"));
        a7.setText(getIntent().getExtras().getString("value7"));
        a8.setText(getIntent().getExtras().getString("value8"));
        tvr.setText(getIntent().getExtras().getString("value11"));

        score = getIntent().getIntExtra("value12", 0);
        tvr1.setText(String.valueOf(score));
    }
    private long pressedTime;
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(getApplicationContext(), com.example.score.kabaddi.Kabaddi1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to end match", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
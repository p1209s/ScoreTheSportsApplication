package com.example.score.volleyball;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.score.R;

public class Volleyball3 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volleyball3);
        setTitle("Volleyball Scorecard");

        TextView view1 = findViewById(R.id.team_a);
        TextView view2 = findViewById(R.id.team_b);
        TextView view3 = findViewById(R.id.attack_teamA);
        TextView view4 = findViewById(R.id.attack_teamB);
        TextView view5 = findViewById(R.id.block_teamA);
        TextView view6 = findViewById(R.id.block_teamB);
        TextView view7 = findViewById(R.id.error_teamA);
        TextView view8 = findViewById(R.id.error_teamB);
        TextView view9 = findViewById(R.id.service_teamA);
        TextView view10 = findViewById(R.id.service_teamB);
        TextView view11 = findViewById(R.id.team_a_sets_won);
        TextView view12 = findViewById(R.id.team_b_sets_won);
        TextView view13 = findViewById(R.id.team_a_score);
        TextView view14 = findViewById(R.id.team_b_score);

        String s6 = getIntent().getStringExtra("keyn5");
        String s7 = getIntent().getStringExtra("keyn6");
        String s8 = getIntent().getStringExtra("keyn7");
        String s9 = getIntent().getStringExtra("keyn8");
        String s10 = getIntent().getStringExtra("keyn9");
        String s11 = getIntent().getStringExtra("keyn10");
        String s12 = getIntent().getStringExtra("keyn11");
        String s13 = getIntent().getStringExtra("keyn12");
        String s14 = getIntent().getStringExtra("keyn13");
        String s15 = getIntent().getStringExtra("keyn14");
        String s16 = getIntent().getStringExtra("keyn15");
        String s17 = getIntent().getStringExtra("keyn16");
        String s18 = getIntent().getStringExtra("keyn17");
        String s19 = getIntent().getStringExtra("keyn18");
        view1.setText(s6);
        view2.setText(s7);
        view3.setText(s8);
        view4.setText(s9);
        view5.setText(s10);
        view6.setText(s11);
        view7.setText(s12);
        view8.setText(s13);
        view9.setText(s14);
        view10.setText(s15);
        view11.setText(s16);
        view12.setText(s17);
        view13.setText(s18);
        view14.setText(s19);

    }
    private long pressedTime;
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(getApplicationContext(), Volleyball1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to end match", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}

package com.example.score;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Fantasy extends AppCompatActivity {
    TextView btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fantasy);
        setTitle("Fantasy");

        btn1 = findViewById(R.id.one);
        btn2 = findViewById(R.id.two);
        btn3 = findViewById(R.id.three);

        btn1.setOnClickListener(v -> {
            Intent on = new Intent(Fantasy.this, FantasyFirst.class);
            startActivity(on);
        });
        btn2.setOnClickListener(v -> {
            Intent on1 = new Intent(Fantasy.this, FantasySecond.class);
            startActivity(on1);
        });
        btn3.setOnClickListener(v -> {
            Intent on2 = new Intent(Fantasy.this, FantasyThird.class);
            startActivity(on2);
        });
    }
    public void onBackPressed() {
        // Go back to the Home page
        Intent intent = new Intent(getApplicationContext(),com.example.score. HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
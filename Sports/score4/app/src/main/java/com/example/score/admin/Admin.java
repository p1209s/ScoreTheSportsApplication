package com.example.score.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.score.Fantasy;
import com.example.score.MainActivity;
import com.example.score.R;

public class Admin extends AppCompatActivity {
    Button adminLogout,adminFantasy;
    private long pressedTime;
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");

        adminLogout = findViewById(R.id.adminLogout);
        adminFantasy = findViewById(R.id.AddFantasy);
        adminLogout.setOnClickListener(v -> {
            Intent i = new Intent(Admin.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}
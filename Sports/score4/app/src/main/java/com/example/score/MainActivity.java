package com.example.score;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText TextEmail;
    EditText TextPassword;
    Button ButtonLogin;
    TextView TextViewRegister;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");

        TextEmail = findViewById(R.id.editText1);
        TextPassword = findViewById(R.id.editText2);
        ButtonLogin = findViewById(R.id.Button);
        TextViewRegister = findViewById(R.id.Textview);
        firebaseAuth = FirebaseAuth.getInstance();
        TextViewRegister.setOnClickListener(view -> {
            Intent registerIntent = new Intent(MainActivity.this,Register.class);
            startActivity(registerIntent);
        });

        ButtonLogin.setOnClickListener(view -> {

            if(TextEmail.getText().toString().equals("admin") &&
                    TextPassword.getText().toString().equals("admin")) {
                Intent i = new Intent(MainActivity.this, com.example.score.admin.Admin.class);
                startActivity(i);
                finish();
            }else{

                String Email = TextEmail.getText().toString().trim();
                String pwd = TextPassword.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(MainActivity.this,"Please Enter Email id",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pwd.length()<6)
                {
                    Toast.makeText(MainActivity.this,"Password Too Short",Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(Email, pwd)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(getApplicationContext(),MainActivity1.class));

                            } else {

                                Toast.makeText(MainActivity.this, "User Not Available", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });


    }

    @Override
    public void onBackPressed() {

        finishAffinity();

    }
}
package com.example.score;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText TextEmail;
    EditText TextPassword;
    EditText TextConfirmPassword;
    Button Register;
    TextView TextViewLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");

        TextEmail = findViewById(R.id.editText1);
        TextPassword = findViewById(R.id.editText2);
        TextConfirmPassword = findViewById(R.id.editText3);
        Register = findViewById(R.id.ButtonR);
        TextViewLogin = findViewById(R.id.TextviewL);

        firebaseAuth = FirebaseAuth.getInstance();

        TextViewLogin.setOnClickListener(v -> {
            Intent LoginIntent = new Intent(Register.this,MainActivity.class);
            startActivity(LoginIntent);
        });
        Register.setOnClickListener(v -> {
            String Email = TextEmail.getText().toString().trim();
            String pwd = TextPassword.getText().toString().trim();
            String Cnf_pwd = TextConfirmPassword.getText().toString().trim();

            if(TextUtils.isEmpty(Email)){
                Toast.makeText(Register.this,"Please Enter Email id",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(pwd)){
                Toast.makeText(Register.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(Cnf_pwd)){
                Toast.makeText(Register.this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
                return;
            }

            if(pwd.length()<6)
            {
                Toast.makeText(Register.this,"Password Too Short",Toast.LENGTH_SHORT).show();
            }


            if(pwd.equals(Cnf_pwd))
            {
                firebaseAuth.createUserWithEmailAndPassword(Email, pwd)
                        .addOnCompleteListener(Register.this, task -> {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                Toast.makeText(Register.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(Register.this, "Please Check Your Password", Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        });
            }

        });
    }
}
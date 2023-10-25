package com.example.score.football;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.score.R;

public class Football1 extends AppCompatActivity{
    EditText n1,n2;
    TextView m1,m2,to,to1;
    RadioButton m3,m4;
    Button b1;
    RadioGroup radioGroup,radioGroup1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football1);
        setTitle("Football");

        n1=findViewById(R.id.e1);
        n2=findViewById(R.id.e2);
        b1=findViewById(R.id.btn);
        m1=findViewById(R.id.t1);
        m2=findViewById(R.id.t2);
        m3=findViewById(R.id.radioButton);
        m4=findViewById(R.id.radioButton2);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup1 = findViewById(R.id.radioGroup1);
        to=findViewById(R.id.t3);
        to1=findViewById(R.id.t4);

        n1.addTextChangedListener(TextWatcher);
        n2.addTextChangedListener(TextWatcher);

        b1.setOnClickListener(v -> {
            int radioId = radioGroup.getCheckedRadioButtonId();
            b1 = findViewById(radioId);
            to.setText(b1.getText());

            int radioId1 = radioGroup1.getCheckedRadioButtonId();
            b1 = findViewById(radioId1);
            to1.setText(b1.getText());

            String s=n1.getText().toString();
            String s1=n2.getText().toString();
            String s2=n1.getText().toString();
            String s3=n2.getText().toString();
            String s4=to.getText().toString();
            String s5=to1.getText().toString();

            Intent inten=new Intent(Football1.this,Football1.class);
            inten.putExtra("keyn1",s2);
            inten.putExtra("keyn2",s3);
            startActivity(inten);

            Intent intent=new Intent(Football1.this,Football2.class);
            intent.putExtra("keyn1",s);
            intent.putExtra("keyn2",s1);
            intent.putExtra("keyn3",s4);
            intent.putExtra("keyn4",s5);
            startActivity(intent);


        });
    }
    private final TextWatcher TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String teamA = n1.getText().toString().trim();
            String teamB = n2.getText().toString().trim();

            b1.setEnabled(!teamA.isEmpty() && !teamB.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str=n1.getText().toString();
            m3.setText(str);
            String str1=n2.getText().toString();
            m4.setText(str1);

        }
    };
    public void onBackPressed() {
        // Go back to the Home page
        Intent intent = new Intent(getApplicationContext(), com.example.score.HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

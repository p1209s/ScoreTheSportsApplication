package com.example.score.football;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;

public class Football2 extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private long pauseoffset;
    int Ainteger = 0;
    int Binteger = 0;
    int Sainteger = 0;
    int Cainteger = 0;
    int Yainteger = 0;
    int Rainteger = 0;
    int Sbinteger = 0;
    int Cbinteger = 0;
    int Ybinteger = 0;
    int Rbinteger = 0;
    int Substitution = 0;
    int Substitution1 = 0;
    TextView tvteam_a,tvteam_b,ScA,ScB;
    Button footend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football2);
        setTitle("Football Score");

        chronometer = findViewById(R.id.chronometer);

        tvteam_a= findViewById(R.id.tvteam_a);
        tvteam_b= findViewById(R.id.tvteam_b);
        ScA= findViewById(R.id.ScA);
        ScB= findViewById(R.id.ScB);
        footend= findViewById(R.id.footend);
        TextView view3 = findViewById(R.id.view);
        TextView view4 = findViewById(R.id.chosen);

        Intent intent = getIntent();
        String str = intent.getStringExtra("keyn1");
        tvteam_a.setText(str);

        Intent intent1 = getIntent();
        String str1 = intent1.getStringExtra("keyn2");
        tvteam_b.setText(str1);

        Intent intent2 = getIntent();
        String str2 = intent2.getStringExtra("keyn3");
        view3.setText(str2);

        Intent intent3 = getIntent();
        String str3 = intent3.getStringExtra("keyn4");
        view4.setText(str3);

        footend.setOnClickListener(view -> {

            @SuppressLint("CutPasteId") TextView o1= findViewById(R.id.ScSa);
            @SuppressLint("CutPasteId") TextView o2= findViewById(R.id.ScSb);
            @SuppressLint("CutPasteId") TextView o3= findViewById(R.id.ScCa);
            @SuppressLint("CutPasteId") TextView o4= findViewById(R.id.ScCb);
            @SuppressLint("CutPasteId") TextView o5= findViewById(R.id.Scya);
            @SuppressLint("CutPasteId") TextView o6= findViewById(R.id.Scyb);
            @SuppressLint("CutPasteId") TextView o7= findViewById(R.id.ScRa);
            @SuppressLint("CutPasteId") TextView o8= findViewById(R.id.ScRb);

            String s8=o1.getText().toString();
            String s9=o2.getText().toString();
            String s10=o3.getText().toString();
            String s11=o4.getText().toString();
            String s12=o5.getText().toString();
            String s13=o6.getText().toString();
            String s14=o7.getText().toString();
            String s15=o8.getText().toString();

            Intent in = new Intent(Football2.this, Football3.class);
            String s = getIntent().getStringExtra("keyn1");
            String s1 = getIntent().getStringExtra("keyn2");

            in.putExtra("value15", s8);
            in.putExtra("keyn8", s9);
            in.putExtra("keyn9", s10);
            in.putExtra("keyn10", s11);
            in.putExtra("keyn11", s12);
            in.putExtra("keyn12", s13);
            in.putExtra("keyn13", s14);
            in.putExtra("keyn14", s15);

            String sva=ScA.getText().toString();
            String svb=ScB.getText().toString();

            int d1=Integer.parseInt(sva);
            int d2=Integer.parseInt(svb);

            int d3;
            if(d1>d2)
            {
                d3 = d1 - d2;
                in.putExtra("value11", String.valueOf(s));
            }
            else
            {
                d3 = d2 - d1;
                in.putExtra("value11", String.valueOf(s1));
            }
            in.putExtra("value12", d3);

            in.putExtra("keyn6",s);
            in.putExtra("keyn7",s1);

            in.putExtra("value1", sva);
            in.putExtra("value2", svb);

            startActivity(in);
        });
    }



    public void start_chronometer(View view){
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffset);
            chronometer.start();
            running = true;
        }
    }
    public void pause_chronometer(View view){
        if(running) {
            chronometer.stop();
            pauseoffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    // Score For A

    public void ScoreteamA(View view)
    {
        Ainteger = Ainteger+1;
        display(Ainteger);
    }
    @SuppressLint("SetTextI18n")
    private void display(int number)
    {
        TextView displayInteger = findViewById(R.id.ScA);
        displayInteger.setText(""+number);
    }
    public void Scoreteama(View view)
    {
        if(Ainteger<0)
        {
            Ainteger=0;
        }
        display1(Ainteger);
        Ainteger--;
    }
    @SuppressLint("SetTextI18n")
    private void display1(int number1)
    {
        TextView display1Integer = findViewById(R.id.ScA);
        display1Integer.setText(""+number1);
    }

    // Score For B

    public void ScoreteamB(View view)
    {
        Binteger = Binteger+1;
        displayB(Binteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayB(int numberB)
    {
        TextView displayInteger = findViewById(R.id.ScB);
        displayInteger.setText(""+numberB);
    }
    public void Scoreteamb(View view)
    {
        if(Binteger<0)
        {
            Binteger=0;
        }
        displayB1(Binteger);
        Binteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayB1(int numberB1)
    {
        TextView display1Integer = findViewById(R.id.ScB);
        display1Integer.setText(""+numberB1);
    }

    // Substitution for A

    public void substitutiona(View view)
    {
        Substitution = Substitution+1;
        displaySub(Substitution);
    }
    @SuppressLint("SetTextI18n")
    private void displaySub(int numberSub)
    {
        TextView displayInteger = findViewById(R.id.ScSub);
        displayInteger.setText(""+numberSub);
    }

    // Substitution for B

    public void substitutionb(View view)
    {
        Substitution1 = Substitution1+1;
        displaySub1(Substitution1);
    }
    @SuppressLint("SetTextI18n")
    private void displaySub1(int numberSub1)
    {
        TextView displayInteger = findViewById(R.id.ScSub1);
        displayInteger.setText(""+numberSub1);
    }

    // Shots of A
    public void Shots(View view)
    {
        Sainteger = Sainteger+1;
        displayS(Sainteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayS(int numberS)
    {
        TextView displayInteger = findViewById(R.id.ScSa);
        displayInteger.setText(""+numberS);
    }
    public void Shots1(View view)
    {
        if(Sainteger<0)
        {
            Sainteger=0;
        }
        displayS1(Sainteger);
        Sainteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayS1(int numberS)
    {
        TextView display1Integer = findViewById(R.id.ScSa);
        display1Integer.setText(""+numberS);
    }

    // Shots of B
    public void Shotsb(View view)
    {
        Sbinteger = Sbinteger+1;
        displaySb(Sbinteger);
    }
    @SuppressLint("SetTextI18n")
    private void displaySb1(int numberSb)
    {
        TextView displayInteger = findViewById(R.id.ScSb);
        displayInteger.setText(""+numberSb);
    }
    public void Shots1b(View view)
    {
        if(Sbinteger<0)
        {
            Sbinteger=0;
        }
        displaySb1(Sbinteger);
        Sbinteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displaySb(int numberSb)
    {
        TextView display1Integer = findViewById(R.id.ScSb);
        display1Integer.setText(""+numberSb);
    }

    // Corner For A

    public void Corner(View view)
    {
        Cainteger = Cainteger+1;
        displayC(Cainteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayC(int numberC)
    {
        TextView displayInteger = findViewById(R.id.ScCa);
        displayInteger.setText(""+numberC);
    }
    public void Corner1(View view)
    {
        if(Cainteger<0)
        {
            Cainteger=0;
        }
        displayC1(Cainteger);
        Cainteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayC1(int numberC)
    {
        TextView display1Integer = findViewById(R.id.ScCa);
        display1Integer.setText(""+numberC);
    }

    // Corner for B

    public void Cornerb(View view)
    {
        Cbinteger = Cbinteger+1;
        displayCb(Cbinteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayCb(int numberCb)
    {
        TextView displayInteger = findViewById(R.id.ScCb);
        displayInteger.setText(""+numberCb);
    }
    public void Corner1b(View view)
    {
        if(Cbinteger<0)
        {
            Cbinteger=0;
        }
        displayC1b(Cbinteger);
        Cbinteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayC1b(int numberCb)
    {
        TextView display1Integer = findViewById(R.id.ScCb);
        display1Integer.setText(""+numberCb);
    }

    // Yellow For A

    public void Yellowa(View view)
    {
        Yainteger = Yainteger+1;
        displayY(Yainteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayY(int numberY)
    {
        TextView displayInteger = findViewById(R.id.Scya);
        displayInteger.setText(""+numberY);
    }
    public void Yellowa1(View view)
    {
        if(Yainteger<0)
        {
            Yainteger=0;
        }
        displayY1(Yainteger);
        Yainteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayY1(int numberY)
    {
        TextView display1Integer = findViewById(R.id.Scya);
        display1Integer.setText(""+numberY);
    }

    // Yellow For B

    public void Yellowb(View view)
    {
        Ybinteger = Ybinteger+1;
        displayYb(Ybinteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayYb(int numberYb)
    {
        TextView displayInteger = findViewById(R.id.Scyb);
        displayInteger.setText(""+numberYb);
    }
    public void Yellowb1(View view)
    {
        if(Ybinteger<0)
        {
            Ybinteger=0;
        }
        displayY1b(Ybinteger);
        Ybinteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayY1b(int numberYb)
    {
        TextView display1Integer = findViewById(R.id.Scyb);
        display1Integer.setText(""+numberYb);
    }

    // Red For A

    public void Reda(View view)
    {
        Rainteger = Rainteger+1;
        displayR(Rainteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayR(int numberR)
    {
        TextView displayInteger = findViewById(R.id.ScRa);
        displayInteger.setText(""+numberR);
    }
    public void Reda1(View view)
    {
        if(Rainteger<0)
        {
            Rainteger=0;
        }
        displayR1(Rainteger);
        Rainteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayR1(int numberR)
    {
        TextView display1Integer = findViewById(R.id.ScRa);
        display1Integer.setText(""+numberR);
    }

    // Red For B

    public void Redb(View view)
    {
        Rbinteger = Rbinteger+1;
        displayRb(Rbinteger);
    }
    @SuppressLint("SetTextI18n")
    private void displayRb(int numberRb)
    {
        TextView displayInteger = findViewById(R.id.ScRb);
        displayInteger.setText(""+numberRb);
    }
    public void Redb1(View view)
    {
        if(Rbinteger<0)
        {
            Rbinteger=0;
        }
        displayR1b(Rbinteger);
        Rbinteger--;
    }
    @SuppressLint("SetTextI18n")
    private void displayR1b(int numberRb)
    {
        TextView display1Integer = findViewById(R.id.ScRb);
        display1Integer.setText(""+numberRb);
    }

    // Reset

    public void Resetall(View view)
    {
        Ainteger=0;
        Binteger=0;
        Sainteger=0;
        Sbinteger=0;
        Cainteger=0;
        Cbinteger=0;
        Yainteger=0;
        Ybinteger=0;
        Rainteger=0;
        Rbinteger=0;
        Substitution=0;
        Substitution1=0;

        display(Ainteger);
        displayB(Binteger);
        displaySub(Substitution);
        displaySub1(Substitution1);
        displayS(Sainteger);
        displaySb(Sbinteger);
        displayC(Cainteger);
        displayCb(Cbinteger);
        displayY(Yainteger);
        displayYb(Ybinteger);
        displayR(Rainteger);
        displayRb(Rbinteger);

        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseoffset =0;
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
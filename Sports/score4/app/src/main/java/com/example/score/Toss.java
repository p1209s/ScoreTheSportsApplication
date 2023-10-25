package com.example.score;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class Toss extends AppCompatActivity {

    Button F;
    ImageView C;

    Random r;
    int side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss);
        setTitle("Toss");

        F = findViewById(R.id.flip);
        C = findViewById(R.id.coin);

        r = new Random();

        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                side =r.nextInt(2);

                if(side == 0)
                {
                    C.setImageResource(R.drawable.heads);
                    Toast.makeText(Toss.this,"HEADS",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    C.setImageResource(R.drawable.tails);
                    Toast.makeText(Toss.this,"TAILS",Toast.LENGTH_SHORT).show();
                }

                RotateAnimation rotate = new RotateAnimation(0,360,
                        RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);

                rotate.setDuration(1000);
                C.startAnimation(rotate);
            }
        });
    }
}
package com.example.justmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class mainGameActivity extends AppCompatActivity {

    public int seconds = 10, score; //THIS WILL RUN FOR 10 SECONDS
    private boolean stopTimer = false, isResultCorrect;
    private TextView timeCountdown, questions, scoreTxt;
    private ImageButton yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        timeCountdown = findViewById(R.id.timeCountdown);
        questions = findViewById(R.id.questions);
        scoreTxt = findViewById(R.id.scoreBox);


        timer();
        generateQuestion();

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(true);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(false);
            }
        });
    }

    private void timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                seconds--;
                if (seconds < 0) {
                    String s = String.valueOf(score);
                    // DO SOMETHING WHEN TIMES UP
                    stopTimer = true;
                    Intent i = new Intent(getApplicationContext(), finalScore.class);
                    i.putExtra("scores", s);
                    startActivity(i);
                }
                if (stopTimer == false) {
                    timeCountdown.setText("TIME : " + seconds);
                    handler.postDelayed(this, 1000);
                }
            }

        });
    }

    public void generateQuestion() {
        isResultCorrect = true;
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        int result = a + b;
        float f = random.nextFloat();
        if (f > 0.5f) {
            result = random.nextInt(100);
            isResultCorrect = false;
        }
        questions.setText(a + "+" + b + "=" + result);
    }

    public void verifyAnswer(boolean answer) {
        if (answer == isResultCorrect) {
            score += 5;
            scoreTxt.setText("SCORE: " + score);
        } else {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(400);
        }
        generateQuestion();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}

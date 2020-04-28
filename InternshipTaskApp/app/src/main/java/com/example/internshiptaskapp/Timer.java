package com.example.internshiptaskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class Timer extends AppCompatActivity {
    TextView _tv;
    private Timer timer;
    TimerTask timerTask;
    Button ActThree;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ActThree = findViewById(R.id.s);
        ActThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScoreBoard.class));

            }
        });

         _tv =  findViewById( R.id.timerText );
            new CountDownTimer(600000, 1000) {

                public void onTick(long millisUntilFinished) {
                    _tv.setText("Seconds remaining: " + millisUntilFinished / 1000);
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    //  mTextField.setText("done!");
                }

            }.start();

             /*timerTask = new TimerTask() {

                @Override
                public void run() {
                    //  final Random random = new Random();
                    //   int i = random.nextInt(2 - 0 + 1) + 0;
                    // random_note.setImageResource(image[i]);
                    new CountDownTimer(600000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            _tv.setText("Seconds remaining: " + millisUntilFinished / 1000);
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            //  mTextField.setText("done!");
                        }

                    }.start();
                }
            };
*/


    }


    /*public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
       // timer.scheduleAtFixedRate(timerTask, 0, 0);
    }*/




}

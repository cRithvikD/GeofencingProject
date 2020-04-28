package com.example.internshiptaskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ScoreBoard extends AppCompatActivity {
    TextView SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        SCORE = findViewById(R.id.scr);
        SCORE.setText("Score is " +GeofenceHelper.score);


    }
}

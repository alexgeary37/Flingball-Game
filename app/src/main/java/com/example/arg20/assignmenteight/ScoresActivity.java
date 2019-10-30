package com.example.arg20.assignmenteight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class ScoresActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private Integer newScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        settings = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        newScore = getIntent().getIntExtra("SCORE" , 0);

        if (settings.contains("Fifth")) {

            if (newScore > settings.getInt("Fifth", 0)) {

                ArrayList<Integer> scores = new ArrayList<>();
                scores.add(settings.getInt("First", 0));
                scores.add(settings.getInt("Second", 0));
                scores.add(settings.getInt("Third", 0));
                scores.add(settings.getInt("Fourth", 0));
                scores.add(settings.getInt("Fifth", 0));

                scores.set(4, newScore);
                Collections.sort(scores);

                editor.putInt("First", scores.get(4));
                editor.putInt("Second", scores.get(3));
                editor.putInt("Third", scores.get(2));
                editor.putInt("Fourth", scores.get(1));
                editor.putInt("Fifth", scores.get(0));
            }
        } else {

            editor.putInt("First", 0);
            editor.putInt("Second", 0);
            editor.putInt("Third", 0);
            editor.putInt("Fourth", 0);
            editor.putInt("Fifth", 0);
        }

        editor.commit();

        // hide status bar and make full screen immersive sticky mode
        int uiChanges = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(uiChanges);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getIntent().getStringExtra("ACTIVITY").compareTo("mainActivity") == 0) {
            TextView scoreLabel = findViewById(R.id.scoreLabel);
            String yourScore = "Your Score: " + newScore.toString();
            scoreLabel.setText(yourScore);
        }

        TextView pos1 = findViewById(R.id.highScoreLabel);
        TextView pos2 = findViewById(R.id.highScoreLabel2);
        TextView pos3 = findViewById(R.id.highScoreLabel3);
        TextView pos4 = findViewById(R.id.highScoreLabel4);
        TextView pos5 = findViewById(R.id.highScoreLabel5);

        pos1.setText(Integer.toString(settings.getInt("First", 0)));
        pos2.setText(Integer.toString(settings.getInt("Second", 0)));
        pos3.setText(Integer.toString(settings.getInt("Third", 0)));
        pos4.setText(Integer.toString(settings.getInt("Fourth", 0)));
        pos5.setText(Integer.toString(settings.getInt("Fifth", 0)));
    }

    public void onclickTryAgain(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onclickButtonMain(View v) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
package com.example.arg20.assignmenteight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // hide status bar and make full screen immersive sticky mode
        int uiChanges = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(uiChanges);
    }

    // handles start button click event - goes to game play
    public void onclickButtonStart(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    // handles scores button click event
    public void onclickButtonScores(View v) {
        Intent intent = new Intent(this, ScoresActivity.class);
        intent.putExtra("ACTIVITY", "homeActivity");
        startActivity(intent);
    }
}

package com.example.arg20.assignmenteight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private String time;
    private TextView timerTv, scoreTv;
    private View graphicsView;
    private Intent intent;
    public Integer score;


    public class GraphicsView extends View {
        private GestureDetector gestureDetector;
        private Ball ball;
        private ArrayList<Obstacle> obstacles;
        private Target target;
        private Paint[] ballPaints, obstaclePaints, targetPaints;
        private boolean hasTwoObstacles, hasThreeObstacles;


        public GraphicsView(Context context) {
            super(context);
            gestureDetector = new GestureDetector(context, new MyGestureListener());

            // create colours and set colours for objects
            ballPaints = new Paint[] { new Paint(), new Paint(), new Paint() };
            ballPaints[0].setColor(getColor(R.color.colorBall));
            ballPaints[1].setColor(getColor(R.color.colorPrimaryLight));
            ballPaints[2].setColor(getColor(R.color.colorWhite));

            obstaclePaints = new Paint[] { new Paint(), new Paint() };
            obstaclePaints[0].setColor(getColor(R.color.colorObstacle));
            obstaclePaints[1].setColor(getColor(R.color.colorPrimary));

            targetPaints = new Paint[] { new Paint(), new Paint() };
            targetPaints[0].setColor(getColor(R.color.colorTarget));
            targetPaints[1].setColor(getColor(R.color.colorWhite));

            obstacles = new ArrayList<>();
            score = 0;
            hasTwoObstacles = false;
            hasThreeObstacles = false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            String playerScore = "Score: " + score.toString();
            scoreTv.setText(playerScore);
            timerTv.setText(time);

            // create object instances
            if (ball == null) {
                Sprite.xMax = getWidth();
                Sprite.yMax = getHeight();
                obstacles.add(new Obstacle(getWidth() / 4, getHeight() / 3, 50));
                target = new Target(getWidth() / 2, getHeight() / 8, 50);
                ball = new Ball(getWidth() / 2, (getHeight() / 8) * 7, 50, target, getHeight());
            }

            // draw all sprite objects
            ball.draw(canvas, ballPaints);
            target.draw(canvas, targetPaints);
            for (int i = 0; i < obstacles.size(); i++) {
                obstacles.get(i).draw(canvas, obstaclePaints);
            }

            // remove target and increase score by 50
            if (ball.collision(target)) {
                score += 50;

                // add new obstacles at various times
                if (score == 300 && !hasTwoObstacles) {
                    hasTwoObstacles = true;
                    obstacles.add(new Obstacle((getWidth() / 4) * 3, (getHeight() / 3) * 2, 70));
                } else if (score == 600 && !hasThreeObstacles) {
                    hasThreeObstacles = true;
                    obstacles.add(new Obstacle(getWidth() / 4, getHeight() / 3, 60));
                }

                target.remove();
                ball.hitTarget();
            }

            // handle ball collision with obstacles
            for (int i = 0; i < obstacles.size(); i++) {
                if (ball.collision(obstacles.get(i))) {
                    ball.drawExplosion(canvas, ballPaints);
                    ball.reset();
                    score -= 50;
                    break;
                }
            }

            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            // make sure ball can only be flung once
            if (!ball.hasPlayed() && gestureDetector.onTouchEvent(event)) {
                return true;
            }

            return super.onTouchEvent(event);
        }

        public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent event, MotionEvent event2, float v, float v2) {
                
                // stop player from dragging ball to target
                if ((event.getY() - event2.getY()) < ball.yMax / 2.0) {
                   ball.setHasPlayed();
                   ball.setXVelocity(v / 200);
                   ball.setYVelocity(v2 / 200);
                }

                return true;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        countDownTimer = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (time != null) {
            long timeRemaining = Integer.parseInt(time);
            timeRemaining = timeRemaining * 1000;
            startTimer(timeRemaining);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide status bar and make full screen immersive sticky mode
        int uiChanges = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(uiChanges);

        timerTv = findViewById(R.id.timerTv);
        scoreTv = findViewById(R.id.scoreTv);
        intent = new Intent(getApplicationContext(), ScoresActivity.class);

        // initialise timer to count down from 60 to 0
        startTimer(60000);

        // display everything
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutMain);
        graphicsView = new GraphicsView(this);
        constraintLayout.addView(graphicsView);
    }

    // method initializes and starts timer
    private void startTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Integer t = (int) millisUntilFinished;
                t /= 1000;
                time = t.toString();
            }

            @Override
            public void onFinish() {
                // make popup visible and the game screen invisible
                View popup = findViewById(R.id.popup);
                popup.setVisibility(View.VISIBLE);

                graphicsView.setVisibility(View.INVISIBLE);
                timerTv.setVisibility(View.INVISIBLE);
                scoreTv.setVisibility(View.INVISIBLE);

                // get score and display it as well as pass it to intent
                TextView gameScoreTv = findViewById(R.id.gameScore);

                String punctuation = (score > 0)? "!" : " :(";
                String gameScore = "Score: " + score + punctuation;
                gameScoreTv.setText(gameScore);
                intent.putExtra("SCORE", score);
                intent.putExtra("ACTIVITY", "mainActivity");
            }
        }.start();
    }

    // handles main menu button click event
    public void onclickButtonMain(View v) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void onclickButtonPlayAgain(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    // handles scores button click event
    public void onclickButtonScores(View v) {
        startActivity(intent);
    }
}
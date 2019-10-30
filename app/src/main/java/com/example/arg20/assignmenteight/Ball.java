package com.example.arg20.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;

class Ball extends Sprite {
    private float xVelocity, yVelocity, extraXVelocity, extraYVelocity;
    private boolean yDirection;
    private float originalY; // very first y position used for resetting the ball
    private boolean hasPlayed, hitTarget; // makes sure player cannot interfere with ball after each fling
    private Target ballTarget;


    public Ball(float x, float y, int radius, Target target, int yMaximum) {
        super(x, y, radius);
        originalY = y;
        yDirection = true;
        hasPlayed = false;
        hitTarget = false;
        ballTarget = target;
        yMax = yMaximum;
        extraXVelocity = 0;
        extraYVelocity = 0;
        speed = 0;
    }

    public void drawExplosion(Canvas canvas, Paint[] paints) {
        canvas.drawCircle(x, y, radius * 2, paints[2]);
        canvas.drawCircle(x, y, ((radius * 2) / 4) * 3, paints[1]);
    }
    public void draw(Canvas canvas, Paint[] paints) {
        canvas.drawCircle(x, y, radius, paints[1]);
        canvas.drawCircle(x, y, (radius / 6) * 5, paints[0]);
        speed = Math.abs((int) (xVelocity + yVelocity));

        if (speed < 1 && hasPlayed) {
            reset();
        }

        // handle x direction
        if (x >= xMax - radius || x <= radius) {
            xDirection = !xDirection;
        }

        if (xDirection) {
            x += xVelocity + extraXVelocity;
        } else {
            x -= xVelocity + extraXVelocity;
        }

        // handle y direction
        if (y >= yMax - radius) {
            yDirection = !yDirection;
        }

        if (yDirection) {
            y += yVelocity + extraYVelocity;
        } else {
            y -= yVelocity + extraYVelocity;
        }

        // once ball gets past top of screen, reset it
        if (y <= -radius) {
            reset();
        }
    }

    public void hitTarget() {
        hitTarget = true;
    }

    // reset the position and speed of the ball
    public void reset() {
        y = originalY;
        x = rand.nextInt(xMax - radius * 4) + radius * 2;
        xVelocity = 0;
        yVelocity = 0;
        xDirection = true;
        yDirection = true;
        hasPlayed = false;
        extraXVelocity = 0;
        extraYVelocity = 0;

        // reset target after hitting it
        if (hitTarget) {
            ballTarget.reset();
            hitTarget = false;
        }
    }

    public void setHasPlayed() {
        hasPlayed = true;
    }

    public boolean hasPlayed() {
        return hasPlayed;
    }

    public void setXVelocity(float x) {
        xVelocity = x;
    }

    public void setYVelocity(float y) {
        yVelocity = y;
    }
}

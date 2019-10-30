package com.example.arg20.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;

class Target extends NonPlayerSprite {
    private float originalY;
    private int originalRadius;

    public Target(float x, float y, int radius) {
        super(x, y , radius);
        originalY = y;
        originalRadius = radius;
    }

    public void draw(Canvas canvas, Paint[] paints) {
        canvas.drawCircle(x, y, radius, paints[0]);
        canvas.drawCircle(x, y, (radius / 4) * 3, paints[1]);
        move();
    }

    // removes the target from the screen
    public void remove() {
        y = yMax + 4 * radius;
        radius = 0;
    }

    // reset the position and speed of the ball
    public void reset() {
        x = rand.nextInt(xMax - radius);
        y = originalY;
        radius = originalRadius;
        xDirection = rand.nextBoolean();
        speed = rand.nextInt(10);
    }
}

package com.example.arg20.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

abstract class Sprite {
    protected float x, y;
    protected int radius, speed;
    protected static int xMax, yMax;
    protected boolean xDirection;
    protected Random rand;

    public Sprite(float xPosition, float yPosition, int spriteRadius) {
        rand = new Random();
        x = xPosition;
        y = yPosition;
        xDirection = rand.nextBoolean();
        radius = spriteRadius;
        speed = 0;
    }

    protected abstract void draw(Canvas canvas, Paint[] paints);

    // returns true if there is a collision between two sprites, false otherwise
    public boolean collision(Sprite other) {
        return Math.abs(x - other.x) < radius + other.radius && Math.abs(y - other.y) < radius + other.radius;
    }
}

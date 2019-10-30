package com.example.arg20.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;

class Obstacle extends NonPlayerSprite {

    public Obstacle(float x, float y, int radius) {
        super(x, y, radius);
    }

    public void draw(Canvas canvas, Paint[] paints) {
        canvas.drawCircle(x, y, radius * 1.5f, paints[0]);
        canvas.drawCircle(x, y, ((radius * 1.5f) / 9) * 8, paints[1]);
        canvas.drawCircle(x, y, radius, paints[0]);
        move();
    }
}

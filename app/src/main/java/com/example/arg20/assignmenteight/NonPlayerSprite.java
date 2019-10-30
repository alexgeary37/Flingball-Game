package com.example.arg20.assignmenteight;

// class for all sprites that are not the player
// includes the default move method for them
abstract class NonPlayerSprite extends Sprite {

    public NonPlayerSprite(float x, float y, int radius) {
        super(x, y, radius);
        speed = rand.nextInt(10);
    }

    // default move method for non player sprites
    protected void move() {
        if (xDirection) {
            if (x <= xMax - radius) {
                x += speed;
            } else {
                xDirection = false;
            }
        } else {
            if (x > radius) {
                x -= speed;
            } else {
                xDirection = true;
            }
        }
    }
}

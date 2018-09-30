package com.meeethew.main;

import javafx.scene.canvas.GraphicsContext;

public class BulletEntity extends Entity {

    public BulletEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {

        y -= 3;

        if (y < 0) {
            alive = false;
        }

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillRect(x, y, width, height);
    }

}

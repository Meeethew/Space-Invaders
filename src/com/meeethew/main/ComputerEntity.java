package com.meeethew.main;

import javafx.scene.canvas.GraphicsContext;

import static com.meeethew.main.StaticData.SCREEN_WIDTH;

public class ComputerEntity extends Entity {

    private int direction;

    public ComputerEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
        direction = 2;
    }

    @Override
    public void update() {

        x += direction;

        if (x > SCREEN_WIDTH) {
            y += width;
            direction = -2;
        }
        if (x < (0 - width)) {
            y += width;
            direction = 2;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillOval(x, y, width, height);
    }
}

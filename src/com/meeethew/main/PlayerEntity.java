package com.meeethew.main;

import javafx.scene.canvas.GraphicsContext;

public class PlayerEntity extends Entity {
    public PlayerEntity(double x, double y, double width, double height) {
        super(x, y, width, height );
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillRect(x, y, width, height);
    }

}

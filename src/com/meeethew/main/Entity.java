package com.meeethew.main;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected double x;
    protected double y;
    protected double height;
    protected double width;
    protected boolean alive;

    public Entity(double x, double y, double width, double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        alive = true;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isAlive(){
        return alive;
    }

    public abstract void update();

    public abstract void render(GraphicsContext gc);

}

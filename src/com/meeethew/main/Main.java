package com.meeethew.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static com.meeethew.main.StaticData.HEIGHT;
import static com.meeethew.main.StaticData.WIDTH;

public class Main extends Application {

    private GraphicsContext gc;
    private PlayerEntity p;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent(WIDTH, HEIGHT)));
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();
        initSetup();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
    }

    private Parent createContent(double prefWidth, double prefHeight) {
        Pane root = new Pane();
        root.setPrefSize(prefWidth, prefHeight);

        Canvas canvas = new Canvas(prefWidth, prefHeight);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(event -> {
            //Create a bullet object
            System.out.println("Mouse Clicked");
        });
        canvas.setOnMouseMoved(event -> {
            p.setX(event.getX());
            System.out.println(p.getX());
        });
        root.getChildren().add(canvas);

        return root;

    }

    private void initSetup() {
        p = new PlayerEntity(WIDTH / 2, HEIGHT - 50, 50, 25);
    }

    private void onUpdate() {

        if ((p.getX()) < 10) {
            p.setX(0);
        }

        if (p.getX() > WIDTH - p.getWidth()) {
            p.setX(WIDTH - p.getWidth());
        }

        gc.clearRect(0, 0, WIDTH, HEIGHT);
        p.render(gc);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

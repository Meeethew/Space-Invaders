package com.meeethew.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.meeethew.main.StaticData.*;

public class Main extends Application {

    private GraphicsContext gc;
    private PlayerEntity player;
    private List<Entity> playerBullets;
    private List<Entity> enemyBullets;
    private List<Entity> enemies;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent(SCREEN_WIDTH, SCREEN_HEIGHT)));
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();

        //Calls the initSetup method to set up the game objects
        initSetup();

        //Sets up a new animation timer that refreshes the screen 60 times per second
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };

        //Starts the animation timer object
        timer.start();
    }

    private Parent createContent(double prefWidth, double prefHeight) {

        Pane root = new Pane();
        root.setPrefSize(prefWidth, prefHeight);

        Canvas canvas = new Canvas(prefWidth, prefHeight);
        gc = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event -> {
            //Create a player bullet object when the mouse is clicked on the canvas
            firePlayerBullet();
        });

        canvas.setOnMouseMoved(event -> {
            //Move player object when the mouse if moved on the canvas
            movePlayer(event.getX());
        });

        //Adds all the objects to the pane
        root.getChildren().add(canvas);

        //Returns the root to the scene ready for adding to the PrimaryStage
        return root;

    }

    private void initSetup() {
        //Instantiate player entity
        player = new PlayerEntity(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 50, 50, 25);

        //Instantiate player bullets
        playerBullets = new ArrayList<>();

        //TODO: Instantiate enemy aliens

        //TODO: Instantiate enemy alien bullets

    }

    private void onUpdate() {

        //Sets player entity limitations to avoid player entity moving off screen
        if ((player.getX()) < 10) {
            player.setX(0);
        }

        if (player.getX() > SCREEN_WIDTH - player.getWidth()) {
            player.setX(SCREEN_WIDTH - player.getWidth());
        }

        //Clears graphic context so its ready for repainting
        gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Iterates through player bullets updating locations and rendering to the screen
        playerBullets.forEach(bullet ->
                {
                    bullet.update();
                    bullet.render(gc);
                }
        );

        //Iterates through the player bullets and removes objects that are no longer alive (i.e. objects that have left the screen or hit an alien)
        playerBullets.removeIf(bullet -> !bullet.isAlive());

        //Renders the player object to the graphics context
        player.render(gc);
    }

    private void movePlayer(double x) {

        //Takes the mouses x coordinate and sets it to the player entity
        player.setX(x);

    }

    private void firePlayerBullet() {

        //Creates a new object of the player bullet and adds to the array list
        playerBullets.add(new BulletEntity((player.getX() + player.getWidth() / 2 - BULLET_WIDTH ), player.getY(), BULLET_WIDTH, 15));

    }


    public static void main(String[] args) {
        launch(args);
    }
}

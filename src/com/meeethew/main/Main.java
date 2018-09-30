package com.meeethew.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.meeethew.main.StaticData.*;

public class Main extends Application {

    private AnimationTimer timer;
    private boolean isPaused;
    private GraphicsContext gc;
    private PlayerEntity player;
    private List<Entity> playerBullets;
    private List<Entity> enemyBullets;
    private List<Entity> enemies;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent(SCREEN_WIDTH, SCREEN_HEIGHT)));
        primaryStage.setTitle("Space Invaders");
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            //Handles key released
            keyReleased(event);
        });

        primaryStage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Create a player bullet object when the mouse is clicked on the canvas
            System.out.println(event);
            firePlayerBullet();
        });

        primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            //Move the player object is relation to the mouse x position
            movePlayer(event.getX(), event.getY());
        });

        //Shows the UI
        primaryStage.show();

        //Calls the initSetup method to set up the game objects
        initSetup();

        //Sets up a new animation timer that refreshes the screen 60 times per second
        timer = new AnimationTimer() {
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

//        canvas.setOnMouseClicked(event -> {
//            //Create a player bullet object when the mouse is clicked on the canvas
//            System.out.println(event);
//            firePlayerBullet();
//        });

//        canvas.setOnMouseMoved(event -> {
//            //Move player object when the mouse if moved on the canvas
//            movePlayer(event.getX());
//        });

        //Adds all the objects to the pane
        root.getChildren().add(canvas);

        //Returns the root to the scene ready for adding to the PrimaryStage
        return root;

    }

    private void initSetup() {

        //Game starts not paused
        isPaused = false;

        //Instantiate player entity
        player = new PlayerEntity(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 50, 50, 25);

        //Instantiate player bullets
        playerBullets = new ArrayList<>();

        //Instantiate enemy aliens
        enemies = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            enemies.add(new ComputerEntity((150 * i / 2) + 150, 50, 50, 20));
        }

        //Instantiate enemy bullets
        enemyBullets = new ArrayList<>();
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

        //Renders the player object to the graphics context
        player.render(gc);

        //Iterates through player bullets updating locations and rendering to the screen
        playerBullets.forEach(bullet ->
                {
                    bullet.update();
                    bullet.render(gc);
                }
        );

        //Iterates through enemy aliens updating locations, checking for player/bullet collisions and rendering to the screen
        enemies.forEach(enemy ->
                {
                    enemy.update();
                    enemy.render(gc);

                    if (enemy.getBounds().intersects(player.getBounds())) {
                        playerLifeLost();

                    }

                    playerBullets.forEach(bullet ->
                    {
                        if (bullet.getBounds().intersects(enemy.getBounds())) {
                            //System.out.println("Bullet and Enemy collision detected");
                            bullet.setAlive(false);
                            enemy.setAlive(false);

                        }
                    });
                }
        );

        //Iterates through the player bullets and removes objects that are no longer alive (i.e. objects that have left the screen or hit an alien)
        playerBullets.removeIf(bullet -> !bullet.isAlive());
        enemies.removeIf(enemy -> !enemy.isAlive());

        //check whether the enemies array size is zero meaning that the level is complete
        if (enemies.size() == 0) {
            levelComplete();
        }
    }

    private void movePlayer(double x, double y) {

        //Takes the mouses x coordinate and sets it to the player entity
        player.setX(x);
        //player.setY(y);

    }

    private void firePlayerBullet() {

        //Creates a new object of the player bullet and adds to the array list
        playerBullets.add(new BulletEntity((player.getX() + player.getWidth() / 2 - BULLET_WIDTH), player.getY(), BULLET_WIDTH, 15));

    }

    private void levelComplete() {

    }

    private void playerLifeLost() {

    }

    private void keyReleased(KeyEvent event) {
        System.out.println(event);
        if (event.getText().equals("p") || event.getText().equals("P")) {
            if (isPaused) {
                isPaused = false;
                timer.start();
            } else {
                isPaused = true;
                timer.stop();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

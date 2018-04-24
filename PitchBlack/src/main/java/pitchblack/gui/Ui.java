/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.gui;

import java.util.ArrayList;
import java.util.HashMap;
import pitchblack.dao.ScoresDao;
import pitchblack.database.Database;
import pitchblack.domain.Score;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import pitchblack.domain.GameMotor;
import pitchblack.domain.Player;

/**
 *
 * @author JoonaHa
 */
public class Ui extends Application {

    public static double WIDTH;
    public static double HEIGHT;
    private static ScoresDao scoreDao;

    public static void main(String[] args) throws Exception {

        launch(args);

    }

    @Override
    public void init() throws Exception {
        WIDTH = 900;
        HEIGHT = 600;
        Database db = new Database("jdbc:sqlite:db/highScores.db");
        scoreDao = new ScoresDao(db);

    }

    @Override
    public void start(Stage mainStage) throws Exception {

        mainStage.setTitle("PitchBlack");

        // Creates the starting screen with two buttons
        // Add cache for scores
        Pane menu = new Pane();
        menu.setPrefSize(WIDTH, HEIGHT);

        VBox menuButtons = new VBox();

        menuButtons.setAlignment(Pos.TOP_CENTER);
        menuButtons.setTranslateX(390);
        menuButtons.setTranslateY(150);

        Button startBtn = new Button("NEW GAME");
        Button scoresBtn = new Button("HIGH SCORES");
        startBtn.setMaxWidth(Double.MAX_VALUE);
        scoresBtn.setMaxWidth(Double.MAX_VALUE);

        menuButtons.setSpacing(120);
        menuButtons.getChildren().add(startBtn);
        menuButtons.getChildren().add(scoresBtn);

        menu.getChildren().add(menuButtons);
        Scene startingScene = new Scene(menu);

        // Create High score screen
        Pane scorePane = new Pane();
        scorePane.setPrefSize(WIDTH, HEIGHT);

        // Make tablview and colums for highscore info
        TableView<Score> scoreTbl = new TableView<>();
        scoreTbl.setPrefSize(WIDTH, HEIGHT);

        TableColumn<Score, String> nameColum = new TableColumn<>("NICKNAME");
        nameColum.setMinWidth(400);
        nameColum.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        TableColumn<Score, Integer> scoreColum = new TableColumn<>("POINTS");
        scoreColum.setMinWidth(200);
        scoreColum.setCellValueFactory(new PropertyValueFactory<>("score"));

        scoreTbl.getColumns().addAll(nameColum, scoreColum);
        scoreTbl.setItems(getScores());

        scorePane.getChildren().add(scoreTbl);
        Scene scoreScene = new Scene(scorePane);

        // Creating the gamescene
        Pane gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        Scene gameScene = new Scene(gamePane);
        // Add player
        Player player = new Player(WIDTH / 2, HEIGHT / 2);
        gamePane.getChildren().addAll(player.getShape(), player.getLamp());
        // Add "darkness"
        Rectangle dark = new Rectangle(WIDTH, HEIGHT);
        dark.setFill(Color.BLACK);

        // Show startingScene
        mainStage.setScene(startingScene);
        mainStage.show();

        // Mainstage menu buttonactions
        scoresBtn.setOnAction((event) -> {
            mainStage.setScene(scoreScene);
        });

        startBtn.setOnAction((event) -> {
            mainStage.setScene(gameScene);
        });

        HashMap<KeyCode, Boolean> buttonPresses = new HashMap<>();

        gameScene.setOnKeyPressed((event -> {
            buttonPresses.put(event.getCode(), Boolean.TRUE);
        }));

        gameScene.setOnKeyReleased((event -> {
            buttonPresses.put(event.getCode(), Boolean.FALSE);
        }));

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                
                GameMotor.getInstance(buttonPresses, player).update();

                // Make player.lamp cut trough dark DOES NOT WORK
                Shape s = Shape.subtract(dark, player.getLamp());

            }

        }.start();

    }

    private ObservableList<Score> getScores() throws Exception {
        ObservableList scores = FXCollections.observableArrayList();
        scores.addAll(scoreDao.getAll());

        return scores;
    }

}

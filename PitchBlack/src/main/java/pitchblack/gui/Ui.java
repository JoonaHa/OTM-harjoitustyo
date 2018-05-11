package pitchblack.gui;

import java.util.HashMap;
import pitchblack.dao.ScoresDao;
import pitchblack.database.Database;
import pitchblack.domain.Score;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pitchblack.domain.GameMotor;
import pitchblack.domain.Player;

/**
 *
 * @author JoonaHa
 */
public class Ui extends Application {

    public static double WIDTH = 900;
    public static double HEIGHT = 600;
    private static ScoresDao scoreDao;
    private static Pane gamePane;

    public static void main(String[] args) throws Exception {

        launch(args);

    }

    @Override
    public void init() throws Exception {

        Database db = new Database("highScores.db");
        scoreDao = new ScoresDao(db);

    }

    @Override
    public void start(Stage mainStage) throws Exception {

        mainStage.setTitle("PitchBlack");

        // Creates the starting screen with two buttons
        // Add cache for scores
        Pane menuPane = new Pane();
        menuPane.setPrefSize(WIDTH, HEIGHT);

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

        menuPane.getChildren().add(menuButtons);
        Scene menuScene = new Scene(menuPane);

        // Create High score screen
        BorderPane scoreBorderP = new BorderPane();
        scoreBorderP.setPrefSize(WIDTH, HEIGHT);
        // Make tableview and colums for highscore info
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
        scoreTbl.getSortOrder().add(scoreColum);
        //Make a back button for highscore
        Button toMenuBtn = new Button("BACK");
        scoreBorderP.setCenter(scoreTbl);
        scoreBorderP.setBottom(toMenuBtn);
        Scene scoreScene = new Scene(scoreBorderP);

        // Creating the gamescene
        gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        Scene gameScene = new Scene(gamePane);

        //Create pause Screen
        Pane pausePane = new Pane();
        pausePane.setPrefSize(WIDTH, HEIGHT);
        VBox pauseButtons = new VBox();

        pauseButtons.setSpacing(80);
        pauseButtons.setAlignment(Pos.TOP_CENTER);
        pauseButtons.setTranslateX(390);
        pauseButtons.setTranslateY(150);

        Button resumeBtn = new Button("RESUME");
        Button quitBtn = new Button("QUIT");
        pauseButtons.getChildren().addAll(resumeBtn, quitBtn);

        pausePane.getChildren().add(pauseButtons);
        Scene pauseScene = new Scene(pausePane);

        //Create Game over screen
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(WIDTH, HEIGHT);
        VBox gameOverVbox = new VBox();

        gameOverVbox.setSpacing(40);
        gameOverVbox.setAlignment(Pos.TOP_CENTER);
        gameOverVbox.setTranslateX(315);
        gameOverVbox.setTranslateY(150);

        Label gameOverTitleLabel = new Label("GAME OVER");
        gameOverTitleLabel.setFont(Font.font(45));
        Label gameOverScoreLabel = new Label("SCORE: " + 0);
        gameOverScoreLabel.setFont(Font.font(25));

        Label gamemOVerHighscoreLabel = new Label("Do you want to save your score?");

        Button addHSBtn = new Button("ADD");
        Button quitHSBtn = new Button("MAIN MENU");
        HBox addHSButtons = new HBox();
        addHSButtons.setSpacing(40);
        addHSButtons.setTranslateX(40);
        addHSButtons.getChildren().addAll(quitHSBtn, addHSBtn);

        TextField nicknamefield = new TextField();
        Label nicknameFieldFeedback = new Label("");
        nicknameFieldFeedback.setTranslateY(HEIGHT - 197);
        nicknameFieldFeedback.setTranslateX(WIDTH / 2 - 130);

        gameOverVbox.getChildren().addAll(gameOverTitleLabel, gameOverScoreLabel,
                gamemOVerHighscoreLabel, nicknamefield, addHSButtons);

        gameOverPane.getChildren().addAll(gameOverVbox, nicknameFieldFeedback);
        Scene gameOverScene = new Scene(gameOverPane);

        // record buttons and mouse events
        HashMap<KeyCode, Boolean> input = new HashMap<>();
        HashMap<MouseButton, Boolean> mouseClicks = new HashMap<>();
        HashMap<Boolean, MouseEvent> mouseMovements = new HashMap<>();

//        // Add darkness
        Darkness dark = new Darkness();
        gamePane.getChildren().add(dark.update(GameMotor.getInstance(input, mouseMovements, mouseClicks).getPlayer().getLamp()));

        //Add score
        ScoreMeter scoreMeter = new ScoreMeter();
        gamePane.getChildren().add(scoreMeter.update(0));

        // Show startingScreen
        mainStage.setScene(menuScene);
        mainStage.show();

        //keyboad and mouseEvent handling
        gameScene.setOnKeyPressed((event -> {
            input.put(event.getCode(), Boolean.TRUE);
        }));

        gameScene.setOnKeyReleased((event -> {

            // Escape buttons shows pauseScreen
            if (event.getCode() == KeyCode.ESCAPE) {
                GameMotor.getInstance(input, mouseMovements, mouseClicks).pause();
                mainStage.setScene(pauseScene);
            }

            input.put(event.getCode(), Boolean.FALSE);
        }));

        gameScene.setOnMousePressed((event -> {
            mouseClicks.put(event.getButton(), Boolean.TRUE);

        }));

        gameScene.setOnMouseReleased((event -> {
            mouseClicks.put(event.getButton(), Boolean.FALSE);
        }));
        gameScene.setOnMouseMoved((event -> {
            mouseMovements.put(Boolean.TRUE, event);
        }));

        //javafx Button actions
        // Show Highscore screen button
        scoresBtn.setOnAction((event) -> {
            this.updateScoreTable(scoreTbl);
            mainStage.setScene(scoreScene);
        });

        // Highscore Screen Back button
        toMenuBtn.setOnAction((event) -> {
            mainStage.setScene(menuScene);
        });

        // Start a new game Button
        startBtn.setOnAction((event) -> {
            GameMotor.getInstance(input, mouseMovements, mouseClicks).newGame();
            mainStage.setScene(gameScene);
            GameMotor.getInstance(input, mouseMovements, mouseClicks).resume();
        });
        // Resume game Button
        resumeBtn.setOnAction((event) -> {
            GameMotor.getInstance(input, mouseMovements, mouseClicks).resume();
            mainStage.setScene(gameScene);
        });

        // Quit game Button
        quitBtn.setOnAction((event) -> {
            GameMotor.getInstance(input, mouseMovements, mouseClicks).quit();
            mainStage.setScene(menuScene);
        });
        // Quit endscreen Button
        quitHSBtn.setOnAction((event -> {
            GameMotor.getInstance(input, mouseMovements, mouseClicks).quit();
            mainStage.setScene(menuScene);
        }));

        addHSBtn.setOnAction((event -> {
            String nickName = nicknamefield.getText().trim();
            int points = GameMotor.getInstance(input,
                    mouseMovements, mouseClicks).getScore();

            if (nickName.length() < 1) {
                nicknameFieldFeedback.setText("Give a proper nickname!");
                nicknameFieldFeedback.setTextFill(Color.RED);

            } else if (nickName.length() > 100) {
                nicknameFieldFeedback.setText("Nickname can only be 100 characters long!");
                nicknameFieldFeedback.setTextFill(Color.RED);

            } else if (!isAlphanumeric(nickName)) {
                nicknameFieldFeedback.setText("Nickname can only contain alphanumeric characters!");
                nicknameFieldFeedback.setTextFill(Color.RED);
            } else {

                Score score = new Score(nickName, points);
                this.addScore(score);
                this.updateScoreTable(scoreTbl);

                mainStage.setScene(scoreScene);

            }

        }));

        new AnimationTimer() {

            @Override
            public void handle(long l) {

                //Check if Game Over screen needs to be shown
                boolean gameOver = GameMotor.getInstance(input, mouseMovements, mouseClicks).isGameOver();
                int score = GameMotor.getInstance(input, mouseMovements, mouseClicks).getScore();

                if (gameOver) {

                    Player player = GameMotor.getInstance(input, mouseMovements, mouseClicks).getPlayer();
                    boolean running = GameMotor.getInstance(input, mouseMovements, mouseClicks).isRunning();
                    gameOverScoreLabel.setText("SCORE: " + score);
                    mainStage.setScene(gameOverScene);
                    GameMotor.getInstance(input, mouseMovements, mouseClicks).quit();

                }

                //Make player.lamp cut trough dark 
                gamePane.getChildren().remove(dark.getOld());
                gamePane.getChildren().add(dark.update(GameMotor.getInstance(input, mouseMovements, mouseClicks).getPlayer().getLamp()));

                //Update ScoreMeter
                gamePane.getChildren().remove(scoreMeter.getOld());
                gamePane.getChildren().add(scoreMeter.update(score));

                // Update Game's state
                GameMotor.getInstance(input, mouseMovements, mouseClicks).update();
            }

        }.start();

    }

    private ObservableList<Score> getScores() throws Exception {
        ObservableList scores = FXCollections.observableArrayList();
        scores.addAll(scoreDao.getAll());

        return scores;
    }

    private void addScore(Score score) {

        try {
            scoreDao.add(score);

        } catch (Exception e) {

        }

    }

    private void updateScoreTable(TableView<Score> scoreTbl) {
        try {
            scoreTbl.setItems(getScores());
        } catch (Exception e) {

        }

    }

    private boolean isAlphanumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a) {
                return false;
            }
        }
        return true;
    }

    public static void addNodeToGame(Node node) {
        gamePane.getChildren().add(node);
    }

    public static void removeNodeFromGame(Node node) {
        gamePane.getChildren().remove(node);
    }

}

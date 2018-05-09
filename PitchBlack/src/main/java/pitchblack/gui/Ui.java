package pitchblack.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import pitchblack.domain.GameMotor;

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

        Database db = new Database("jdbc:sqlite:highScores.db");
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
        //Make a back button for highscore
        Button toMenuBtn = new Button("BACK");
        scoreBorderP.setCenter(scoreTbl);
        scoreBorderP.setBottom(toMenuBtn);
        Scene scoreScene = new Scene(scoreBorderP);

        // Creating the gamescene
        gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        Scene gameScene = new Scene(gamePane);

        // test recntangle
        Shape test = new Rectangle(10, 10);
        test.setTranslateX(WIDTH / 3);
        test.setTranslateY(WIDTH / 3);
        gamePane.getChildren().add(test);

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

        // record buttons and mouse events
        HashMap<KeyCode, Boolean> input = new HashMap<>();
        HashMap<MouseButton, Boolean> mouseClicks = new HashMap<>();
        Stack<MouseEvent>  mouseMovements = new Stack<>();

//        // Add darkness
        Darkness dark = new Darkness();
        gamePane.getChildren().add(dark.update(GameMotor.getInstance(input, mouseMovements, mouseClicks).getPlayer().getLamp()));
        
        
        
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
            mouseMovements.add(event);
        }));

        // Show startingScreen
        mainStage.setScene(menuScene);
        mainStage.show();

        // Show Highscore screen button
        scoresBtn.setOnAction((event) -> {
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
        });
        // Resume game Button
        resumeBtn.setOnAction((event) -> {
            GameMotor.getInstance(input, mouseMovements, mouseClicks).resume();
            mainStage.setScene(gameScene);
        });

        // Quit game Button
        quitBtn.setOnAction((event) -> {
            gamePane.getChildren().remove(GameMotor.getInstance(input, mouseMovements, mouseClicks).getPlayer().getShape());
            mainStage.setScene(menuScene);
        });

        new AnimationTimer() {

            @Override
            public void handle(long l) {

                GameMotor.getInstance(input, mouseMovements, mouseClicks).update();

//                Make player.lamp cut trough dark 
                gamePane.getChildren().remove(dark.getOld());

                gamePane.getChildren().add(dark.update(GameMotor.getInstance(input, mouseMovements, mouseClicks).getPlayer().getLamp()));
            }

        }.start();

    }

    private ObservableList<Score> getScores() throws Exception {
        ObservableList scores = FXCollections.observableArrayList();
        scores.addAll(scoreDao.getAll());

        return scores;
    }

    public static void addNodeToGame(Node node) {
        gamePane.getChildren().add(node);
    }
    
        public static void removeNodeFromGame(Node node) {
        gamePane.getChildren().remove(node);
    }

}

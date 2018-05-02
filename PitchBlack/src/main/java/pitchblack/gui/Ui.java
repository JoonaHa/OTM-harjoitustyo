
package pitchblack.gui;

import java.util.ArrayList;
import java.util.HashMap;
import pitchblack.dao.ScoresDao;
import pitchblack.database.Database;
import pitchblack.domain.Score;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    public static double WIDTH = 900;
    public static double HEIGHT = 600;
    private static ScoresDao scoreDao;

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
        Scene menuScene = new Scene(menu);

        // Create High score screen
        Pane scorePane = new Pane();
        scorePane.setPrefSize(WIDTH, HEIGHT);
        BorderPane scoreBorderP = new BorderPane();
        scoreBorderP.setPrefSize(WIDTH, HEIGHT);

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

        //Make a back button
        Button toMenuBtn = new Button("BACK");

        scoreBorderP.setCenter(scoreTbl);
        scoreBorderP.setBottom(toMenuBtn);
        Scene scoreScene = new Scene(scoreBorderP);

        // Creating the gamescene
        Pane gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        Scene gameScene = new Scene(gamePane);
        // Add player
        Player player = new Player(WIDTH / 2, HEIGHT / 2);
        gamePane.getChildren().add(player.getShape());

        // test recntangle
        Shape test = new Rectangle(10, 10);
        test.setTranslateX(WIDTH / 3);
        test.setTranslateY(WIDTH / 3);
        gamePane.getChildren().add(test);

        // Add darkness
        Darkness dark = new Darkness();
        gamePane.getChildren().add(dark.update(player.getLamp()));

        // Show startingScreen
        mainStage.setScene(menuScene);
        mainStage.show();

        // startingScene button actions
        scoresBtn.setOnAction((event) -> {
            mainStage.setScene(scoreScene);
        });

        // start a new game
        startBtn.setOnAction((event) -> {
            mainStage.setScene(gameScene);
        });

        // toMenu button actions
        toMenuBtn.setOnAction((event) -> {
            mainStage.setScene(menuScene);
        });

        // record buttons and mouse events
        HashMap<KeyCode, Boolean> input = new HashMap<>();
        ArrayList<MouseEvent> mouseEvents = new ArrayList<>();

        gameScene.setOnKeyPressed((event -> {
            input.put(event.getCode(), Boolean.TRUE);

        }));

        gameScene.setOnKeyReleased((event -> {
            input.put(event.getCode(), Boolean.FALSE);
        }));

        gameScene.setOnMouseMoved((event -> {
            mouseEvents.add(event);
        }));

        new AnimationTimer() {

            @Override
            public void handle(long l) {

                GameMotor.getInstance(input, mouseEvents, player).update();

                // Make player.lamp cut trough dark 
                gamePane.getChildren().remove(dark.getOld());

                gamePane.getChildren().add(dark.update(player.getLamp()));

            }

        }.start();

    }

    private ObservableList<Score> getScores() throws Exception {
        ObservableList scores = FXCollections.observableArrayList();
        scores.addAll(scoreDao.getAll());

        return scores;
    }

}

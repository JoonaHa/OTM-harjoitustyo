/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ScoresDao;
import database.Database;
import domain.Score;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author JoonaHa
 */
public class Ui extends Application {

    private  static ScoresDao scoreDao;

    @Override
    public void init() throws Exception {
        Database db = new Database("jdbc:sqlite:db/highScores.db");
        scoreDao = new ScoresDao(db);

    }

    @Override
    public void start(Stage mainStage) throws Exception {

        // Creates the starting screen with two buttons
        Pane menu = new Pane();
        menu.setPrefSize(900, 600);

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
        scorePane.setPrefSize(900, 600);

        // Make tablview and colums for highscore info
        TableView<Score> scoreTbl = new TableView<>();
        scoreTbl.setPrefSize(900, 600);

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

        // Show startingScene
        mainStage.setScene(startingScene);
        mainStage.show();

        scoresBtn.setOnAction((event) -> {
            mainStage.setScene(scoreScene);
        });

    }

    private ObservableList<Score> getScores() throws Exception {
        ObservableList scores = FXCollections.observableArrayList();
        scores.addAll(scoreDao.getAll());

        return scores;
    }

    public static void main(String[] args) throws Exception {
        
        launch(args);
        
        
       
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author JoonaHa
 */
public class GameMotor {

    private static GameMotor gameMotor;
    private final HashMap<KeyCode, Boolean> input;
    private final ArrayList<MouseEvent> mouseEvents;
    private final Player player;

    private GameMotor(HashMap<KeyCode, Boolean> input, ArrayList<MouseEvent> mouseEvents, Player player) {
        this.input = input;
        this.player = player;
        this.mouseEvents = mouseEvents;
    }

    public static GameMotor getInstance(HashMap<KeyCode, Boolean> input, ArrayList<MouseEvent> mouseEvents, Player player) {
        if (gameMotor == null) {
            gameMotor = new GameMotor(input, mouseEvents, player);
        }
        return gameMotor;
    }

    public void update() {

        player.setVelocity(new Point2D(0, 0));

        if (input.getOrDefault(KeyCode.W, Boolean.FALSE)) {
            player.setVelocity((new Point2D(player.getVelocity().getX(), -5)));

        }

        if (input.getOrDefault(KeyCode.S, Boolean.FALSE)) {
            player.setVelocity((new Point2D(player.getVelocity().getX(), 5)));

        }

        if (input.getOrDefault(KeyCode.A, Boolean.FALSE)) {
            player.setVelocity((new Point2D(-5, player.getVelocity().getY())));

        }

        if (input.getOrDefault(KeyCode.D, Boolean.FALSE)) {
            player.setVelocity((new Point2D(5, player.getVelocity().getY())));

        }

        if (mouseEvents.size() > 0) {
            player.rotateTowards(mouseEvents.get(mouseEvents.size() - 1).getSceneX(), mouseEvents.get(mouseEvents.size() - 1).getSceneY());
        }

        player.update();
    }

}

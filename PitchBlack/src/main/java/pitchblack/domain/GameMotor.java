/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

/**
 *
 * @author mina
 */
public class GameMotor {

    private static GameMotor gameMotor;
    private HashMap<KeyCode, Boolean> input;
    private Player player;

    private GameMotor(HashMap<KeyCode, Boolean> input, Player player) {
        this.input = input;
        this.player = player;
    }

    public static GameMotor getInstance(HashMap<KeyCode, Boolean> input, Player player) {
        if (gameMotor == null) {
            gameMotor = new GameMotor(input, player);
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

        player.update();
    }

}

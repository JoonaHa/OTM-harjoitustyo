package pitchblack.domain;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import static pitchblack.gui.Ui.HEIGHT;
import static pitchblack.gui.Ui.WIDTH;

/**
 * Singleton-luokka, joka käsittelee pelin tiloja ja pelaajan syötteitä.
 *
 * @author JoonaHa
 *
 */
public class GameMotor {

    private static GameMotor gameMotor;
    private final HashMap<KeyCode, Boolean> input;
    private final ArrayList<MouseEvent> mouseEvents;
    private Player player;
    private Boolean running;

    /**
     *
     *
     */
    private GameMotor(HashMap<KeyCode, Boolean> input, ArrayList<MouseEvent> mouseEvents) {
        this.input = input;
        this.mouseEvents = mouseEvents;
        this.player = new Player(WIDTH / 2, HEIGHT / 2);
        this.player.rotateTowards(WIDTH/2, 0);
        this.running = true;

    }

    /**
     * Luokka palauttaa GameMotor instanssin.
     *
     * @param input Käyttäjän näppäimistö painallukset.
     * @param mouseEvents Käyttäjän hiiren tapahtumat.
     * @return GameMotor instanssi.
     */
    public static GameMotor getInstance(HashMap<KeyCode, Boolean> input, ArrayList<MouseEvent> mouseEvents) {
        if (gameMotor == null) {
            gameMotor = new GameMotor(input, mouseEvents);
        }
        return gameMotor;
    }

    /**
     * Päivittäää pelin tilan.
     */
    public void update() {

        if (!this.running) {
            return;
        }

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

    
    public Player getPlayer() {
        return player;
    }
    

     /**
     * Pysäyttää pelin eli updaten käsittelyn.
     * Eli running = false.
     */
    public void pause() {
        this.running = false;
    }

    /**
     * Jatkaa peliä eli palauttaa updaten käsittelyn.
     * Eli running = true.
     * 
     */
    public void resume() {
        this.running = true;
    }

     /**
     * Resetoi pelin tilan alkutilanteeseen.
     */
    public void newGame() {
        this.player = new Player(WIDTH / 2, HEIGHT / 2);
        this.player.rotateTowards(WIDTH/2, 0);
        this.resume();

    }

}

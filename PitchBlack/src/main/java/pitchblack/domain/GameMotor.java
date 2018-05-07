package pitchblack.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pitchblack.gui.Ui;
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
    private final HashMap<MouseButton, Boolean> mouseClicks;
    private final ArrayList<MouseEvent> mouseEvents;
    private Player player;
    private Boolean running;
    private int score;
    private List<Bullet> bullets;

    /**
     *
     *
     */
    private GameMotor(HashMap<KeyCode, Boolean> input, ArrayList<MouseEvent> mouseEvents, HashMap<MouseButton, Boolean> mouseClicks) {
        this.input = input;
        this.mouseClicks = mouseClicks;
        this.mouseEvents = mouseEvents;
        this.player = new Player(WIDTH / 2, HEIGHT / 2);
        this.player.rotateTowards(WIDTH / 2, 0);
        this.running = true;
        this.score = 0;
        this.bullets = new ArrayList<>();

    }

    /**
     * Luokka palauttaa GameMotor instanssin.
     *
     * @param input Käyttäjän näppäimistö painallukset.
     * @param mouseEvents Käyttäjän hiiren tapahtumat.
     * @return GameMotor instanssi.
     */
    public static GameMotor getInstance(HashMap<KeyCode, Boolean> input, ArrayList<MouseEvent> mouseEvents, HashMap<MouseButton, Boolean> mouseClicks) {
        if (gameMotor == null) {
            gameMotor = new GameMotor(input, mouseEvents, mouseClicks);
        }
        return gameMotor;
    }

    /**
     * Päivittäää pelin tilan.
     */
    public void update() {

        // if game is paused don't update game's state
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

        if (mouseClicks.getOrDefault(MouseButton.PRIMARY, Boolean.FALSE && bullets.size() < 1)) {
            Bullet bullet = new Bullet(player);
            bullets.add(bullet);
            bullet.shoot();

            Ui.render(bullet.getShape());

        }

        if (mouseEvents.size() > 0) {
            player.rotateTowards(mouseEvents.get(mouseEvents.size() - 1).getSceneX(), mouseEvents.get(mouseEvents.size() - 1).getSceneY());
        }

        bullets.forEach(bullet -> bullet.update());
        player.update();

    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Pysäyttää pelin eli updaten käsittelyn. Eli running = false.
     */
    public void pause() {
        this.running = false;
    }

    /**
     * Jatkaa peliä eli palauttaa updaten käsittelyn. Eli running = true.
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
        this.player.rotateTowards(WIDTH / 2, 0);
        this.score = 0;
        this.bullets = new ArrayList<>();
        this.resume();

    }

}

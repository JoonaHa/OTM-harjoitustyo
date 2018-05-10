package pitchblack.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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
    private final HashMap<Integer, MouseEvent> mouseMovements;
    private Player player;
    private List<Sprite> enemies;
    private Boolean running;
    private int score;
    private long spawnTime;
    private List<Bullet> bullets;

    /**
     *
     *
     */
    private GameMotor(HashMap<KeyCode, Boolean> input, HashMap<Integer, MouseEvent> mouseMovements, HashMap<MouseButton, Boolean> mouseClicks) {
        this.input = input;
        this.mouseClicks = mouseClicks;
        this.mouseMovements = mouseMovements;
        this.player = new Player(WIDTH / 2, HEIGHT / 2);
        this.player.rotateTowards(WIDTH / 2, 0);
        this.enemies = new ArrayList<>();
        this.running = true;
        this.score = 0;
        this.spawnTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
        this.bullets = new ArrayList<>();

    }

    /**
     * Luokka palauttaa GameMotor instanssin.
     *
     * @param input Käyttäjän näppäimistö painallukset.
     * @param mouseMovements Käyttäjän hiiren tapahtumat.
     * @return GameMotor instanssi.
     */
    public static GameMotor getInstance(HashMap<KeyCode, Boolean> input, HashMap<Integer, MouseEvent> mouseMovements, HashMap<MouseButton, Boolean> mouseClicks) {
        if (gameMotor == null) {
            gameMotor = new GameMotor(input, mouseMovements, mouseClicks);
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

        if (mouseClicks.getOrDefault(MouseButton.PRIMARY, Boolean.FALSE) && bullets.size() < 1) {
            Bullet bullet = new Bullet(player);
            bullets.add(bullet);
            bullet.shoot();

            Ui.addNodeToGame(bullet.getShape());

        }

        if (mouseMovements.size() > 0) {
            MouseEvent event = mouseMovements.get(1);
            player.rotateTowards(event.getSceneX(), event.getSceneY());
        }

        long compTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

        if (compTime - this.spawnTime >= 6 && enemies.size() < 1) {
            spawnEnemies();
            this.spawnTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        bullets.forEach(bullet -> {
            enemies.stream().filter(enemy -> enemy.intersects(bullet.getShape())).forEach(enemy -> {
                this.score++;
                bullet.setAlife(false);
                enemy.setAlife(false);
            });
        });

        enemies.stream().filter(enemy -> !enemy.isAlife())
                .forEach(enemy -> Ui.removeNodeFromGame(enemy.getShape()));

        enemies.removeAll(enemies.stream()
                .filter(enemy -> !enemy.isAlife())
                .collect(Collectors.toList()));

        bullets.stream().filter(bullet -> !bullet.isAlife())
                .forEach(bullet -> Ui.removeNodeFromGame(bullet.getShape()));

        bullets.removeAll(bullets.stream()
                .filter(bullet -> !bullet.isAlife())
                .collect(Collectors.toList()));

        enemies.stream().forEach(enemy -> {

            if (enemy.intersects(getPlayer().getShape())) {
                getPlayer().setAlife(false);
            }
        });

        bullets.forEach(bullet -> bullet.update());
        enemies.forEach(enemy -> enemy.patrol(getPlayer()));
        enemies.forEach(enemy -> enemy.update());
        player.update();

    }

    private void spawnEnemies() {

        int scale;
        scale = (int) Math.ceil(score * 0.2);
        int enemy1Count = 2 + scale;
        int enemy2Count = 1 + scale;

        for (int i = 0; i < enemy1Count; i++) {

            // spawn enemy offscreen but still on their acceptable locations
            double xSpawnArea = Ui.WIDTH + Enemy1.borderOffset;
            double y = Ui.HEIGHT + Enemy1.borderOffset;

            double x = xSpawnArea * new Random().nextDouble();

            Enemy1 enemy1 = new Enemy1(x, y);

            this.enemies.add(enemy1);
            Ui.addNodeToGame(enemy1.getShape());

        }

        for (int i = 0; i < enemy2Count; i++) {

            // spawn enemy offscreen but still in to their acceptable locations
            double x = 0 - Enemy2.borderOffset;
            double ySpawnArea = Ui.HEIGHT + Enemy2.borderOffset;

            double y = ySpawnArea * new Random().nextDouble();

            Enemy2 enemy2 = new Enemy2(x, y);

            this.enemies.add(enemy2);
            Ui.addNodeToGame(enemy2.getShape());

        }

    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    private List<Bullet> getBullets() {
        return bullets;
    }

    public Boolean isRunning() {
        return running;
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
        Ui.addNodeToGame(this.player.getShape());
        this.enemies = new ArrayList<>();
        this.running = true;
        this.score = 0;
        this.bullets = new ArrayList<>();
        this.resume();
        spawnEnemies();
        this.pause();

    }

    public void quit() {

        Ui.removeNodeFromGame(getPlayer().getShape());

        enemies.forEach(enemy -> Ui.removeNodeFromGame(enemy.getShape()));

        bullets.forEach(bullet -> Ui.removeNodeFromGame(bullet.getShape()));
        this.pause();
    }

}

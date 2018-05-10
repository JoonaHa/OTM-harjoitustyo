/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author JoonaHa
 */
public class ScoreMeter {
    private Text score;
    private Text oldScore;

    public ScoreMeter() {
        this.score = new Text(Ui.WIDTH -100, Ui.HEIGHT-20, "SCORE: 0");
        this.score.setFill(Color.RED);
        this.score.setFont(Font.font(18));
        
    }

    public Text getOld() {
        return oldScore;
    }
    
    private void updateOld() {
        this.oldScore = this.score;
    }

   
    
    
    public Text update(int score) {
        updateOld();
        this.score.setText("SCORE: " + score);
        return this.score;
    }
    
    
    
    
}

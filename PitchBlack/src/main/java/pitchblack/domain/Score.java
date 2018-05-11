/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

/**
 * Luokka pelaajien pistetuloksille.
 *
 * @author JoonaHa
 *
 *
 *
 */
public class Score implements Comparable<Score> {

    private String nickname;
    private Integer score;

    /**
     * Luo uuden pistetuloksen.
     *
     * @param nickname Pelaajan lempinimi.
     * @param score Pelaajan pisteet
     */
    public Score(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score other) {
        if (this.score < other.getScore()) {
            return -1;
        }
        if (this.score > other.getScore()) {
            return 1;
        }

        return 0;
    }

}

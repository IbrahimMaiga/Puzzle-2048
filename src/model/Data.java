package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ibrahim Maïga.
 */
public class Data implements Serializable{

    private PlatformConfig config;
    private ArrayList<Cell> cells;
    private Score currentScore;
    private Score bestScore;

    public Data(PlatformConfig config, ArrayList<Cell> cells, Score currentScore, Score bestScore){
        this.config = config;
        this.cells = cells;
        this.currentScore = currentScore;
        this.bestScore = bestScore;
    }

    public PlatformConfig getConfig() {
        return config;
    }

    public void setConfig(PlatformConfig config) {
        this.config = config;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public Score getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Score currentScore) {
        this.currentScore = currentScore;
    }

    public Score getBestScore() {
        return bestScore;
    }

    public void setBestScore(Score bestScore) {
        this.bestScore = bestScore;
    }
}

package ml.kanfa.engine;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ibrahim Maïga.
 */
public class Data implements Serializable{

    private static final long serialVersionUID = 1L;

    private PlatformConfig config;
    private ArrayList<Cell> cells;
    private Score currentScore;
    private Score bestScore;
    private boolean win;

    public Data(PlatformConfig config, ArrayList<Cell> cells, Score currentScore, Score bestScore){
        this.config = config;
        this.cells = cells;
        this.currentScore = currentScore;
        this.bestScore = bestScore;
        this.win = false;
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

    public boolean isWin(){
        return this.win;
    }

    public void setWin(boolean win){
        this.win = win;
    }
}

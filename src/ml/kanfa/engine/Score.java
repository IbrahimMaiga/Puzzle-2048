package ml.kanfa.engine;

import java.io.Serializable;

/**
 * @uthor Ibrahim Maïga.
 */

public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private int value;
    private int current;
    private int old;

    public Score(){}

    public void addValue(int value){
        this.old += value;
        this.current = this.old;
        this.value += value;
    }

    public int getCurrent(){
        this.old = 0;
        return current;
    }

    public void setCurrent(int current){
        this.current = current;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public boolean greaterThan(Score otherScore) {
        return this.getValue() > otherScore.getValue();
    }
}

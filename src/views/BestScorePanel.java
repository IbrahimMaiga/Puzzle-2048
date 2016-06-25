package views;

import model.Model;

import java.awt.*;

/**
 * @uthor Kanfa.
 */
public class BestScorePanel extends ScorePanel{

    public BestScorePanel(Model model, Color background, Color fontColor) {
        super(model, background, fontColor);
    }

    @Override public String getObserverName() {
        return "obs_best";
    }

    @Override public boolean run() {
        return false;
    }
}

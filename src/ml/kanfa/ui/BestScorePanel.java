package ml.kanfa.ui;

import ml.kanfa.engine.Model;

import java.awt.*;

/**
 * @uthor Ibrahim Maïga.
 */
public class BestScorePanel extends ScorePanel{

    public BestScorePanel(Model model, Color background, Color fontColor) {
        super(model, background, fontColor);
    }

    @Override public String getObserverName() {
        return OBS_BEST;
    }

    @Override public boolean run() {
        return false;
    }

    @Override public String getTitle() {
        return "BEST";
    }
}

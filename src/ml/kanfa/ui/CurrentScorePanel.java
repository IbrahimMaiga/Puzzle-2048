package ml.kanfa.ui;

import ml.kanfa.engine.Animated;
import ml.kanfa.engine.Model;

import java.awt.*;

/**
 * @uthor Ibrahim Maïga.
 */
public class CurrentScorePanel extends ScorePanel implements Animated{

    public CurrentScorePanel(Model model, Color background, Color fontColor) {
        super(model, background, fontColor);
    }

    @Override public String getObserverName() {
        return OBS_CURRENT;
    }

    @Override public boolean run() {
        return true;
    }

    @Override public String getTitle() {
        return "SCORE";
    }
}

package ml.kanfa.views;

import ml.kanfa.model.Animated;
import ml.kanfa.model.Model;

import java.awt.*;

/**
 * @uthor Kanfa.
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

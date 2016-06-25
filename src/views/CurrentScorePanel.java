package views;

import model.Animated;
import model.Model;

import java.awt.*;

/**
 * @uthor Kanfa.
 */
public class CurrentScorePanel extends ScorePanel implements Animated{

    public CurrentScorePanel(Model model, Color background, Color fontColor) {
        super(model, background, fontColor);
    }

    @Override public String getObserverName() {
        return "obs_current";
    }

    @Override public boolean run() {
        return true;
    }
}

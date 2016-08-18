package ml.kanfa.engine;

import java.awt.*;

/**
 * @uthor Ibrahim Maïga.
 */
public class Config_4x4 extends PlatformConfig{

    @Override public int getSide() {
        return 4;
    }

    @Override public int getBlocLength() {
        return 98;
    }

    @Override public Color getBackground() {
        return new Color(84, 98, 101);
    }

    @Override public String getConfigName() {
        return "4x4";
    }
}

package model;

import java.awt.*;

/**
 * @uthor Kanfa.
 */

public class Config_5x5 extends PlatformConfig{

    @Override public int getSide() {
        return 5;
    }

    @Override public int getBlocLength() {
        return 78;
    }

    @Override public Color getBackground() {
        return Color.WHITE;
    }

    @Override public String getConfigName() {
        return "5x5";
    }
}

package ml.kanfa.engine;

import java.awt.*;
import java.io.Serializable;

/**
 * @uthor Ibrahim Maïga.
 */
public abstract class PlatformConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlatformConfig(){}

    public abstract int getSide();
    public abstract int getBlocLength();
    public abstract Color getBackground();
    public abstract String getConfigName();
}

package ml.kanfa.model;

import java.awt.*;
import java.io.Serializable;

/**
 * @uthor Kanfa.
 */
public abstract class PlatformConfig implements Serializable {

    public PlatformConfig(){}

    public abstract int getSide();
    public abstract int getBlocLength();
    public abstract Color getBackground();
    public abstract String getConfigName();
}

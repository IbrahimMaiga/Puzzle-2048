package ml.kanfa.engine;

import java.awt.*;

/**
 * @uthor Ibrahim Maïga.
 */

public class CellConfig_4x4 extends CellConfig{

    @Override public Color getBackground(int value) {
        switch (value){
            case 2:
                return new Color(210, 201, 148);
            case 4:
                return new Color(205, 196, 143);
            case 8:
                return new Color(241, 100, 58);
            case 16:
                return new Color(255, 100, 95);
            case 32:
                return new Color(252, 104, 60);
            case 64:
                return new Color(241, 56, 48);
            case 128:
                return new Color(255, 214, 15);
            case 256:
                return new Color(245, 205, 15);
            case 512:
                return new Color(235, 195, 15);
            case 1024:
                return new Color(225, 187, 15);
            case 2048:
                return new Color(205, 169, 15);
            case 4096:
                return new Color(255, 98, 6);
            case 8192:
                return new Color(205, 86, 6);
            case 16384:
                return new Color(205, 51, 11);
            case 32768:
                return new Color(136, 160, 255);
            case 65536:
                return new Color(112, 134, 218);
            case 131072:
                return new Color(88, 99, 176);
            default: return new Color(84, 98, 101);
        }
    }

}

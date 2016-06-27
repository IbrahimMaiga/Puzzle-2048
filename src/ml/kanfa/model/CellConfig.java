package ml.kanfa.model;

import java.awt.*;
import java.io.Serializable;

/**
 * @uthor Kanfa.
 */
public abstract class CellConfig implements Serializable{

    public abstract Color getBackground(int value);

    public Color getForeground(int value){
        switch (value){
            case 2:
            case 4:
                return Color.BLACK;
            case 8:
            case 16:
            case 32:
            case 64:
            case 128:
            case 256:
            case 512:
            case 1024:
            case 2048:
            case 4096:
            case 8192:
            case 16384:
            case 32768:
            case 65536:
            case 131072:
                return Color.WHITE;
            default: return null;
        }
    }

    public Font getFont(int value){
        return new Font("Verdana", Font.PLAIN, 30 - (String.valueOf(value).length() * 2));
    }
}

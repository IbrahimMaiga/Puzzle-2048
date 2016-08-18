package ml.kanfa.engine;

import java.awt.*;
import java.io.Serializable;

/**
 * @uthor Ibrahim Maïga.
 */

public class Cell implements Serializable, Cloneable{

    private int posx;
    private int posy;
    private int value;
    private CellConfig config;
    private static final Color defaultBackground = new Color(195, 201, 206);

    public Cell(){
        this(new CellConfig_4x4(), 0, 0, 0);
    }

    public Cell(CellConfig config, int value, int posx, int posy){
        this.value = value;
        this.posx = posx;
        this.posy = posy;
        this.config = config;
    }

    public Cell(int value, int posx, int posy){
        this(new CellConfig_4x4(), value, posx, posy);
    }

    public Cell(int posx, int posy){
        this(0, posx, posy);
    }

    public void add(Cell cell){
        this.value += cell.getValue();
        cell.setValue(0);
    }

    public Cell clone() throws CloneNotSupportedException {
        CellConfig cellConfig = this.config.clone();
        Cell cell = (Cell)super.clone();
        cell.setConfig(cellConfig);
        return cell;
    }

    public void set(int posx, int posy){
        this.setPosX(posx);
        this.setPosY(posy);
    }

    public boolean same(Cell cell){
        return (!this.isEmpty() && !cell.isEmpty()) && (this.value == cell.getValue());
    }

    public Color getBackground(){
        return this.isEmpty() ? defaultBackground : this.config.getBackground(this.value);
    }

    public Color getForeground(){
        return this.config.getForeground(this.value);
    }

    public Font getFont(){
        return this.config.getFont(this.value);
    }


    public CellConfig getConfig() {
        return config;
    }

    public void setConfig(CellConfig config) {
        this.config = config;
    }

    public boolean isEmpty(){
        return this.value == 0;
    }

    public int getPosX(){
        return this.posx;
    }

    public void setPosX(int posx){
        this.posx = posx;
    }

    public int getPosY(){
        return this.posy;
    }

    public void setPosY(int posy){
        this.posy = posy;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }
}

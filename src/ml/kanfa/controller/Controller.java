package ml.kanfa.controller;

import ml.kanfa.model.Cell;
import ml.kanfa.model.CellUtils;
import ml.kanfa.model.Direction;
import ml.kanfa.model.Model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @uthor Kanfa.
 */

public class Controller {

    private Model model;
    private Direction[] directions;
    public Controller(Model model){
        this.model = model;
        this.directions = Direction.values();
    }

    public void control(int keyCode){
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT){
            if (this.isValid(keyCode)) this.model.move(getDirection(keyCode));
            else{
                if (isFill(this.model.getCells())) this.model.notifyGameOver();
            }
        }
    }

    private boolean isFill(ArrayList<Cell> cells){
        boolean complete = true;

        for (Cell cell : cells){
            if (cell.isEmpty()){
                complete = false;
                break;
            }
        }

        boolean filled = true;

        for (Direction direction : this.directions){
            if (this.isValid(this.getKeyCode(direction))){
                filled = false;
                break;
            }
        }

        return filled && complete;
    }

    private boolean isValid(int keyCode){
        int d = (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_UP) ? 1 :
                (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_DOWN) ? -1 : 0;
        ArrayList<ArrayList<Cell>> formatedCells = (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) ?
                CellUtils.toArray(this.model.getCells()) :
                                                    (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) ?
                                                            CellUtils.reverseArray(this.model.getCells()) : new ArrayList<>();

        int beginIndex = (d == 1) ? 0 : (d == -1) ? formatedCells.size() - 1 : -1;

        for (ArrayList<Cell> cells : formatedCells){
            int index = CellUtils.searchEmpty(cells, d, beginIndex);
            if (index != -1){
                for (int i = index + d; CellUtils.iterate(cells.size(), i, d); i += d){
                    if (!cells.get(i).isEmpty()) return true;
                }
            }
        }

        for (ArrayList<Cell> cells : formatedCells){
            for (int i = 0; i < cells.size() - 1; i++){
                if (cells.get(i).same(cells.get(i + 1))){
                    return true;
                }
            }
        }

        return false;
    }

    public Direction getDirection(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_UP:
                return Direction.UP;
            case KeyEvent.VK_DOWN:
                return Direction.DOWN;
            case KeyEvent.VK_LEFT:
                return Direction.LEFT;
            case KeyEvent.VK_RIGHT:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    public int getKeyCode(Direction direction){
        if (direction.equals(Direction.UP)) return KeyEvent.VK_UP;
        else if (direction.equals(Direction.DOWN)) return KeyEvent.VK_DOWN;
        else if (direction.equals(Direction.LEFT)) return KeyEvent.VK_LEFT;
        else if (direction.equals(Direction.RIGHT)) return KeyEvent.VK_RIGHT;
        else return -1;
    }
}

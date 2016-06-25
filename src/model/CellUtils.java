package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @uthor Kanfa.
 */
public class CellUtils {


    private CellUtils(){}

    public static ArrayList<ArrayList<Cell>> toArray(ArrayList<Cell> cells){
        ArrayList<ArrayList<Cell>> arrays = new ArrayList<>();
        ArrayList<Cell> array = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            array.add(cells.get(i));
            if ((i+1) % (int)Math.sqrt(cells.size()) == 0){
                arrays.add(array);
                array = new ArrayList<>();
            }
        }
        return arrays;
    }

    public static ArrayList<ArrayList<Cell>> reverseArray(ArrayList<Cell> cells){
        ArrayList<ArrayList<Cell>> arrays = new ArrayList<>();
        int side = (int)Math.sqrt(cells.size());
        for (int i = 0; i < side; i ++){
            ArrayList<Cell> array = new ArrayList<>();
            for (int j = 0; j < side; j++){
                array.add(cells.get(i + (side * j)));
            }
            arrays.add(array);
        }
        return arrays;
    }

    public static boolean iterate(int length, int i, int d){
        return iterate(length, 0, i, d);
    }

    public static boolean iterate(int length, int b, int i, int d){
        return d == 1 ? i < length : d == -1 ? i >= b : false;
    }

    public static final int searchEmpty(ArrayList<Cell> cell, int d, int index){
        int emptyIndex = -1;
        for (int i = index; iterate(cell.size(), i, d); i += d){
            if (cell.get(i).isEmpty()){
                emptyIndex = i;
                break;
            }
        }
        return emptyIndex;
    }

    public static Set<Integer> searchAllEmpty(ArrayList<Cell> cells){
        Set<Integer> set = new HashSet<>();
        int index = 0;
        for (Cell cell :cells){
            if (cell.isEmpty()) {
                set.add(index);
            }
            index++;
        }
        return set;
    }

    public static Set<Integer> searchAll(ArrayList<Cell> cells){
        Set<Integer> set = new HashSet<>();
        int index = 0;
        for (Cell cell :cells){
            if (!cell.isEmpty()) {
                set.add(index);
            }
            index++;
        }
        return set;
    }

    public static final int searchIndex(ArrayList<Cell> cell, int d, int index) {
        int a = -1;
        for (int i = index; iterate(cell.size(), i, d); i += d){
            if (!cell.get(i).isEmpty()){
                a = i;
                break;
            }
        }
        return a;
    }
}

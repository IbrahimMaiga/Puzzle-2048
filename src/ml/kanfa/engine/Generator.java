package ml.kanfa.engine;

import java.util.*;

/**
 * @uthor Ibrahim Maïga.
 */

public class Generator {

    private static List<Integer> list = new ArrayList<>();

    static {
        list.add(1 << 1);
        list.add(1 << list.get(0));
    }

    public static int generate(){
        Random random = new Random();
        int index = random.nextInt(list.get(0));
        return list.get(index);
    }

}

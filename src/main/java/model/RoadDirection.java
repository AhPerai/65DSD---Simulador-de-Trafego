package model;

import java.util.HashMap;
import java.util.Map;

/*
*Classe para mapear os tipos de blocos/estradas que compõem a malha 
 */
public enum RoadDirection {

    BASE(0, "/images/tilebase.png"),
    UP(1, "/images/tileup.png"),
    RIGHT(2, "/images/tileright.png"),
    DOWN(3, "/images/tiledown.png"),
    LEFT(4, "/images/tileleft.png"),
    CROSS_UP(5, "/images/tilecross.png"),
    CROSS_RIGHT(6, "/images/tilecross.png"),
    CROSS_DOWN(7, "/images/tilecross.png"),
    CROSS_LEFT(8, "/images/tilecross.png"),
    CROSS_UP_RIGHT(9, "/images/tilecross.png"),
    CROSS_UP_LEFT(10, "/images/tilecross.png"),
    CROSS_DOWN_RIGHT(11, "/images/tilecross.png"),
    CROSS_DOWN_LEFT(12, "/images/tilecross.png");

    private int number;
    private String path;
    private static Map directionsMap = new HashMap<Integer, String>();

    private RoadDirection(int number, String path) {
        this.number = number;
        this.path = path;
    }

    public static void toMap() {
        for (RoadDirection direction : RoadDirection.values()) {
            directionsMap.put(direction.number, direction.path);
        }
    }

    public static String getDirectionPath(int number) {
        return (String) directionsMap.get(number);
    }

    public static boolean isCross(int number) {
        if (number > 4) {
            return true;
        }
        return false;
    }

    public int getNumber() {
        return number;
    }

}

package model;

/*
*Classe para mapear os tipos de blocos/estradas que compõem a malha 
 */
public enum RoadDirection {

    BASE(0, "/images/TileBase.png"),
    UP(1, "images/TileUp.png"),
    RIGHT(2, "images/TileRight.png"),
    DOWN(3, "images/TileDown.png"),
    LEFT(4, "images/TileLeft.png"),
    FIVE(5, "images/TileCross.png"),
    SIX(6, "images/TileCross.png"),
    SEVEN(7, "images/TileCross.png"),
    EIGHT(8, "images/TileCross.png"),
    NINE(9, "images/TileCross.png"),
    TEN(10, "images/TileCross.png"),
    ELEVEN(11, "images/TileCross.png"),
    TWELVE(12, "images/TileCross.png");

    private int number;
    private String path;

    private RoadDirection(int number, String path) {
        this.number = number;
        this.path = path;
    }

    public static String getDirectionPath(int number) {
        for (RoadDirection direction : RoadDirection.values()) {
            if (number == direction.number) {
                return direction.path;
            }
        }
        return null;
    }
    
    public static boolean isCross(int number){
        if(number > 4){
            return true;
        }
        return false;
    }

    public int getNumber() {
        return number;
    }
    
}

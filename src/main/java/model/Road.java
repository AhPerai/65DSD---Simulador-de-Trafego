package model;

import java.util.ArrayList;
import java.util.List;

public class Road {
    //uma matriz de linhas e colunas; que pode conter carros; ou espaços vazios;
    // a road vai ter as entradas;saidas;cruzamentos;carros;
    private RoadDirection roadDirection;

    private RoadCross roadCross;

    static List<Car> threadList = new ArrayList<>();

    private int[][] road;
    private int line;
    private int collumn;

    public Road(int[][]road, int line,int column){
        this.road = road;
        this.line = line;
        this.collumn = column;

    }

}

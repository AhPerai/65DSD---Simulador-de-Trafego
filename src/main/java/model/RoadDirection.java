package model;

public class RoadDirection {

    // apenas no sentido horizontal e vertical;
    private RoadDirection roadUp;//1
    private RoadDirection roadRight;//2
    private RoadDirection roadDown;//3
    private RoadDirection roadLeft;//4




    private PositionRoad positionRoad;


    //private boolean basy = false;
    private boolean entrance;// pega o flow e a posicao,
    private boolean exite;// if o flow eh para roadRight and eh a ultima posicao da Road
    // lastLine with flow

    // as entradas
    /*
    qtdEntradasDireita;qtd de 2; Case1:1; case2:3; case3:2;
    qtdEntradasEsquerda;
    qtdEntradasCima;qtd de 2; Case1:1; case2:3; case3:2;
    qtdEntradasBaixo;
    qtdCross:
    entraceRight; para ser uma entrada right precisa estah na columnposition 0 && DirectionFlow == 2;
    entranceLeft; para ser uma entrada lefth precisa estah na columnposition ultima && DirectionFlow == 4;
    entranceDown; para ser uma entrada down precisa estah na linePosition && DirectionFlow == 3;
    entranceUp; para ser uma entrada up precisa estah na ultima linePosition && DirectionFlow == 1;
     */




    // as saidas



}

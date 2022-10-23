package model;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class Car extends Thread{

    //cada carro tem uma velocidade;
    private  int velocity;//random sleep;
    // o carro deve ter uma Malha;

    //private Road road;

    private RoadDirection roadAtual;
    private RoadDirection roadAnt;

    private Iterator<RoadDirection> route;

    private int idCar;
   // private int typeCar;
    private int nextDirectionFlow;
    private Block currentBlockRoad;
    private Block oldBlockRoad;

    public Car(int carId, Block blockCurrent,int velocity){
        this.idCar = carId;
        this.velocity = velocity;
        this.currentBlockRoad = blockCurrent;
        this.nextDirectionFlow = 0;
    }
    public int getNextDirectionFlow() {
        return nextDirectionFlow;
    }

    public void setNextDirectionFlow(int nextDirection) {

        this.nextDirectionFlow = nextDirection;
    }

    public Block getOldBlockRoad() {
        return oldBlockRoad;
    }

    public void setOldBlockRoad(Block oldRoad) {
        this.oldBlockRoad = oldRoad;
    }

    public Block getCurrentBlockRoad() {
        return currentBlockRoad;
    }

    public void setCurrentBlockRoad(Block currentRoad) {
        this.currentBlockRoad = currentRoad;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int carId) {
        this.idCar = carId;
    }

    public int getVelocity(int velocity){
        return this.velocity;
    }

    public int velocityDivergente(){
        Random rand = new Random();

        return rand.nextInt();
    }

    //private String idCar;//getid - thread;


    private String directionFlow;//

    // a posicao pensando na matriz:
    private int linePosition;
    private int columnPosition;

    //private boolean runRoad(int linePosition,int columnPosition){
    //    if()
    //}





    // Movement correto se -> seguir directionFlow e se a position a frente estiver vazia;
}

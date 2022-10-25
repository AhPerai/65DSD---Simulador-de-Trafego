package model;

import controller.Controller;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class Car extends Thread {

    private int velocity;//random sleep;
    private RoadDirection roadAtual;
    private RoadDirection roadAnt;
    private Iterator<RoadDirection> route;
    private int idCar;

    private int nextDirectionFlow;
    private int currentRow;
    private int currentCol;
    private boolean out;

    private Block currentBlockRoad;

    public Car(Controller controller) {
        controller = Controller.getInstance();
        seRandomVelocity();
    }

    public int getNextDirectionFlow() {
        return nextDirectionFlow;
    }

    public void setNextDirectionFlow(int nextDirection) {

        this.nextDirectionFlow = nextDirection;
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

    public int getVelocity(int velocity) {
        return this.velocity;
    }

    public void seRandomVelocity() {
        Random r = new Random();
        this.velocity = (int) (1 + r.nextFloat() * (10 - 1)*100);
    }

    //private String idCar;//getid - thread;
    private String directionFlow;//

    //private boolean runRoad(int linePosition,int columnPosition){
    //    if()
    //}
    // Movement correto se -> seguir directionFlow e se a position a frente estiver vazia;
}

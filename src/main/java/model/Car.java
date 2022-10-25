package model;

import controller.Controller;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Car extends Thread {

    private int velocity;//random sleep;
    private Iterator<RoadDirection> route;
    private int idCar;

    private int nextDirectionFlow;
    private RoadMutex currentBlock;
    private Controller controller;
    private boolean out;

    public Car(Controller controller) {
        this.controller = Controller.getInstance();
        seRandomVelocity();
    }

    public void run() {
        while (!out) {
            try {
                Thread.currentThread().sleep(velocity);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
            if (currentBlock.getNextBlock().isExit()) {
                //Executa lógica de ser saída
            } else if (currentBlock.getNextBlock().isCross()) {
                //Realiza lógica de cruzamento
            } else {
                //Movimentação normal
            }

        }
    }

    private void move() {
        RoadMutex next = (RoadMutex) currentBlock.getNextBlock();
        try {
            next.getSemaphore().acquire();
            next.setCar(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer[][] positions = {
            {currentBlock.getLinePosition(), currentBlock.getColumnPosition()},
            {next.getLinePosition(), next.getColumnPosition()}
        };
        controller.notifyMovement(positions);
        currentBlock.getSemaphore().release();
        currentBlock.setCar(null);
        currentBlock = next;
    }

    private void moveCrossing() {

    }

    private void moveOut() {

    }

    public void enterRoad(RoadMutex entrance) {

        try {
            entrance.getSemaphore().acquire();
            entrance.setCar(this);
            this.setCurrentBlockRoad(entrance);
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getNextDirectionFlow() {
        return nextDirectionFlow;
    }

    public void setNextDirectionFlow(int nextDirection) {

        this.nextDirectionFlow = nextDirection;
    }

    public Block getCurrentBlockRoad() {
        return currentBlock;
    }

    public void setCurrentBlockRoad(RoadMutex currentRoad) {
        this.currentBlock = currentRoad;
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
        this.velocity = (int) (1 + r.nextFloat() * (10 - 1) * 100);
    }

}

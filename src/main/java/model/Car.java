package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Car extends Thread {

    private int velocity;
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
            if (currentBlock.isExit()) {
                moveOut();
            } else if (currentBlock.getNextBlock().isCross()) {
                moveCrossing();
            } else {
                move();
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
        List<RoadMutex> acquiredPath = mapCrossOuts();
        if (acquiredPath != null) {

            for (int i = 0; i < acquiredPath.size(); i++) {
                RoadMutex path = acquiredPath.get(i);

                Integer[][] positions = {
                    {currentBlock.getLinePosition(), currentBlock.getColumnPosition()},
                    {path.getLinePosition(), path.getColumnPosition()}
                };
                controller.notifyMovement(positions);
                currentBlock.getSemaphore().release();
                currentBlock.setCar(null);
                currentBlock = path;

                if (i < acquiredPath.size() - 1) {
                    try {
                        Thread.currentThread().sleep(velocity);
                    } catch (InterruptedException e) {
                        e.getStackTrace();
                    }
                }
            }
        }

    }

    private void moveOut() {
        out = true;
        Integer[][] positions = {
            {currentBlock.getLinePosition(), currentBlock.getColumnPosition()},
            {null, null}
        };
        controller.notifyMovement(positions);
        currentBlock.getSemaphore().release();
        currentBlock.setCar(null);

        controller.getCarList().remove(this);
        controller.notifyThreadCounter();
        this.interrupt();
    }

    private List<RoadMutex> mapCrossOuts() {
        Map<Integer, RoadMutex> crossOptions = new HashMap<>();
        int i = currentBlock.getLinePosition();
        int j = currentBlock.getColumnPosition();

        switch (currentBlock.getDirectionFlow()) {
            case 1:
                crossOptions.put(0, controller.getMatrixRoad().getBlock(i - 1, j));
                crossOptions.put(1, controller.getMatrixRoad().getBlock(i - 2, j));
                crossOptions.put(2, controller.getMatrixRoad().getBlock(i - 2, j - 1));
                crossOptions.put(3, controller.getMatrixRoad().getBlock(i - 1, j - 1));

                break;
            case 2:
                crossOptions.put(0, controller.getMatrixRoad().getBlock(i, j + 1));
                crossOptions.put(1, controller.getMatrixRoad().getBlock(i, j + 2));
                crossOptions.put(2, controller.getMatrixRoad().getBlock(i - 1, j + 2));
                crossOptions.put(3, controller.getMatrixRoad().getBlock(i - 1, j + 1));
                break;
            case 3:
                crossOptions.put(0, controller.getMatrixRoad().getBlock(i + 1, j));
                crossOptions.put(1, controller.getMatrixRoad().getBlock(i + 2, j));
                crossOptions.put(2, controller.getMatrixRoad().getBlock(i + 2, j + 1));
                crossOptions.put(3, controller.getMatrixRoad().getBlock(i + 1, j + 1));
                break;
            case 4:
                crossOptions.put(0, controller.getMatrixRoad().getBlock(i, j - 1));
                crossOptions.put(1, controller.getMatrixRoad().getBlock(i, j - 2));
                crossOptions.put(2, controller.getMatrixRoad().getBlock(i + 1, j - 2));
                crossOptions.put(3, controller.getMatrixRoad().getBlock(i + 1, j - 1));
                break;
            default:
        }

        List<RoadMutex> outOptions = new ArrayList<>();
        for (RoadMutex crossOption : crossOptions.values()) {
            switch (crossOption.getDirectionFlow()) {
                case 9:
                    RoadMutex r = controller.getMatrixRoad().getBlock(crossOption.getLinePosition(), crossOption.getColumnPosition() + 1);
                    if (r.getDirectionFlow() == 2) {
                        outOptions.add(r);
                    }
                    break;
                case 10:
                    RoadMutex u = controller.getMatrixRoad().getBlock(crossOption.getLinePosition() - 1, crossOption.getColumnPosition());
                    if (u.getDirectionFlow() == 1) {
                        outOptions.add(u);
                    }
                    break;
                case 11:
                    RoadMutex d = controller.getMatrixRoad().getBlock(crossOption.getLinePosition() + 1, crossOption.getColumnPosition());
                    if (d.getDirectionFlow() == 3) {
                        outOptions.add(d);
                    }
                    break;
                case 12:
                    RoadMutex l = controller.getMatrixRoad().getBlock(crossOption.getLinePosition(), crossOption.getColumnPosition() - 1);
                    if (l.getDirectionFlow() == 4) {
                        outOptions.add(l);
                    }
                    break;
                default:
            }
        }

        //Criando caminho
        RoadMutex chosen = outOptions.get(new Random().nextInt(outOptions.size()));
        List<RoadMutex> path = new ArrayList<>();
        boolean acquiredPath = chosen.getSemaphore().tryAcquire();
        System.out.println("Estou em: " + currentBlock.getDirectionFlow()
                + "\nQuero ir para: " + chosen.getDirectionFlow()
                + "\ne deu " + acquiredPath);
        if (acquiredPath) {
            chosen.setCar(this);
            if (currentBlock.getDirectionFlow() - chosen.getDirectionFlow() == 0) {
                for (int k = 0; k < 2; k++) {
                    path.add(crossOptions.get(k));
                }
            }
            if (Math.abs(currentBlock.getDirectionFlow() - chosen.getDirectionFlow()) == 2) {
                for (int k = 0; k < 4; k++) {
                    path.add(crossOptions.get(k));
                }
            }
            if (currentBlock.getDirectionFlow() - chosen.getDirectionFlow() == -1
                    || currentBlock.getDirectionFlow() - chosen.getDirectionFlow() == 3) {
                for (int k = 0; k < 1; k++) {
                    path.add(crossOptions.get(k));
                }
            }
            if (currentBlock.getDirectionFlow() - chosen.getDirectionFlow() == 1
                    || currentBlock.getDirectionFlow() - chosen.getDirectionFlow() == -3) {
                for (int k = 0; k < 3; k++) {
                    path.add(crossOptions.get(k));
                }
            }

            for (RoadMutex roadMutex : path) {
                boolean b = roadMutex.getSemaphore().tryAcquire();
                if (!b) {
                    return null;
                }
                roadMutex.setCar(this);
            }

            path.add(chosen);
            return path;
        }
        return null;
    }

    public boolean enterRoad(RoadMutex entrance) {
        boolean acquired = entrance.getSemaphore().tryAcquire();
        if (acquired) {
            System.out.println(acquired);
            entrance.setCar(this);
            this.setCurrentBlockRoad(entrance);
        }
        System.out.println(acquired);
        return acquired;
    }

    public Block getCurrentBlockRoad() {
        return currentBlock;
    }

    public void setCurrentBlockRoad(RoadMutex currentRoad) {
        this.currentBlock = currentRoad;
    }

    public int getVelocity(int velocity) {
        return this.velocity;
    }

    public void seRandomVelocity() {
        Random r = new Random();
        this.velocity = 1000/*(int) (1 + r.nextFloat() * (10 - 1) * 100) + 375*/;
    }

}

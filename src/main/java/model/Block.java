package model;

import java.util.ArrayList;

public abstract class Block {

    protected boolean cross;
    protected boolean entryCar;
    protected Block nextBlock;
    protected int directionFlow;
    protected Car car;

    protected int linePosition;

    protected int columnPosition;

    public Block(int directionFlow, int x, int y) {
        this.directionFlow = directionFlow;
        this.linePosition = x;
        this.columnPosition = y;
        this.nextBlock = null;
    }

    public abstract void addCar(Car car);

    public abstract void killCar();

    public int getDirectionFlow() {
        return directionFlow;
    }

    public void setDirectionFlow(int direction) {
        this.directionFlow = direction;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car; //necessário para informar que está com um carro no momento

    }

    public int getLinePosition() {
        return linePosition;
    }

    public void setLinePosition(int xPos) {
        this.linePosition = xPos;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int yPos) {
        this.columnPosition = yPos;
    }

    private boolean verifyCross() {
        return RoadDirection.isCross(this.directionFlow);
    }

    public boolean getCross() {
        return cross;
    }

    public void setCross(boolean cross) {
        this.cross = cross;
    }

    public boolean getEntryCar() {
        return entryCar;
    }

    public void setEntryCar(boolean entryCar) {
        this.entryCar = entryCar;
    }
    
    public Block getNextBlock(){
        return this.nextBlock;
    }

    public void setNextBlock(Block[][] road) {

        switch (this.directionFlow) {
            case 1:
                if (this.linePosition - 1 >= 0) {
                    this.nextBlock = road[this.linePosition - 1][this.columnPosition];
                }
                break;
            case 2:
                if (this.columnPosition + 1 < road[0].length) {
                    this.nextBlock = road[this.linePosition][this.columnPosition + 1];
                }
                break;
            case 3:
                if (this.linePosition + 1 < road.length) {
                    this.nextBlock = road[this.linePosition + 1][this.columnPosition];
                }
                break;
            case 4:
                if (this.columnPosition - 1 >= 0) {
                    this.nextBlock = road[this.linePosition][this.columnPosition - 1];
                }

        }

    }

}

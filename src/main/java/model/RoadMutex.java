package model;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoadMutex extends Block {

    private final Semaphore semaphore = new Semaphore(1);

    public RoadMutex(int direction, int xPos, int yPos) {
        super(direction, xPos, yPos);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}

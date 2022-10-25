package model;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void addCar(Car car) {
        while (true) {
            try {
                if (this.getCar() != null) {
                    Thread.sleep(300L);
                    continue;
                }
                this.semaphore.acquire();
                this.setCar(car);
            } catch (InterruptedException e) {
                Logger.getLogger(RoadMutex.class.getName()).log(Level.SEVERE, (String) null, e);
            } finally {
                this.semaphore.release();
            }
            return;
        }
    }

    @Override
    public void killCar() {
        try {
            this.semaphore.acquire();
            this.setCar((Car) null);
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        } finally {
            this.semaphore.release();
        }

    }
}

package controller.state;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Car;

/**
 *
 * @author Usuario
 */
public class Closing extends SimulationState {

    public Closing(Controller controller) {
        super(controller);
    }

    @Override
    public void execute() {
        controller.await();
        if (controller.getCarList().isEmpty()) {
            nextState();
        }
    }

    @Override
    public void nextState() {
        List<ThreadKiller> tList= new ArrayList<>();
        int segment = (int) Math.ceil(controller.getCarList().size() / 10);
        int remaining = controller.getCarList().size();

        for (int i = 0; i < segment; i++) {
            // Remaining used to find the upper index of each sublist
            // 10 is used instead of 9, because the second argument is exclusive (doesn't count last)
            int upperBound = remaining < 10 ? (remaining) : 10;
            List<Car> subList = controller.getCarList().subList((i * 10), (i * 10) + upperBound);
            //
            ThreadKiller t = new ThreadKiller(subList);
            t.start();
            tList.add(t);
            //
            remaining -= 10;
        }

        for (ThreadKiller t : tList) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Closing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        controller.getCarList().clear();
        controller.getMatrixRoad().generateMapFromFile(controller.getFilename());
        controller.setState(new Finished(controller));
        controller.notifyThreadCounter();
        controller.notifyReset();
        controller.notifyControllButton();
    }

    @Override
    public String getNextAction() {
        return "FINALIZAR";
    }
}

class ThreadKiller extends Thread {

    List<Car> cars;

    public ThreadKiller(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public void run() {
        for (Car car : cars) {
            car.setOut(true);
            car.interrupt();
        }
    }
}

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Car;
import model.RoadDirection;
import model.RoadMutex;
import utils.MatrixUtils;

/**
 *
 * @author Usuario
 */
public class Controller extends Thread {

    private final MatrixUtils roadInstance = MatrixUtils.getInstance();
    private static Controller instance;
    private List<Observer> roadObservers = new ArrayList<>();
    private String filename = "src/main/resources/casefiles/malha-exemplo-1.txt";

    //Singleton
    private Controller() {
        try {
            RoadDirection.toMap();
            this.roadInstance.generateMapFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addObserver(Observer obs) {
        this.roadObservers.add(obs);
    }

    public void removeObserver(Observer obs) {
        this.roadObservers.remove(obs);
    }

    public void notifyMovement(Integer[][] blockPositions) {
        for (Observer observer : roadObservers) {
            observer.updateCarPosition(blockPositions);
        }
    }

    public void notifyControllButton() {
        for (Observer observer : roadObservers) {
            observer.updateControllStatus(isStopped());
        }
    }

    public void notifyThreadCounter() {
        for (Observer observer : roadObservers) {
            observer.updateThreadCounter(carList.size());
        }
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public MatrixUtils getMatrixRoad() {
        return roadInstance;
    }

    private List<Car> carList = new ArrayList<>();
    private int qtdCar;
    private int await;
    private boolean stopped = true;

    public List<Car> getCarList() {
        return carList;
    }

    public void setQtdCar(int qtdCar) {
        this.qtdCar = qtdCar;
    }

    public void setAwait(int await) {
        this.await = await * 1000;
    }

    public void getAwait(int await) {
        this.await = await;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public void await() throws InterruptedException {
        sleep(await);
    }

    @Override
    public void run() {
        int i = 0;
        this.stopped = false;
        notifyControllButton();
        while (!stopped) {
            if (carList.size() < qtdCar) {
                Car newCar = new Car(this);
                RoadMutex entrance = roadInstance.getEntrances().get(i);
                Integer[][] positions = {{null, null}, {entrance.getLinePosition(), entrance.getColumnPosition()}};

                if (newCar.enterRoad(entrance)) {
                    carList.add(newCar);
                    notifyThreadCounter();
                    notifyMovement(positions);
                    newCar.start();
                } else {
                    System.out.println("Não deu, vamos para a entrada " + (i + 1));
                }

                i++;

                if (i == roadInstance.getEntrances().size()) {
                    i = 0;
                }
                try {
                    await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void finish() {
        this.stopped = true;
        notifyControllButton();
        this.interrupt();
    }

}

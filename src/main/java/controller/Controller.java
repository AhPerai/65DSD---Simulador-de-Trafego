package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Car;
import model.RoadDirection;
import utils.MatrixUtils;

/**
 *
 * @author Usuario
 */
public class Controller {

    private final MatrixUtils roadInstance = MatrixUtils.getInstance();
    private static Controller instance;
    private List<Observer> roadObservers = new ArrayList<>();
    private String filename = "src/main/resources/casefiles/malha-exemplo-3.txt";

    //Singleton
    private Controller() {
        try {
            RoadDirection.toMap();
            this.roadInstance.generateMapFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
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
        Thread.currentThread().sleep(await);
    }

    public void start() {
        for (int i = 0; i < qtdCar; i++) {
            if (stopped) {
                break;
            }
            Car newCar = new Car(this);
            try {
                Thread.currentThread().sleep(this.await);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

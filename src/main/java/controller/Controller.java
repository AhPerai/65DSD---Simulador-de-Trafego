package controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
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
    private Map<String, String> filePaths = new HashMap<String, String>();
    private String filename = "src/main/resources/casefiles/malha-exemplo-1.txt";

    private Controller() {
        RoadDirection.toMap();
        this.roadInstance.generateMapFromFile(filename);
        initMapFiles();
        this.start();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
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

    public void notifyInitFiles() {
        Set<String> keys = filePaths.keySet();
        for (Observer observer : roadObservers) {
            observer.initRoadFiles(keys.toArray(new String[keys.size()]));
        }
    }

    private void initMapFiles() {
        File folder = new File("src/main/resources/casefiles");
        File[] listOfFiles = folder.listFiles();
        String[] filenames = new String[listOfFiles.length];

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                filenames[i] = "Malha " + i;
                filePaths.put(filenames[i], listOfFiles[i].getPath());
            }
        }
    }

    public MatrixUtils getMatrixRoad() {
        return roadInstance;
    }

    public Map<String, String> getFilePaths() {
        return filePaths;
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
        this.await = await * 100;
    }

    public int getAwait() {
        return this.await;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public void await() {
        try {
            sleep(await);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        do {
            if (!stopped) {
                roadStart();
            } else {
                await();
            }

        } while (true);
    }

    public void initialize() {
        this.stopped = false;
        notifyControllButton();
    }

    public void finish() {
        this.stopped = true;
        notifyControllButton();
    }

    private void roadStart() {
        int i = 0;
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
                await();
            }
            if (carList.size() == qtdCar) {
                await();
            }
        }
    }

}

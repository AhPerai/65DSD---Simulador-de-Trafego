package controller;

import java.io.IOException;
import model.RoadDirection;
import utils.MatrixUtils;

/**
 *
 * @author Usuario
 */
public class Controller {

    private final MatrixUtils roadInstance = MatrixUtils.getInstance();
    private static Controller instance;
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

}

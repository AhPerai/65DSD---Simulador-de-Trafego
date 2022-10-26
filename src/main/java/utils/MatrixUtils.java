package utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Block;
import model.RoadDirection;
import model.RoadMutex;

public class MatrixUtils {

    private static MatrixUtils instance;

    private MatrixUtils() {
    }

    public static MatrixUtils getInstance() {
        if (instance == null) {
            instance = new MatrixUtils();
        }
        return instance;
    }

    private RoadMutex[][] matriz;
    List<RoadMutex> entrances = new ArrayList<>();
    List<RoadMutex> exits = new ArrayList<>();

    public void generateMapFromFile(String mPath) throws IOException {
        Path path = Paths.get(mPath);
        List<String> lines = Files.readAllLines(path);
        System.out.println(lines.get(0));
        System.out.println(lines.get(1));
        matriz = new RoadMutex[Integer.parseInt(lines.get(0))][Integer.parseInt(lines.get(1))];
        StringBuilder strRoad = new StringBuilder();

        //Criação da Matriz
        for (int i = 2; i < lines.size(); i++) {
            String[] line = lines.get(i).split("\t");
            for (int j = 0; j < line.length; j++) {
                matriz[i - 2][j] = new RoadMutex(Integer.parseInt(line[j]), i - 2, j);

                strRoad.append(line[j] + " ");
            }
            strRoad.append("\n");
        }

        //Setando nextBlock de todas as células
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j].setNextBlock(matriz);
            }
        }

        loadEntrances();
        loadExits();

        System.out.println(strRoad);
    }

    public void loadExits() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (getCellDirection(i, j) > 0 && getCellDirection(i, j) < 5 && matriz[i][j].getNextBlock() == null) {
                    matriz[i][j].setIsExit(true);
                    exits.add(matriz[i][j]);
                }
            }
        }

        System.out.println("Saídas");
        for (Block exit : exits) {
            System.out.println(exit.getLinePosition() + " " + exit.getColumnPosition());
        }
    }

    public void loadEntrances() {
        //Ruas que sobem e se encontram na linha ultima linha
        for (int i = matriz.length - 1; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (this.getCellDirection(i, j) == RoadDirection.UP.getNumber()) {
                    entrances.add(matriz[i][j]);
                }
            }
        }

        //Ruas que descem e se encontram na primeira linha
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (this.getCellDirection(i, j) == RoadDirection.DOWN.getNumber()) {
                    entrances.add(matriz[i][j]);
                }
            }
        }

        //Ruas que vão para a direita e se encontram na primeira coluna
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < 1; j++) {
                if (this.getCellDirection(i, j) == RoadDirection.RIGHT.getNumber()) {
                    entrances.add(matriz[i][j]);
                }
            }
        }

        //Ruas que vao para a esquerda e se encontram na ultima coluna
        for (int i = 0; i < matriz.length; i++) {
            for (int j = matriz[i].length - 1; j < matriz[i].length; j++) {
                if (this.getCellDirection(i, j) == RoadDirection.LEFT.getNumber()) {
                    entrances.add(matriz[i][j]);
                }
            }
        }

        //embalhara as entradas
        Collections.shuffle(getEntrances());

        System.out.println("Entradas");
        for (Block entrance : entrances) {
            System.out.println(entrance.getLinePosition() + " " + entrance.getColumnPosition());
        }
    }

    public List<RoadMutex> getExits() {
        return this.exits;
    }

    public List<RoadMutex> getEntrances() {
        return this.entrances;
    }

    public int getCellDirection(int i, int j) {
        return matriz[i][j].getDirectionFlow();
    }

    public int getRowCount() {
        return matriz.length;
    }

    public int getColCount() {
        return matriz[0].length;
    }

    public RoadMutex[][] getMatrix() {
        return matriz;
    }

    public RoadMutex getBlock(int i, int j) {
        if (i <= getRowCount() && j <= getColCount()) {
            return matriz[i][j];
        }
        return null;
    }
}

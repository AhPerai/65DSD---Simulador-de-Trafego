package view;

import controller.Controller;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import model.RoadDirection;

public class Table extends JPanel {

    private final int size = 600;
    private int cellSize;
    private int rows;
    private int cols;
    private Controller c;
    private Cell[][] road;

    public Table() {
        this.c = Controller.getInstance();
//        boardController.addObserver(this);
        rows = c.getMatrixRoad().getRowCount();
        cols = c.getMatrixRoad().getColCount();
        road = new Cell[rows][cols];
        cellSize = 600/rows;
        setPreferredSize(new Dimension(cellSize * (int)Math.round(cols * 1.2), cellSize * (int)Math.round(rows * 1.2)));
        setLayout(new GridLayout(rows, cols));
        setOpaque(false);
        draw();
    }

    public void draw() {
        this.removeAll();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String p = RoadDirection.getDirectionPath(c.getMatrixRoad().getCellDirection(i, j));
                Cell cell = new Cell(p, cellSize, cellSize);
                road[i][j] = cell;
                this.add(cell);
            }
        }
       
        this.revalidate();
    }

    public Cell getTileByPosition(int i, int j) {
        return road[i][j];
    }

}

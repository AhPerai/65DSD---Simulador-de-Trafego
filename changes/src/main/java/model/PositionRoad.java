package model;

public class PositionRoad {

    private  int linePosition;
    private  int columnPosition;

    public PositionRoad(int linha, int coluna) {
        this.linePosition = linha;
        this.columnPosition = coluna;
    }

    public int getLinePosition() {
        return linePosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }
}

package view;

import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Cell extends JPanelImage{
    
    private static int cellSize;
    private JLabel car;
    
    
    public Cell(String path, int width, int height) {
        super(path, width, height);
    }
    
    public static int getCellSize(){
        return cellSize;
    }
    
}

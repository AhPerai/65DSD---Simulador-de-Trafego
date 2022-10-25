package view;

import controller.Observer;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Cell extends JPanelImage implements Observer{
    
    private static int cellSize;
    private JLabel car;
    
    
    public Cell(String path, int width, int height) {
        super(path, width, height);
    }
    
    public static int getCellSize(){
        return cellSize;
    }

    @Override
    public void updateControllStatus(boolean status) {}

    @Override
    public void updateThreadCounter(int counter) {}

    @Override
    public void updateCarPosition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

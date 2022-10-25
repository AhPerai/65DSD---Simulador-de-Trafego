package view;

import controller.Observer;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Cell extends JPanelImage {

    private static int cellSize;
    private JLabel car;

    public Cell(String path, int width, int height) {
        super(path, width, height);
        setLayout(new GridBagLayout());
    }

    public static int getCellSize() {
        return cellSize;
    }

    public JLabel getCar() {
        return car;
    }

    public void setCar(JLabel car) {
        this.car = car;
    }

    public void addCarLabel() {
        if (car != null) {
            GridBagConstraints tileConstraint = new GridBagConstraints();
            tileConstraint.anchor = GridBagConstraints.CENTER;
            tileConstraint.fill = GridBagConstraints.BOTH;
            int iconSize = (int) (cellSize * 0.75);
            Image icon = new ImageIcon(getClass().getResource("/images/car.png")).getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            JLabel car = new JLabel(new ImageIcon(icon));
            
            add(car, tileConstraint);
            setCar(car);
            repaint();
            revalidate();
        }
    }

    public void reset() {
        remove(car);
        setCar(null);
        repaint();
        revalidate();
    }
}

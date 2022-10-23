package view;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class Index extends JFrame {

    JPanelImage settingsPanel;
    GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints layoutConstraint = new GridBagConstraints();

    HashMap<Integer, JComponent> menuComp = new HashMap<Integer, JComponent>();
    JButton btControll;      //Controle para iniciar e finaliza a op
    JLabel lblCar;           //Label de indicação: quantidade de carros
    JLabel lblInsertionSpeed;//Label de indicação: delay para inserção de novas threads  
    JLabel lblCarCounter;    //Label de indicação: contador de threads
    JLabel lblCounter;       //Label de indicação: contador de threads notificável

    JSpinner tfInsertionSpeed;
    JSpinner tfCar;

    public Index() {
        setTitle("Malha Viária");
        setSize(1400, 825);
        setLayout(gbl);
        getContentPane().setBackground(Color.decode("#D3CAC5"));
        setLocationRelativeTo(null);

        buildPanels();
    }

    public void buildPanels() {
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 0;

        //Criação do painel superior
        settingsPanel = new JPanelImage("/images/settings.png", 1300, 65);
        settingsPanel.setPreferredSize(new Dimension(settingsPanel.getWidth(), settingsPanel.getHeight()));
        settingsPanel.setOpaque(false);
        
        initilizeMenuComponents();

        //Posicionando os items do menu
        add(settingsPanel, layoutConstraint);
    }

    private void initilizeMenuComponents() {

        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints mLayout = new GridBagConstraints();
       
        btControll = new JButton("INICIAR");
        lblCar = new JLabel("Quantidade de carros: ");
        lblInsertionSpeed = new JLabel("Delay de inserção: ");
        lblCarCounter = new JLabel("Quantidade de carros passeando: ");
        lblCounter = new JLabel("");
        tfCar = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        tfInsertionSpeed = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        menuComp.put(1, btControll);
        menuComp.put(2, lblCar);
        menuComp.put(3, tfCar);
        menuComp.put(4, lblInsertionSpeed);
        menuComp.put(5, tfInsertionSpeed);
        menuComp.put(6, lblCarCounter);
        menuComp.put(7, lblCounter);

        mLayout.gridx = 0;
        mLayout.gridy = 0;

        for (int i = 1; i < 8; i++) {
            mLayout.gridx = i;
            mLayout.weightx = 0.1;
            if(i % 2 != 0){
            mLayout.weightx = 0.9;    
            }           
            settingsPanel.add(menuComp.get(i), mLayout);
        }
    }


}

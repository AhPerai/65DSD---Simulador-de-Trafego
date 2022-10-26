package view;

import controller.Controller;
import controller.Observer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.*;

public class Index extends JFrame implements Observer {

    Controller controller;
    JPanelImage settingsPanel;
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints layoutConstraint = new GridBagConstraints();

    HashMap<Integer, JComponent> menuComp = new HashMap<Integer, JComponent>();
    JButton btStart;      //Controle para iniciar e finaliza a op
    JButton btStop;      //Controle para iniciar e finaliza a op
    JLabel lblCar;           //Label de indicação: quantidade de carros
    JLabel lblInsertionSpeed;//Label de indicação: delay para inserção de novas threads  
    JLabel lblCarCounter;    //Label de indicação: contador de threads
    JLabel lblCounter;       //Label de indicação: contador de threads notificável

    JSpinner tfInsertionSpeed;
    JSpinner tfCar;
    JComboBox<String> cbFile;

    public Index() {
        controller = Controller.getInstance();
        controller.addObserver(this);
        setTitle("Malha Viária");
        setSize(1400, 825);
        setLayout(gbl);
        getContentPane().setBackground(Color.decode("#D3CAC5"));
        setLocationRelativeTo(null);

        buildPanels();
        setVisible(true);
        controller.notifyControllButton();
    }

    public void buildPanels() {
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 0;
        layoutConstraint.weighty = 0.1;

        //Criação do painel superior
        settingsPanel = new JPanelImage("/images/settings.png", 1300, 65);
        settingsPanel.setPreferredSize(new Dimension(settingsPanel.getWidth(), settingsPanel.getHeight()));
        settingsPanel.setOpaque(false);
        initilizeMenuComponents();
        add(settingsPanel, layoutConstraint);

        //Desenhando a malha
        Table road = new Table();
        layoutConstraint.weighty = 0.9;
        layoutConstraint.gridy = 1;
        add(road, layoutConstraint);
    }

    private void initilizeMenuComponents() {

        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints mLayout = new GridBagConstraints();

        btStart = new JButton("Iniciar");
        btStop = new JButton("Parar");
        lblCar = new JLabel("Quantidade de carros: ");
        lblInsertionSpeed = new JLabel("Delay de inserção: ");
        lblCarCounter = new JLabel("Quantidade de carros passeando: ");
        lblCounter = new JLabel("0");
        tfCar = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        tfInsertionSpeed = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        menuComp.put(0, btStart);
        menuComp.put(1, btStop);
        menuComp.put(2, lblCar);
        menuComp.put(3, tfCar);
        menuComp.put(4, lblInsertionSpeed);
        menuComp.put(5, tfInsertionSpeed);
        menuComp.put(6, lblCarCounter);
        menuComp.put(7, lblCounter);

        mLayout.gridx = 0;
        mLayout.gridy = 0;

        for (int i = 0; i < menuComp.size(); i++) {
            mLayout.gridx = i;
            mLayout.weightx = 1.0;
            settingsPanel.add(menuComp.get(i), mLayout);
        }
        
        addActions();
    }

    //Controller Actions
    private void addActions() {
        btStart.addActionListener((ActionEvent e) -> {
            controller.setQtdCar((int) tfCar.getValue());
            controller.setAwait((int) tfInsertionSpeed.getValue());
            controller.start();
        });

        btStop.addActionListener((ActionEvent e) -> {
            controller.finish();
        });
    }

    //Observer updates
    @Override
    public void updateControllStatus(boolean isStopped) {
            btStart.setEnabled(isStopped);
            btStop.setEnabled(!isStopped);
    }

    @Override
    public void updateThreadCounter(int counter) {
        lblCounter.setText(counter + "");
    }

    @Override
    public void updateCarPosition(Integer[][] blockPositions) {
    }

}

package view;

import controller.Controller;
import controller.Observer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Index extends JFrame implements Observer {

    Table road;
    Controller controller;
    JPanelImage settingsPanel;
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints layoutConstraint = new GridBagConstraints();

    ArrayList<JComponent> menuComp = new ArrayList<>();
    JButton btStart;      //Controle para iniciar e finaliza a op
    JButton btStop;      //Controle para iniciar e finaliza a op
    JLabel lblCar;           //Label de indicação: quantidade de carros
    JLabel lblInsertionSpeed;//Label de indicação: delay para inserção de novas threads  
    JLabel lblCarCounter;    //Label de indicação: contador de threads
    JLabel lblCounter;       //Label de indicação: contador de threads notificável

    JComboBox<String> cbRoadFiles;
    JSpinner tfInsertionSpeed;
    JSpinner tfCar;

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
        road = new Table();
        layoutConstraint.weighty = 0.9;
        layoutConstraint.gridy = 1;
        add(road, layoutConstraint);

        controller.notifyInitFiles();
    }

    private void initilizeMenuComponents() {

        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints mLayout = new GridBagConstraints();

        btStart = new JButton("Iniciar");
        btStop = new JButton("Parar");
        cbRoadFiles = new JComboBox();
        lblCar = new JLabel("Quantidade de carros: ");
        lblInsertionSpeed = new JLabel("Delay de inserção: ");
        lblCarCounter = new JLabel("Quantidade de carros passeando: ");
        lblCounter = new JLabel("0");
        tfCar = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
        tfInsertionSpeed = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        menuComp.add(btStart);
        menuComp.add(btStop);
        menuComp.add(cbRoadFiles);
        menuComp.add(lblCar);
        menuComp.add(tfCar);
        menuComp.add(lblInsertionSpeed);
        menuComp.add(tfInsertionSpeed);
        menuComp.add(lblCarCounter);
        menuComp.add(lblCounter);

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
            controller.initialize();
        });

        btStop.addActionListener((ActionEvent e) -> {
            controller.finish();
        });

        cbRoadFiles.addActionListener((ActionEvent e) -> {
            controller.getMatrixRoad().generateMapFromFile(controller.getFilePaths().get((String) cbRoadFiles.getSelectedItem()));
            road.draw();
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

    @Override
    public void initRoadFiles(String[] roadFiles) {
        for (String file : roadFiles) {
            cbRoadFiles.addItem(file);
        }
    }

}

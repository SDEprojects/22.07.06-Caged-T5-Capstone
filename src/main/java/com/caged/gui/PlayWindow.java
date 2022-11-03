package com.caged.gui;

import com.caged.Doors;
import com.caged.LocationGetter;
import com.caged.Player;
import com.caged.YAMLReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class PlayWindow implements ActionListener {

    // GUI VARIABLES
    JFrame frame;
    JPanel topPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    JLabel label;
    JLabel compassLabel;
    ImageIcon displayImage;
    ImageIcon compassImage;

    JLabel location;
    JLabel weapon;
    JLabel HP;
    JLabel disguised;

    JTextField locationField;
    JTextField weaponField;
    JTextField HPField;
    JTextField disguisedField;

    JButton help;
    JButton quitBtn;

    JToggleButton volume;

    // GAME VARIABLES
    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    //List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths

    public void execute() {
        mainLabel();
        createLabels(player);
        createHelpBtn();
        createMusicToggleBtn();
        createQuitBtn();
        createTopPanel();
        createCompass();
        createCenterPanel();
        createBottomPanel();
        createFrame();

    }

    public void createFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The Caged");
        frame.setSize(1200, 900);
        frame.setLayout(null);
        frame.add(topPanel);
        frame.add(centerPanel);
        frame.add(bottomPanel);
        frame.add(label);
        frame.setVisible(true);

    }

    public void mainLabel() {
        displayImage = new ImageIcon("resources/MainGameDisplay.jpg");
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);
    }

    public void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(false);
        topPanel.add(volume);
        topPanel.add(location);
        topPanel.add(weapon);
        topPanel.add(HP);
        topPanel.add(disguised);
        topPanel.add(help);
    }

    public void createCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 600);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(null);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
        centerPanel.add(compassLabel);
    }

    public void createBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 700, 1200, 90);
        bottomPanel.setOpaque(false);
        bottomPanel.add(quitBtn);
    }


    public void createLabels(Player player) {
        location = new JLabel("Location: " + player.getCurrentLocation());
        location.setForeground(Color.ORANGE);
        weapon = new JLabel("Weapon: " + player.getWeapon());
        weapon.setForeground(Color.ORANGE);
        HP = new JLabel("HP: " + player.getHitPoints());
        HP.setForeground(Color.ORANGE);
        disguised = new JLabel("Disguised: " + player.getEquipment());
        disguised.setForeground(Color.ORANGE);
    }

    public void createHelpBtn() {
        help = new JButton("Help");
    }

    public void createQuitBtn() {
        quitBtn = new JButton("Quit Game");
        quitBtn.addActionListener(this);
    }

    public void createMusicToggleBtn() {
        volume = new JToggleButton("Music ON");
    }

    public void createTextFields() {
        locationField = new JTextField(5);
        weaponField = new JTextField(5);
        HPField = new JTextField(5);
        disguisedField = new JTextField(5);

    }

    public void createCompass() {
        compassImage = new ImageIcon("resources/compass.jpeg");
        compassLabel = new JLabel(compassImage);
        compassLabel.setBounds(970, 200, 200, 200);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == quitBtn){
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitBtn) {
            int userInput = JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?", "Caged", JOptionPane.YES_NO_OPTION);
            if (userInput == 0) {
                frame.dispose();
            }
        }
    }
}

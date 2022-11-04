package com.caged.gui;

import com.caged.*;

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
    ImageIcon northImg;
    ImageIcon southImg;
    ImageIcon eastImg;
    ImageIcon westImg;

    JLabel location;
    JLabel weapon;
    JLabel HP;
    JLabel disguised;

    JTextArea actionField;


    JButton help;
    JButton quitBtn;
    JButton north;
    JButton south;
    JButton east;
    JButton west;

    JToggleButton volume;

    // GAME VARIABLES
    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    //List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths
    MusicPlayer gameMusic = new MusicPlayer();

    public void execute() {
        mainLabel();
        createLabels(player);
        createHelpBtn();
        createMusicToggleButton();
        createQuitBtn();
        createTopPanel();
//        createCompass();
        createDirectionalButtons();
        createActionInfoArea();
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
       // centerPanel.add(compassLabel);
        centerPanel.add(north);
        centerPanel.add(south);
        centerPanel.add(east);
        centerPanel.add(west);
        centerPanel.add(actionField);
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

    public void createMusicToggleButton() {
        volume = new JToggleButton("Music ON");
        volume.setForeground(Color.GREEN);
        gameMusic.play();
        volume.addActionListener(this);
    }

    public void createActionInfoArea() {
          String text = "You  awaken and found yourself crying in the cage!";
          actionField = new JTextArea(5, 40);
          actionField.setBounds(930,90, 250, 100);
          actionField.setFont(new Font ("Arial", Font.BOLD, 14));
          actionField.setText(text);
          actionField.setLineWrap(true);
          actionField.setWrapStyleWord(true);
//          actionField.setOpaque(false);
          actionField.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));

    }

    public void createCompass() {
        compassImage = new ImageIcon("resources/compass.jpeg");
        compassLabel = new JLabel(compassImage);
        compassLabel.setBounds(970, 200, 200, 200);
    }
    public void createDirectionalButtons(){
        northImg = new ImageIcon("resources/north.png");
        southImg = new ImageIcon("resources/south.png");
        eastImg = new ImageIcon("resources/east.png");
        westImg = new ImageIcon("resources/west.png");
        north = new JButton("NORTH", northImg);
        south = new JButton("SOUTH", southImg);
        east = new JButton("EAST", eastImg);
        west = new JButton("WEST", westImg);
        north.setBounds(0, 0, 100, 100);
        south.setBounds(100, 0, 100, 100);
        east.setBounds(200, 0, 100, 100);
        west.setBounds(300, 0, 100, 100);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitBtn) {
            int userInput = JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?", "Caged", JOptionPane.YES_NO_OPTION );
            if (userInput == 0) {
                System.out.println(volume.isSelected());
                gameMusic.turnOff();
                frame.dispose();
            }
        }
        if(volume.isSelected()){
            volume.setText("Music OFF");
            volume.setForeground(Color.RED);
            gameMusic.turnOff();
        }
        else{
            volume.setText("Music ON");
            volume.setForeground(Color.GREEN);
            gameMusic.play();
        }
    }
}

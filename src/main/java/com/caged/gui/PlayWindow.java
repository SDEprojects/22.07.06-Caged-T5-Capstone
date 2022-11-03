package com.caged.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class PlayWindow {
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



    public void execute(){
        mainLabel();
        createLabels();
        createTextFields();
        createHelpBtn();
        createMusicToggleBtn();
        createQuitBtn();
        createTopPanel();
        createCompass();
        createCenterPanel();
        createBottomPanel();
        createFrame();

    }

    public void createFrame(){
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
    public void mainLabel(){
        displayImage = new ImageIcon("resources/MainGameDisplay.jpg");
        label = new JLabel(displayImage);
        label.setBounds(0,0, 1200, 900);


    }
    public void createTopPanel(){
        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(false);
        topPanel.add(volume);
        topPanel.add(location);
        topPanel.add(locationField);
        topPanel.add(weapon);
        topPanel.add(weaponField);
        topPanel.add(HP);
        topPanel.add(HPField);
        topPanel.add(disguised);
        topPanel.add(disguisedField);
        topPanel.add(help);

    }

    public void createCenterPanel(){
        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 600);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(null);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
        centerPanel.add(compassLabel);

    }

    public void createBottomPanel(){
        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 700, 1200, 90);
        bottomPanel.setOpaque(false);
        bottomPanel.add(quitBtn);
    }
    public void createLabels(){
        location = new JLabel("Location");
        location.setForeground(Color.ORANGE);
        weapon = new JLabel("Weapon");
        weapon.setForeground(Color.ORANGE);
        HP = new JLabel("HP");
        HP.setForeground(Color.ORANGE);
        disguised = new JLabel("Disguised");
        disguised.setForeground(Color.ORANGE);

    }
    public void createHelpBtn(){
        help = new JButton("Help");
    }
    public void createQuitBtn(){
        quitBtn = new JButton("Quit Game");
    }

    public void createMusicToggleBtn(){
        volume = new JToggleButton("Music ON");

    }

    public void createTextFields(){
        locationField = new JTextField(5);
        weaponField = new JTextField(5);
        HPField = new JTextField(5);
        disguisedField = new JTextField(5);

    }
    public void createCompass(){
        compassImage = new ImageIcon("resources/compass.jpeg");
        compassLabel = new JLabel(compassImage);
        compassLabel.setBounds(970, 200, 200, 200);


    }


}

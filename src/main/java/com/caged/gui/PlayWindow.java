package com.caged.gui;

import com.caged.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class PlayWindow implements MouseListener{

    // GUI VARIABLES
    JFrame frame;
    JPanel topPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    JPanel centerEastPanel;
    JPanel centerMidPanel;
    JPanel centerSouthPanel;
    JPanel centerWestPanel;
    JPanel inventoryPanel;

    JLabel label;
    ImageIcon displayImage;
    ImageIcon northImg;
    ImageIcon southImg;
    ImageIcon eastImg;
    ImageIcon westImg;
    ImageIcon bedImg;
    ImageIcon wallImg;
    ImageIcon windowImg;
    ImageIcon deskImg;
    ImageIcon map;

    JLabel location;
    JLabel weapon;
    JLabel HP;
    JLabel disguised;
    JLabel mapLabel;

    JTextArea actionField;
    JTextArea hoverText;


    JButton help;
    JButton quitBtn;
    JButton north;
    JButton south;
    JButton east;
    JButton west;
    JButton inv1;
    JButton inv2;
    JButton inv3;
    JButton inv4;

    JToggleButton volume;
    JSlider minMaxVolume;

    // Class loader for image calling
    FileGetter url = new FileGetter();

    // GAME VARIABLES
    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    //List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths
    MusicPlayer gameMusic = new MusicPlayer();

    public void execute() {
        gameMusic.setFile("bgmusic.wav");
        gameMusic.play();
        gameMusic.loopSound();
        mainLabel();
        createLabels(player);
        createHelpBtn();
        createMusicToggleButton();
        createMusicSlider();
        createQuitBtn();
        createTopPanel();
        createDirectionalButtons();
        createActionInfoArea();
        createInvButtons();
        createInventoryPanel();
        createMap();
        createMidCenterPanels();
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
        frame.setLocationRelativeTo(null);
        frame.add(topPanel);
        frame.add(centerPanel);
        frame.add(bottomPanel);
        frame.add(label);
        frame.setVisible(true);

    }

    public void mainLabel() {
        displayImage = new ImageIcon(url.imageGetter("MainGameDIsplay.jpg"));
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);
    }

    public void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(false);
        topPanel.add(minMaxVolume);
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
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
        centerPanel.add(centerMidPanel, BorderLayout.CENTER);
        centerPanel.add(centerEastPanel, BorderLayout.EAST);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);
        centerPanel.add(centerWestPanel, BorderLayout.WEST);

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
        weapon = new JLabel(" || Weapon: " + player.getWeapon());
        weapon.setForeground(Color.ORANGE);
        HP = new JLabel(" || HP: " + player.getHitPoints());
        HP.setForeground(Color.ORANGE);
        disguised = new JLabel(" || Disguised: " + player.getEquipment());
        disguised.setForeground(Color.ORANGE);
    }

    public void createHelpBtn() {
        help = new JButton("Help");
    }

    public void createQuitBtn() {
        quitBtn = new JButton("Quit Game");
        quitBtn.addActionListener(e -> {
            int userInput = JOptionPane.showConfirmDialog(frame,
                    "Are you your you want to quit?", "Caged", JOptionPane.YES_NO_OPTION );
            if (userInput == 0) {
                System.exit(1);
            }
        });
    }

    public void createMusicToggleButton() {
        volume = new JToggleButton("Music ON");
        volume.setForeground(Color.GREEN);
        volume.addActionListener(e -> {
            gameMusic.mute(minMaxVolume);
            if(volume.isSelected()){
                volume.setText("Music OFF");
                volume.setForeground(Color.RED);
            }
            else{
                volume.setText("Music ON");
                volume.setForeground(Color.GREEN);
            }
        });
    }
    public void createMusicSlider(){
        minMaxVolume = new JSlider(-60,6);
        minMaxVolume.setPreferredSize(new Dimension(100,50));
        minMaxVolume.setPaintTicks(true);
        minMaxVolume.addChangeListener(e -> {
            gameMusic.currentVolume = minMaxVolume.getValue();
            if(volume.getText() == "Music ON"){
                gameMusic.floatControl.setValue(gameMusic.currentVolume);
            }
        });

    }
    public void createActionInfoArea() {
          String text = "You  awaken and found yourself crying in the cage!";
          actionField = new JTextArea(50, 40);
          actionField.setFont(new Font ("Arial", Font.BOLD, 14));
          actionField.setText(text);
          actionField.setLineWrap(true);
          actionField.setWrapStyleWord(true);
          actionField.setOpaque(false);
          actionField.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));

    }
    public void createDirectionalButtons(){
        northImg = new ImageIcon(url.imageGetter("north.png"));
        southImg = new ImageIcon(url.imageGetter("south.png"));
        eastImg = new ImageIcon(url.imageGetter("east.png"));
        westImg = new ImageIcon(url.imageGetter("west.png"));
        north = new JButton(northImg);
        north.setPreferredSize(new Dimension(100,190));
        north.setOpaque(false);
        north.setBorderPainted(false);
        north.setBorder(null);
        north.setFocusPainted(false);
        south = new JButton(southImg);
        south.setOpaque(false);
        south.setBorderPainted(false);
        south.setBorder(null);
        south.setFocusPainted(false);
        south.setPreferredSize(new Dimension(100,190));
        east = new JButton(eastImg);
        east.setBorderPainted(false);
        east.setOpaque(false);
        east.setBorder(null);
        east.setPreferredSize(new Dimension(130,195));
        east.setFocusPainted(false);
        west = new JButton(westImg);
        west.setOpaque(false);
        west.setBorderPainted(false);
        west.setBorder(null);
        west.setPreferredSize(new Dimension(130,195));
        west.setFocusPainted(false);

    }
    public void createMidCenterPanels(){
        centerEastPanel = new JPanel();
        centerEastPanel.setOpaque(false);
        centerEastPanel.setPreferredSize(new Dimension(300,480));
        centerEastPanel.setLayout(new BorderLayout());
        centerEastPanel.add(east, BorderLayout.EAST );
        centerEastPanel.add(west, BorderLayout.WEST);
        centerEastPanel.add(north, BorderLayout.NORTH);
        centerEastPanel.add(south, BorderLayout.SOUTH);
        centerEastPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerMidPanel = new JPanel();
        centerMidPanel.setLayout(new BorderLayout());
        centerMidPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerMidPanel.setOpaque(false);
        centerMidPanel.add(inventoryPanel, BorderLayout.NORTH);
        centerMidPanel.add(mapLabel, BorderLayout.CENTER);
        centerWestPanel = new JPanel();
        centerWestPanel.setOpaque(false);
        centerWestPanel.setPreferredSize(new Dimension(300,480));
        centerWestPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerSouthPanel = new JPanel();
        centerSouthPanel.setOpaque(false);
        centerSouthPanel.setPreferredSize(new Dimension(1200,120));
        centerSouthPanel.setLayout(new FlowLayout());
        centerSouthPanel.add(actionField);
        centerSouthPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
    }

    public void createInvButtons(){
        bedImg = new ImageIcon("resources/bed.png");
        wallImg = new ImageIcon("resources/wall.jpg");
        windowImg = new ImageIcon("resources/window.jpg");
        deskImg = new ImageIcon("resources/desk.jpeg");
        inv1 = new JButton(bedImg);
        inv1.setOpaque(false);
        inv1.setBorderPainted(false);
        inv1.setBorder(null);
        inv1.setFocusPainted(false);
        inv1.addMouseListener(this);
        inv2 = new JButton(wallImg);
        inv2.setOpaque(false);
        inv2.setBorderPainted(false);
        inv2.setBorder(null);
        inv2.addMouseListener(this);
        inv3 = new JButton(windowImg);
        inv3.setOpaque(false);
        inv3.setBorderPainted(false);
        inv3.setBorder(null);
        inv3.addMouseListener(this);
        inv4 = new JButton(deskImg);
        inv4.setOpaque(false);
        inv4.setBorderPainted(false);
        inv4.setBorder(null);
        inv4.addMouseListener(this);
    }
    public void createInventoryPanel(){
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(600, 200));
        inventoryPanel.setLayout( new FlowLayout(FlowLayout.CENTER, 15, 40));
        inventoryPanel.setOpaque(false);
        inventoryPanel.add(inv1);
        inventoryPanel.add(inv2);
        inventoryPanel.add(inv3);
        inventoryPanel.add(inv4);

    }

    public void createMap(){
        map = new ImageIcon("resources/map.jpeg");
        mapLabel = new JLabel(map);

    }

    public void inventoryTaken(){


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        String itemInfo = "<html>Heavily reinforced metal, <br> maybe you can wiggle and <br> 'use' the 'window bars</html>";
        UIManager.put("ToolTip.foreground", Color.PINK);
        UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));
        if(e.getSource() == inv1){
            inv1.setToolTipText(itemInfo);
        }
        if(e.getSource() == inv2){
            inv2.setToolTipText(itemInfo);
        }
        if(e.getSource() == inv3){
            inv3.setToolTipText(itemInfo);
        }
        if(e.getSource() == inv4){
            inv4.setToolTipText(itemInfo);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

package com.caged.test;

import com.caged.*;
import com.caged.gui.PlayWindow;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class PlayWindowTest extends JPanel implements MouseListener{

    JPanel inventoryPanel;
    JPanel playerActionPanel;

    JLabel label;

    ImageIcon northImg;
    ImageIcon southImg;
    ImageIcon eastImg;
    ImageIcon westImg;
    ImageIcon bedImg;
    ImageIcon wallImg;
    ImageIcon windowImg;
    ImageIcon deskImg;
    ImageIcon map;


    JTextArea actionField;
    JTextArea hoverText;


    JButton quitBtn;
    JButton north;
    JButton south;
    JButton east;
    JButton west;
    JButton inv1;
    JButton inv2;
    JButton inv3;
    JButton inv4;
    JButton take;
    JButton look;
    JButton talk;
    JButton attack;
    JButton use;
    JButton unlock;
    JButton equip;
    JButton heal;


    //MAIN Panel
    JPanel panel, bottomPanel, topPanel, centerPanel;
    JPanel[] gameWindow = new JPanel[15];
    JLabel[] gameLabel = new JLabel[15];
    JLabel backgroundPic;
    //TOP PANEL///
    JLabel location, weapon, HP, disguised, help;
    JToggleButton volume;
    JSlider minMaxVolume;
    ImageIcon displayImage;
    //CENTER PANEL///
    JPanel centerMidPanel, centerSouthPanel, centerEastPanel, centerWestPanel;
    JLabel mapLabel;


    FileGetter url = new FileGetter();
    MusicPlayer gameMusic = new MusicPlayer();

    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths

    YAMLMapper mapper = new YAMLMapper();
    JsonNode node = mapper.valueToTree(locationVar);

    public PlayWindowTest(JFrame frame) {
        gameMusic.setFile("bgmusic.wav");
        gameMusic.play();
        gameMusic.loopSound();
        createTopPanel(frame);
        createBottomPanel(frame);
        createCenterPanel(frame);
        playWindow(frame);

        frame.setVisible(true);
    }

    public void playWindow(JFrame frame) {
        panel = new JPanel();
        panel.setLayout(null);

        displayImage = new ImageIcon(url.imageGetter("MainGameDIsplay.jpg"));
        backgroundPic = new JLabel(displayImage);
        backgroundPic.setBounds(0, 0, 1200, 900);
        panel.add(topPanel);
        panel.add(bottomPanel);
        panel.add(centerPanel);
        panel.add(backgroundPic);
        frame.add(panel);

    }

    public void createTopPanel(JFrame frame) {
        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(false);

        location = new JLabel("Location: " + player.getCurrentLocation());
        location.setForeground(Color.ORANGE);
        weapon = new JLabel(" || Weapon: " + player.getWeapon());
        weapon.setForeground(Color.ORANGE);
        HP = new JLabel(" || HP: " + player.getHitPoints());
        HP.setForeground(Color.ORANGE);
        disguised = new JLabel(" || Disguised: " + player.getEquipment());
        disguised.setForeground(Color.ORANGE);

        volume = new JToggleButton("Music ON");
        volume.setForeground(Color.GREEN);
        volume.addActionListener(e -> {
            gameMusic.mute(minMaxVolume);
            if (volume.isSelected()) {
                volume.setText("Music OFF");
                volume.setForeground(Color.RED);
            } else {
                volume.setText("Music ON");
                volume.setForeground(Color.GREEN);
            }
        });

        minMaxVolume = new JSlider(-60, 6);
        minMaxVolume.setPreferredSize(new Dimension(100, 50));
        minMaxVolume.setPaintTicks(true);
        minMaxVolume.addChangeListener(e -> {
            gameMusic.currentVolume = minMaxVolume.getValue();
            if (volume.getText() == "Music ON") {
                gameMusic.floatControl.setValue(gameMusic.currentVolume);
            }
        });
        help = new JLabel("Help");

        topPanel.add(minMaxVolume);
        topPanel.add(volume);
        topPanel.add(location);
        topPanel.add(weapon);
        topPanel.add(HP);
        topPanel.add(disguised);
        topPanel.add(help);
    }

    public void createBottomPanel(JFrame frame) {
        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 700, 1200, 90);
        bottomPanel.setOpaque(false);

        JButton quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        quitBtn.setBounds(600, 400, 150, 100);
        quitBtn.addActionListener(e -> {
            int userInput = JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?",
                    "Caged", JOptionPane.YES_NO_OPTION);
            if (userInput == 0) {
                frame.dispose();
            }
        });
        bottomPanel.add(quitBtn);
    }

    public void createCenterPanel(JFrame frame) {
        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 600);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
        createCenterMidPanel();
        createCenterEastPanels();
        createCenterSouthPanel();
        createCenterWestPanel();
        centerPanel.add(centerMidPanel, BorderLayout.CENTER);
        centerPanel.add(centerEastPanel, BorderLayout.EAST);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);
        centerPanel.add(centerWestPanel, BorderLayout.WEST);
    }
    public void createCenterWestPanel(){
        centerWestPanel = new JPanel();
        centerWestPanel.setOpaque(false);
        centerWestPanel.setPreferredSize(new Dimension(300, 480));
        centerWestPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
    }
    public void createCenterSouthPanel(){
        centerSouthPanel = new JPanel();
        centerSouthPanel.setOpaque(false);
        centerSouthPanel.setPreferredSize(new Dimension(1200, 120));
        centerSouthPanel.setLayout(new FlowLayout());
        centerSouthPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        createActionInfoArea();
        createPlayerActionPanel();
        centerSouthPanel.add(actionField);
        centerSouthPanel.add(playerActionPanel);


    }
    public void createActionInfoArea() {
        String text = "You  awaken and found yourself crying in the cage!";
        actionField = new JTextArea(5, 30);
        actionField.setFont(new Font("Arial", Font.BOLD, 14));
        actionField.setText(text);
        actionField.setLineWrap(true);
        actionField.setWrapStyleWord(true);
        actionField.setOpaque(false);
        actionField.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
    }
    public void createPlayerActionPanel(){
        playerActionPanel = new JPanel();
        playerActionPanel.setSize(600, 120);
        playerActionPanel.setOpaque(false);
        createButtonActionPallet();
        playerActionPanel.add(take);
        playerActionPanel.add(look);
        playerActionPanel.add(attack);
        playerActionPanel.add(use);
        playerActionPanel.add(unlock);
        playerActionPanel.add(heal);
        playerActionPanel.add(talk);
        playerActionPanel.add(equip);

    }
    public void createButtonActionPallet(){
        take = new JButton("Take");
        look = new JButton("Look");
        attack = new JButton("Attack");
        use =  new JButton("Use");
        unlock = new JButton("Equip");
        heal = new JButton("Heal");
        talk = new JButton("Talk");
        equip = new JButton("Equip");
    }
    public void createCenterMidPanel(){
        centerMidPanel = new JPanel();
        centerMidPanel.setLayout(new BorderLayout());
        centerMidPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerMidPanel.setOpaque(false);
        createInventoryPanel();
        createMap();
        centerMidPanel.add(inventoryPanel, BorderLayout.NORTH);
        centerMidPanel.add(mapLabel, BorderLayout.CENTER);
    }
    public void createMap() {
        map = new ImageIcon(url.imageGetter("map.jpeg"));
        mapLabel = new JLabel(map);

    }
    public void createInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(600, 200));
        inventoryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 40));
        inventoryPanel.setOpaque(false);
        createInvButtons();
        inventoryPanel.add(inv1);
        inventoryPanel.add(inv2);
        inventoryPanel.add(inv3);
        inventoryPanel.add(inv4);

    }
    public void createInvButtons() {
        bedImg = new ImageIcon(url.imageGetter("bed.png"));
        wallImg = new ImageIcon(url.imageGetter("wall.jpg"));
        windowImg  = new ImageIcon(url.imageGetter("window.jpg"));
        deskImg = new ImageIcon(url.imageGetter("desk.jpeg"));
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
    public void createCenterEastPanels() {
        centerEastPanel = new JPanel();
        centerEastPanel.setOpaque(false);
        centerEastPanel.setPreferredSize(new Dimension(300, 480));
        centerEastPanel.setLayout(new BorderLayout());
        centerEastPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        createDirectionalButtons();
        centerEastPanel.add(east, BorderLayout.EAST);
        centerEastPanel.add(west, BorderLayout.WEST);
        centerEastPanel.add(north, BorderLayout.NORTH);
        centerEastPanel.add(south, BorderLayout.SOUTH);
    }
    public void createDirectionalButtons() {
        northImg = new ImageIcon(url.imageGetter("north.png"));
        southImg = new ImageIcon(url.imageGetter("south.png"));
        eastImg = new ImageIcon(url.imageGetter("east.png"));
        westImg = new ImageIcon(url.imageGetter("west.png"));

        north = new JButton(northImg);
        north.setPreferredSize(new Dimension(100, 190));
        north.setOpaque(false);
        north.setBorderPainted(false);
        north.setBorder(null);
        north.setFocusPainted(false);
        north.addActionListener(this::movePerformed);

        south = new JButton(southImg);
        south.setOpaque(false);
        south.setBorderPainted(false);
        south.setBorder(null);
        south.setFocusPainted(false);
        south.setPreferredSize(new Dimension(100, 190));
        south.addActionListener(this::movePerformed);

        east = new JButton(eastImg);
        east.setBorderPainted(false);
        east.setOpaque(false);
        east.setBorder(null);
        east.setPreferredSize(new Dimension(130, 195));
        east.setFocusPainted(false);
        east.addActionListener(this::movePerformed);

        west = new JButton(westImg);
        west.setOpaque(false);
        west.setBorderPainted(false);
        west.setBorder(null);
        west.setPreferredSize(new Dimension(130, 195));
        west.setFocusPainted(false);
        west.addActionListener(this::movePerformed);
    }


    public void movePerformed(ActionEvent e) {
        if (e.getSource() == north) {
            player.move("north", locationVar, doors);
        } else if (e.getSource() == south) {
            player.move("south", locationVar, doors);
        } else if (e.getSource() == east) {
            player.move("east", locationVar, doors);
        } else if (e.getSource() == west) {
            player.move("west", locationVar, doors);
        }
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

    public void mouseEntered(MouseEvent e) {

        String itemInfo = "<html>Heavily reinforced metal, <br> maybe you can wiggle and <br> 'use' the 'window bars</html>";
        UIManager.put("ToolTip.foreground", Color.PINK);
        UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));
        if (e.getSource() == inv1) {
            inv1.setToolTipText(itemInfo);
        }
        if (e.getSource() == inv2) {
            inv2.setToolTipText(itemInfo);
        }
        if (e.getSource() == inv3) {
            inv3.setToolTipText(itemInfo);
        }
        if (e.getSource() == inv4) {
            inv4.setToolTipText(itemInfo);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

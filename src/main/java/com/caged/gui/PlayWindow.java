package com.caged.gui;

import com.caged.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Objects;

public class PlayWindow extends JPanel implements MouseListener, ActionListener {
    public int playerMaxLife;
    public int playerLife;
    public int hasOrangeKey;
    public int hasChocolate;
    public int hasBedSpring;
    public int hasBrick;
    public int hasGuardUniform;
    public int hasKeyCard;
    int bgNum;
    JPanel inventoryPanel;
    JPanel playerActionPanel;
    JTextArea actionField;
    ImageIcon lifeIcon;
    DefaultListModel<String> inv = new DefaultListModel<>();
    DefaultListModel<String> reactionInv = new DefaultListModel<>();
    DefaultListModel<String> itemInv = new DefaultListModel<>();
    DefaultListModel<String> playerInv = new DefaultListModel<>();
    DefaultListModel<String> npcInv = new DefaultListModel<>();
    JList<String> itemInvList = new JList<>(itemInv);
    JList<String> reactionList = new JList<>(reactionInv);
    JList<String> playerInvList = new JList<>(playerInv);
    JList<String> roomInvList;
    JList<String> npcInvList = new JList<>(npcInv);
    JLabel[] invItem = new JLabel[10];
    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths
    Doors door = new Doors();
    YAMLMapper mapper = new YAMLMapper();
    JsonNode node = mapper.valueToTree(locationVar);
    GameMap playerMap1 = new GameMap();
    GameMap playerMap2 = new GameMap();
    JPanel mapSupportPanel;
    // Creates toggle listener
    boolean selected = false; // this is used by the actionPerformed listener for directional functions
    private FileGetter url = new FileGetter();
    private MusicPlayer gameMusic = new MusicPlayer();
    //images for the arrow buttons
    private JPanel directionalPanel;
    private ImageIcon northImg, southImg, eastImg, westImg;
    private JButton north, south, east, west;
    //images for items on cage1
    private JButton windowBars, bed, desk, thrashBin, wallRubble;
    private ImageIcon trashImg, cage1;
    //All Level 1 in-game halls
    private ImageIcon westHall, southWestHall, southHall, northHall, eastHall;
    //All level 2 in-game halls and vents
    private ImageIcon southWestHall2F, southHall2F, prisonCorridor, northHall2F, northEast2F, ventW, ventNW, ventN, ventNE;
    private ImageIcon prisonExit, storage2F, breakRoom;
    //MAIN Panel
    private JPanel panel, bottomPanel, topPanel, centerPanel;
    private JPanel[] gameWindow = new JPanel[22];
    private JLabel[] gameLabel = new JLabel[22];
    private JLabel backgroundPic, textBGPic;
    //TOP PANEL///
    private JLabel location, weapon, HP, disguised;
    private JButton help;
    private JToggleButton volume;
    private JSlider minMaxVolume;
    private ImageIcon displayImage, textBackground;
    //BOTTOM///
    private JButton quitBtn;
    //CENTER PANEL///
    private JPanel centerSouthPanel, centerEastPanel, centerWestPanel;
    private JPanel[] mapPanelLocation = new JPanel[20];
    private JLabel[] maps = new JLabel[20];
    private ImageIcon sHall, swHall, wHall, nHall, eHall, cage1Loc, npc1Loc, npcLoc, storageLoc;
    private ImageIcon swHall2F, sHall2F, cCorridorLoc, nHall2F, neHall2F, storageLoc2F, breakRoomLoc,
            wVentLoc, nwVentLoc, nVentLoc, getNwVentLoc;
    //CENTER BOTTOM PANEL
    private JPanel actionFieldPanel;
    private JButton take, look, talk, attack, use, equip, heal, drop;
    private JToggleButton unlock;
    private String text;
    private ImageIcon chocolate, brick, orangeKey, bedSpring;
    private JPanel lifePanel;
    private JLabel[] heartLabel = new JLabel[4];
    private ImageIcon southCage, northCage, storageRoom;
    private JPanel centerMidPanel;

    public PlayWindow(JFrame frame) {
        gameMusic.setFile("gamePlaySong.wav");
        gameMusic.play();
        gameMusic.loopSound();
        topPanel(frame);
        bottomPanel(frame);
        centerPanel();
        playWindow(frame);
        frame.setVisible(true);
        drawMap();
    }

    public void playWindow(JFrame frame) {
        panel = new JPanel();
        panel.setLayout(null);

        displayImage = new ImageIcon(url.imageGetter("gameplayBG.png"));
        backgroundPic = new JLabel(displayImage);
        backgroundPic.setBounds(0, 0, 1200, 900);
        panel.add(topPanel);
        panel.add(bottomPanel);
        panel.add(centerPanel);
        panel.add(backgroundPic);
        frame.add(panel);

    }

    public void topPanel(JFrame frame) {
        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(true);
        topPanel.setBackground(new Color(0, 0, 0, 120));

        location = new JLabel("Location: " + player.getCurrentLocation());
        location.setForeground(Color.red);
        location.setFont(new Font("arial", Font.BOLD, 18));
        weapon = new JLabel(" || Weapon: " + player.getWeapon());
        weapon.setForeground(Color.red);
        weapon.setFont(new Font("arial", Font.BOLD, 18));
        HP = new JLabel(" || HP: " + player.getHitPoints());
        HP.setForeground(Color.red);
        HP.setFont(new Font("arial", Font.BOLD, 18));
        disguised = new JLabel(" || Disguised: " + player.getEquipment());
        disguised.setForeground(Color.red);
        disguised.setFont(new Font("arial", Font.BOLD, 18));

        volume = new JToggleButton("Music ON");
        volume.setFont(new Font("arial", Font.BOLD, 18));
        volume.setForeground(Color.GREEN);
        volume.setFocusable(false);
        volume.setBorder(BorderFactory.createEtchedBorder());
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

        minMaxVolume = new JSlider(-70, 6);
        minMaxVolume.setPreferredSize(new Dimension(100, 50));
        minMaxVolume.setPaintTicks(true);
        minMaxVolume.addChangeListener(e -> {
            gameMusic.currentVolume = minMaxVolume.getValue();
            if (Objects.equals(volume.getText(), "Music ON")) {
                gameMusic.floatControl.setValue(gameMusic.currentVolume);
            }
        });
        help = new JButton("Help");
        help.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "<html>Find your way to get out <br>" +
                    "search for clues in certain cages or cell.<br>" +
                    "Do anything it takes to scape <br>" +
                    "use the in-screen menu to set desire volume or mute");
        });

        topPanel.add(minMaxVolume);
        topPanel.add(volume);
        topPanel.add(location);
        topPanel.add(weapon);
        topPanel.add(HP);
        topPanel.add(disguised);
        topPanel.add(help);

    }

    public void bottomPanel(JFrame frame) {
        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 750, 1200, 300);
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setOpaque(false);
        playerHP();

        quitBtn = new JButton("EXIT");
        quitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        quitBtn.setBounds(600, 350, 150, 100);
        quitBtn.addActionListener(e -> {
            int userInput = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the the game?",
                    "Caged", JOptionPane.YES_NO_OPTION);
            if (userInput == 0) {

                gameMusic.turnOff();
                frame.remove(panel);
                new MainWindow(frame);
            }
        });
        bottomPanel.add(lifePanel, BorderLayout.WEST);
        bottomPanel.add(quitBtn);
    }

    public void playerHP() {
        lifePanel = new JPanel();
        lifePanel.setBounds(0, 0, 400, 300);
        lifePanel.setOpaque(false);

        lifeIcon = new ImageIcon(url.imageGetter("healthHeart.png"));
        int i = 1;
        while (i < 4) {
            heartLabel[i] = new JLabel(lifeIcon);
            heartLabel[i].setIcon(lifeIcon);
            lifePanel.add(heartLabel[i]);
            i++;
        }
    }

    public void setPlayerDefaultStatus() {
        playerMaxLife = 3;
        playerLife = 3;
        hasBrick = 0;
        hasOrangeKey = 0;
        hasChocolate = 0;
        hasGuardUniform = 0;
        hasKeyCard = 0;
    }

    public void updatePlayerStatus() {
        //remove life icon
        int i = 1;
        while (i < 4) {
            heartLabel[i].setVisible(false);
            i++;
        }
        int lifeCount = playerLife;
        while (lifeCount != 0) {
            heartLabel[lifeCount].setVisible(true);
            lifeCount--;
        }
    }

    public void centerPanel() {
        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 620);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        cinematicWindow();
        createCenterEastPanels();
        createCenterSouthPanel();
        createCenterWestPanel();
        centerPanel.add(centerMidPanel, BorderLayout.CENTER);
        centerPanel.add(centerEastPanel, BorderLayout.EAST);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);
        centerPanel.add(centerWestPanel, BorderLayout.WEST);
    }
    public void cinematicWindow(){
        centerMidPanel = new JPanel();
        //centerMidPanel.setLayout(null);
        centerMidPanel.setOpaque(false);
        centerMidPanel.setPreferredSize(new Dimension (600,600));
        cinematicBackground();
        panelForCinematic();

        centerMidPanel.add(gameWindow[1]);
        centerMidPanel.add(gameWindow[2]);
        centerMidPanel.add(gameWindow[3]);
        centerMidPanel.add(gameWindow[4]);
        centerMidPanel.add(gameWindow[5]);
        centerMidPanel.add(gameWindow[6]);
        centerMidPanel.add(gameWindow[7]);
        centerMidPanel.add(gameWindow[8]);
        centerMidPanel.add(gameWindow[9]);
        centerMidPanel.add(gameWindow[10]);
        centerMidPanel.add(gameWindow[11]);
        centerMidPanel.add(gameWindow[12]);
        centerMidPanel.add(gameWindow[13]);
        centerMidPanel.add(gameWindow[14]);
        centerMidPanel.add(gameWindow[15]);
        centerMidPanel.add(gameWindow[16]);
        centerMidPanel.add(gameWindow[17]);
        centerMidPanel.add(gameWindow[18]);
        centerMidPanel.add(gameWindow[19]);
        centerMidPanel.add(gameWindow[20]);
        centerMidPanel.add(gameWindow[21]);
    }

    public void cinematicBackground() {
        gameWindow[1] = new JPanel();
        gameWindow[1].setPreferredSize(new Dimension(600, 500));
        gameWindow[1].setLayout(null);
        gameWindow[1].setOpaque(false);

        cage1 = new ImageIcon(url.imageGetter("prisonCell.png"));
        gameLabel[1] = new JLabel(cage1);
        gameLabel[1].setBounds(0, 0, 600, 500);
        cage1Buttons();
        gameWindow[1].add(thrashBin);
        gameWindow[1].add(desk);
        gameWindow[1].add(wallRubble);
        gameWindow[1].add(bed);
        gameWindow[1].add(windowBars);
        gameWindow[1].add(gameLabel[1]);
    }

    public void cage1Buttons() {
        windowBars = new JButton("window bars");
        windowBars.setBounds(45, 50, 50, 155);
        windowBars.setBorderPainted(false);
        windowBars.setBorder(null);
        windowBars.setFocusPainted(false);
        windowBars.addMouseListener(this);

        bed = new JButton();
        bed.setBounds(180, 330, 270, 35);
        bed.setOpaque(false);
        bed.setBorderPainted(false);
        bed.setBorder(null);
        bed.setFocusPainted(false);
        bed.addMouseListener(this);

        wallRubble = new JButton();
        wallRubble.setBounds(320, 210, 125, 125);
        wallRubble.setOpaque(false);
        wallRubble.setBorderPainted(false);
        wallRubble.setBorder(null);
        wallRubble.setFocusPainted(false);
        wallRubble.addMouseListener(this);

        trashImg = new ImageIcon(url.imageGetter("trashbin.png"));
        thrashBin = new JButton(trashImg);
        thrashBin.setBounds(60, 400, 50, 50);
        thrashBin.setOpaque(false);
        thrashBin.setBorderPainted(false);
        thrashBin.setBorder(null);
        thrashBin.setFocusPainted(false);
        thrashBin.addMouseListener(this);

        desk = new JButton();
        desk.setBounds(50, 280, 120, 100);
        desk.setOpaque(false);
        desk.setBorderPainted(false);
        desk.setBorder(null);
        desk.setFocusPainted(false);
        desk.addMouseListener(this);
    }

    public void createCenterWestPanel() {
        centerWestPanel = new JPanel();
        centerWestPanel.setOpaque(false);
        centerWestPanel.setPreferredSize(new Dimension(300, 500));
        createInventoryPanel();
        createRoomInventoryList();
        createNPCList();
        centerWestPanel.add(roomInvList);
        centerWestPanel.add(npcInvList);
        centerWestPanel.add(reactionList);
        centerWestPanel.add(itemInvList);
        centerWestPanel.add(playerInvList);
        itemInvList.setSize(100, 100);
        reactionList.setSize(100, 100);
        playerInvList.setSize(100, 100);
        npcInvList.setSize(100, 100);
        centerWestPanel.add(npcInvList);
        centerWestPanel.add(inventoryPanel, BorderLayout.SOUTH);
    }

    public void createInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(300, 300));
        inventoryPanel.setLayout(new FlowLayout());
        inventoryPanel.setOpaque(false);
        inventoryItemList();
        inventoryPanel.add(invItem[1]);
        inventoryPanel.add(invItem[2]);
        inventoryPanel.add(invItem[3]);
        inventoryPanel.add(invItem[4]);
    }

    public void inventoryItemList() {
        chocolate = new ImageIcon(url.imageGetter("chocolate.png"));
        brick = new ImageIcon(url.imageGetter("brickImg.png"));
        orangeKey = new ImageIcon(url.imageGetter("orangeKey.png"));
        bedSpring = new ImageIcon(url.imageGetter("bedSpring.png"));
        invItem[1] = new JLabel(brick);
        invItem[2] = new JLabel(chocolate);
        invItem[3] = new JLabel(bedSpring);
        invItem[4] = new JLabel(orangeKey);
    }

    public void updateInventory() {
        if (hasBrick == 0) {
            invItem[1].setVisible(false);
        }
        if (hasBrick == 1) {
            invItem[1].setVisible(true);
        }
        if (hasChocolate == 0) {
            invItem[2].setVisible(false);
        }
        if (hasChocolate == 1) {
            invItem[2].setVisible(true);
        }
        if (hasBedSpring == 0) {
            invItem[3].setVisible(false);
        }
        if (hasBedSpring == 1) {
            invItem[3].setVisible(true);
        }
        if (hasOrangeKey == 0) {
            invItem[4].setVisible(false);
        }
        if (hasOrangeKey == 1) {
            invItem[4].setVisible(true);
        }
    }

    public void createRoomInventoryList() {
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory"), InventoryGlobal.getRoomInvList());
        for (String item : InventoryGlobal.getRoomInvList()) {
            inv.addElement(item);
        }
        roomInvList = new JList<>(inv);
        roomInvList.setSize(150, 200);
        roomInvList.setFont(new Font("arial", Font.BOLD, 16));
    }

    public void createNPCList() {
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.key(node.get("room").get(playerLocation).get("NPCs"), InventoryGlobal.npcList);

        for (String item : InventoryGlobal.npcList) {
            npcInv.addElement(item);
        }
        npcInvList = new JList<>(npcInv);
        npcInvList.setSize(150, 250);
        npcInvList.setFont(new Font("arial", Font.BOLD, 16));
    }

    //    public void createItemList(){
//        String playerLocation = player.getCurrentLocation();
//        KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory"));
//        for (String item: InventoryGlobal.roomInvList){
//            inv.addElement(item);
//        }
//        roomInvList = new JList<>(inv);
//        roomInvList.setBounds(100, 100, 75, 75);
//    }
    public void createCenterSouthPanel() {
        centerSouthPanel = new JPanel();
        centerSouthPanel.setOpaque(false);
        centerSouthPanel.setPreferredSize(new Dimension(1200, 120));

        textBackground = new ImageIcon(url.imageGetter("textBGImage.png"));
        textBGPic = new JLabel(textBackground);
        textBGPic.setSize(1200, 120);
        //textBGPic.setLayout(new GridLayout(1, 2));

        createActionInfoArea();
        createPlayerActionPanel();

        textBGPic.add(actionFieldPanel, BorderLayout.NORTH);
        textBGPic.add(playerActionPanel, BorderLayout.SOUTH);
        centerSouthPanel.add(textBGPic);
    }

    public void createActionInfoArea() {
        actionFieldPanel = new JPanel();
        actionFieldPanel.setOpaque(false);
        actionFieldPanel.setBounds(300, 0, 600, 80);
        text = String.join(" ", player.getLastAction());
        actionField = new JTextArea(5, 30);
        actionField.setFont(new Font("SansSerif Bold", Font.BOLD, 19));
        actionField.setBounds(300, 0, 600, 80);
        actionField.setBackground(Color.white);
        actionField.setForeground(Color.red);
        actionField.setText(text);
        actionField.setLineWrap(true);
        actionField.setWrapStyleWord(true);
        actionField.setOpaque(false);
        actionField.setEditable(true);
        actionFieldPanel.add(actionField);
    }

    public void createPlayerActionPanel() {
        playerActionPanel = new JPanel();
        playerActionPanel.setBounds(0, 70, 1200, 70);
        playerActionPanel.setOpaque(false);
        playerActionPanel.setLayout(new FlowLayout());
        createButtonActionPallet();
        playerActionPanel.add(take);
        playerActionPanel.add(look);
        playerActionPanel.add(attack);
        playerActionPanel.add(use);
        playerActionPanel.add(unlock);
        playerActionPanel.add(heal);
        playerActionPanel.add(talk);
        playerActionPanel.add(equip);
        playerActionPanel.add(drop);

    }

    public void createButtonActionPallet() {
        take = new JButton("Take");
        take.setFont(new Font("Arial", Font.BOLD, 20));
        take.setSize(50, 50);
        take.addActionListener(this);
        look = new JButton("Look");
        look.setFont(new Font("Arial", Font.BOLD, 20));
        look.setSize(50, 50);
        look.addActionListener(this);
        attack = new JButton("Attack");
        attack.setFont(new Font("Arial", Font.BOLD, 20));
        attack.setSize(50, 50);
        attack.addActionListener(this);
        use = new JButton("Use");
        use.setFont(new Font("Arial", Font.BOLD, 20));
        use.setSize(50, 50);
        use.addActionListener(this);
        unlock = new JToggleButton("Unlock");
        unlock.setFont(new Font("Arial", Font.BOLD, 20));
        unlock.setSize(50, 50);
        unlock.addActionListener(this::toggle);
        heal = new JButton("Heal");
        heal.setFont(new Font("Arial", Font.BOLD, 20));
        heal.setSize(50, 50);
        heal.addActionListener(this);
        talk = new JButton("Talk");
        talk.setFont(new Font("Arial", Font.BOLD, 20));
        talk.setSize(50, 50);
        talk.addActionListener(this);
        equip = new JButton("Equip");
        equip.setFont(new Font("Arial", Font.BOLD, 20));
        equip.setSize(50, 50);
        equip.addActionListener(this);
        drop = new JButton("Drop");
        drop.setFont(new Font("Arial", Font.BOLD, 20));
        drop.setSize(50, 50);
        drop.addActionListener(this);
    }

    public void createCenterEastPanels() {
        centerEastPanel = new JPanel();
        centerEastPanel.setOpaque(false);
        centerEastPanel.setPreferredSize(new Dimension(300, 600));
        //centerEastPanel.setLayout(new BorderLayout());
        //activeMap();
        flipMap();
        createDirectionalButtons();
        centerEastPanel.add(mapPanelLocation[1], BorderLayout.NORTH);
        centerEastPanel.add(directionalPanel, BorderLayout.SOUTH);

    }

    public void drawMap() {
        playerMap1.build();
        playerMap2.build();
        String playerLocation = player.getCurrentLocation();

        if (node.get("room").get(playerLocation).get("Phase").intValue() == 1) {
            playerMap1.positionUpdate(player, locationVar);
            playerMap1.show();
        } else {
            playerMap2.positionUpdate(player, locationVar);
            playerMap2.show();
        }

    }

    public void flipMap() {
        mapPanelLocation[1] = new JPanel();
        mapPanelLocation[1].setLayout(null);
        mapPanelLocation[1].setOpaque(false);
        mapPanelLocation[1].setPreferredSize(new Dimension(300, 200));
        mapPanelLocation[1].setLayout(null);


    }
//    public void activeMap() {
//        mapSupportPanel = new JPanel();
//        mapSupportPanel.setOpaque(false);
//        mapSupportPanel.setPreferredSize(new Dimension(300, 200));
//        JTextArea mapArea = new JTextArea();
//        mapArea.setSize(300, 200);
//        mapArea.setLineWrap(true);
//        mapArea.setWrapStyleWord(true);
//    }

    public void createDirectionalButtons() {
        directionalPanel = new JPanel();
        directionalPanel.setBackground(new Color(0, 0, 0, 100));
        directionalPanel.setPreferredSize(new Dimension(300, 300));
        directionalPanel.setLayout(new BorderLayout());
        directionalPanel.setOpaque(false);
        northImg = new ImageIcon(url.imageGetter("north.png"));
        southImg = new ImageIcon(url.imageGetter("south.png"));
        eastImg = new ImageIcon(url.imageGetter("east.png"));
        westImg = new ImageIcon(url.imageGetter("west.png"));

        north = new JButton(northImg);
        north.setPreferredSize(new Dimension(80, 120));
        north.setOpaque(false);
        north.setBorderPainted(false);
        north.setBorder(null);
        north.setFocusPainted(false);
        north.addActionListener(this);
        north.setActionCommand("north");
//        north.setEnabled(false);

        south = new JButton(southImg);
        south.setOpaque(false);
        south.setBorderPainted(false);
        south.setBorder(null);
        south.setFocusPainted(false);
        south.setPreferredSize(new Dimension(80, 120));
        south.addActionListener(this);
        south.setActionCommand("south");
//        south.setEnabled(false);


        east = new JButton(eastImg);
        east.setBorderPainted(false);
        east.setOpaque(false);
        east.setBorder(null);
        east.setPreferredSize(new Dimension(80, 120));
        east.setFocusPainted(false);
        east.addActionListener(this);
        east.setActionCommand("east");


        west = new JButton(westImg);
        west.setOpaque(false);
        west.setBorderPainted(false);
        west.setBorder(null);
        west.setPreferredSize(new Dimension(80, 120));
        west.setFocusPainted(false);
        west.addActionListener(this);
        west.setActionCommand("west");
//        west.setEnabled(false);
        //movePlayer();
        directionalPanel.add(east, BorderLayout.EAST);
        directionalPanel.add(west, BorderLayout.WEST);
        directionalPanel.add(north, BorderLayout.NORTH);
        directionalPanel.add(south, BorderLayout.SOUTH);

    }

    public void locationStatus() {
        location.setText("Location: " + player.getCurrentLocation());
        weapon.setText(" || Weapon: " + player.getWeapon());
        HP.setText(" || HP: " + player.getHitPoints());
        disguised.setText(" || Disguised: " + player.getEquipment());
    }

    public void playerInvStatus() {
        // Remove prior first
        // Update player inv list
        InventoryGlobal.getRoomInvList().clear();
        inv.clear();
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory"), InventoryGlobal.getRoomInvList());
        for (String item : InventoryGlobal.getRoomInvList()) {
            inv.addElement(item);
        }
        if (inv.isEmpty()) {
            reactionInv.clear();
            InventoryGlobal.reactionList.clear();
            InventoryGlobal.itemList.clear();
            itemInv.clear();
        }
        InventoryGlobal.npcList.clear();

        npcInv.clear();
        KeyValueParser.key(node.get("room").get(playerLocation).get("NPCs"), InventoryGlobal.npcList);
        for (String item : InventoryGlobal.npcList) {
            npcInv.addElement(item);
            System.out.println(item);
        }
    }

    public void movePlayer() {
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.locationKeyValue(node.get("room").get(playerLocation).get("Moves"), player, doors);
    }
////        for (String item : InventoryGlobal.locationList) {
//////            System.out.println(item);
//////        }
//
//        if (InventoryGlobal.locationList.contains(north.getActionCommand())) {
//            north.setEnabled(true);
//        } else {
//            north.setEnabled(false);
//        }
//        if (InventoryGlobal.locationList.contains(south.getActionCommand())) {
//            south.setEnabled(true);
//        } else {
//            south.setEnabled(false);
//        }
//        if (InventoryGlobal.locationList.contains(east.getActionCommand())) {
//            east.setEnabled(true);
//        } else {
//            east.setEnabled(false);
//        }
//        if (InventoryGlobal.locationList.contains(west.getActionCommand())) {
//            west.setEnabled(true);
//        } else {
//            west.setEnabled(false);
//        }
//    }

    public void resetNpcHP() {
        if (player.getCurrentLocation().equals("North Hall - 2F")) {
            InventoryGlobal.enemyHP = 40;
        } else {
            InventoryGlobal.enemyHP = 30;
        }
    }

    public void toggle(ActionEvent actionEvent) {
        AbstractButton abstractButton =
                (AbstractButton) actionEvent.getSource();

        // return true or false according to the
        // selection or deselection of the button
        selected = abstractButton.getModel().isSelected();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == north) {
            if (selected) {
                player.unlock("door", "north", locationVar, doors);
                selected = false;
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } else {
                player.move("north", locationVar, doors);
                System.out.println(player.getFoundItems() + " Peter ");
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                locationStatus();
                playerInvStatus();
                resetNpcHP();
                if (player.getCurrentLocation().equals("Cage 1")){
                    SouthToCage1();
                }
                if (player.getCurrentLocation().equals("South Hall")){
                    southNpcCageToSouthHall();
                }
                if (player.getCurrentLocation().equals("Southwest Hall")){
                    StorageToSWHall();
                }
                if (player.getCurrentLocation().equals("West Hall")){
                    SWHallToWestHall();
                }
                if (player.getCurrentLocation().equals("North Hall")){
                    WestHallToNorthHall();
                }
                if (player.getCurrentLocation().equals("NPC Cage 2")){
                    NorthHallToNorthCage();
                }
                //////////2nd Floor//////////
                if (player.getCurrentLocation().equals("West Vent")){
                    SWHall2FToWVent();//
                }

                if (player.getCurrentLocation().equals("Storage Room - 2F")){
                    WVentToStorageRoom2f();
                }

                if (player.getCurrentLocation().equals("North West Vent")){
                    StorageRoom2fToNWVent();
                }
                if (player.getCurrentLocation().equals("North East Vent")){
                    NEHall2fToNEVent();
                }
                if (player.getCurrentLocation().equals("North Hall - 2F")){
                    corridorToNorthHall2F();//
                }
                if (player.getCurrentLocation().equals("Central Corridor - 2F")){
                    southHall2FToCorridor();//
                }
                if (player.getCurrentLocation().equals("South Hall - 2F")){
                    breakRoomToSouthHall();//
                }
            }
        } else if (e.getSource() == south) {
            if (selected) {
                player.unlock("door", "south", locationVar, doors);
                selected = false;
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } else {
                player.move("south", locationVar, doors);
                System.out.println("directions from " + player.getCurrentLocation());
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                locationStatus();
                playerInvStatus();
                resetNpcHP();
                if (player.getCurrentLocation().equals("NPC Cage 1")){
                    SouthHallToSouthNpcCage();
                }
                if (player.getCurrentLocation().equals("South Hall")){
                    cage1ToSouthHall();
                }
                if (player.getCurrentLocation().equals("Southwest Hall")){
                    WestHallToSWHall();

                }
                if (player.getCurrentLocation().equals("West Hall")){
                    NorthHallToWestHall();//
                }
                if (player.getCurrentLocation().equals("Storage Room")){
                    SWHallToStorage();//

                }
                if (player.getCurrentLocation().equals("North Hall")){
                    NorthCageToNorthHall();

                }

                //////////2nd Floor//////////


                if (player.getCurrentLocation().equals("West Vent")){
                    storageRoom2fToWVent();//
                }

                if (player.getCurrentLocation().equals("Storage Room - 2F")){
                    NWVentToStorageRoom2f();
                }

                if (player.getCurrentLocation().equals("North East Hall - 2F")){
                    NEVentToNEHall2f();
                }
                if (player.getCurrentLocation().equals("South West Hall - 2F")){
                    WVentToSWHall2F();//
                }
                if (player.getCurrentLocation().equals("Central Corridor - 2F")){
                    northHall2FToCorridor();//
                }
                if (player.getCurrentLocation().equals("South Hall - 2F")){
                    corridorToSouthHall2F();//
                }
                if (player.getCurrentLocation().equals("Break Room - 2F")){
                    southHall2FToBreakRoom();//
                }
            }

        } else if (e.getSource() == east) {
            if (selected) {
                player.unlock("door", "east", locationVar, doors);
                selected = false;
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } else {
                player.move("east", locationVar, doors);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                locationStatus();
                playerInvStatus();
                resetNpcHP();
                if (player.getCurrentLocation().equals("West Hall")){
                    cage1ToWestHall();
                }
                if (player.getCurrentLocation().equals("Southwest Hall")){
                    SouthToSWHall();
                }
                if (player.getCurrentLocation().equals("East Hall")){
                    NorthHallToEastHall();
                }
                if (player.getCurrentLocation().equals("South West Hall - 2F")){
                    EastHallToSWHall2F();
                }
                if (player.getCurrentLocation().equals("South Hall - 2F")){
                    SWestHall2FToSouthHall2F();
                }
                if (player.getCurrentLocation().equals("North East Hall - 2F")){
                    NorthHall2FToNorthEastHall();
                }
                ////
                if (player.getCurrentLocation().equals("North Vent")){
                    NWVentToNVent();
                }
                if (player.getCurrentLocation().equals("North East Vent")){
                    NVentToNEVent();
                }
                if (player.getCurrentLocation().equals("North Hall - 2F")){
                    storage2FToNorthHall();
                }
                if (player.getCurrentLocation().equals("Exit")) {
                    actionField.setText("Congratulations! You escaped the Cage!!!" +
                            "\nTry to be mindful when pumping gas in the future...");
                    winGame();
                    north.setVisible(false);
                    south.setVisible(false);
                    east.setVisible(false);
                    west.setVisible(false);
                }
            }
        } else if (e.getSource() == west) {
            if (selected) {
                player.unlock("door", "west", locationVar, doors);
                selected = false;
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } else {
                player.move("west", locationVar, doors);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                locationStatus();
                playerInvStatus();
                resetNpcHP();
                if (player.getCurrentLocation().equals("Cage 1")){
                    WestHallToCage1();
                }
                if (player.getCurrentLocation().equals("South Hall")){
                    SWHallToSouthHall();
                }
                if (player.getCurrentLocation().equals("North Hall")){
                    EastHallToNorthHall();
                }
                if (player.getCurrentLocation().equals("East Hall")){
                    SWHall2FToEastHall();
                }
                ///
                if (player.getCurrentLocation().equals("South West Hall - 2F")){
                    southHall2FToSWHall2F();//
                }

                if (player.getCurrentLocation().equals("Storage Room - 2F")){
                    NorthHallToStorageRoom2F();
                }

                if (player.getCurrentLocation().equals("North Vent")){
                    NEVentToNVent();
                }
                if (player.getCurrentLocation().equals("North West Vent")){
                    NVentToNWVent();
                }
                if (player.getCurrentLocation().equals("North Hall - 2F")){
                    northEastHall2fToNorthHall2F();//
                }
            }
        }

        String val = roomInvList.getSelectedValue();
        if (e.getSource() == look) {
            try {
                player.look(val, locationVar);
                text = node.get("room").get(player.getCurrentLocation()).get("Inventory").get(val).get("description").textValue();
                actionField.setText(text);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //populate Jlist for the items seen in the rooms
            String playerLocation = player.getCurrentLocation();
            try {
                KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory").get(val).get("items"), InventoryGlobal.reactionList);
                for (String item : InventoryGlobal.reactionList) {
                    if (!reactionInv.contains(item)) {
                        reactionInv.addElement(item);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == use) {
            try {
                String child = reactionList.getSelectedValue();
                player.use(child, val, locationVar);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                // Check for use of items and open the directions
                //Display found items at this point
                for (Item item : player.getFoundItems()) {
                    //If item found is key, unlock the door
                    if (item.getName().equals("brick")) {
                        door.setLocked(false);
                        movePlayer();
                        text = player.getLastAction().get(player.getLastAction().size() - 1);
                        actionField.setText(text);
                        playerInvStatus();

                    }
                    if (!itemInv.contains(item.getName())) {
                        itemInv.addElement(item.getName());
                    }
                }
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        }

        if (e.getSource() == take) {
            try {
                String child = itemInvList.getSelectedValue();
                player.take(child, "", locationVar);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
//                if (InventoryGlobal.itemList.contains(itemInvList.getSelectedValue())) {
//                    // TODO: don't let player take it twice
//                    playerInv.add(itemInvList.getSelectedIndex(), child);
//                }
                if (!playerInv.contains(child)) {
                    playerInv.add(itemInvList.getSelectedIndex(), child);
                }

            } catch (Exception a) {
                a.printStackTrace();
            }
        }

        if (e.getSource() == attack) {
            try {
                String name = npcInvList.getSelectedValue();
                String[] npcName = name.split(" ", 0);
                String firstName = npcName[1];
                String lastName = npcName[0];
                player.attack(firstName, lastName, locationVar);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                topPanel.repaint();

                if (InventoryGlobal.enemyHP <= 0) {

                    for (Item foundItem : player.getFoundItems()) {
                        if (foundItem.getName().equals("red key") ||
                                foundItem.getName().equals("chocolate bar") ||
                                foundItem.getName().equals("wallet") ||
                                foundItem.getName().equals("ladder") ||
                                foundItem.getName().equals("ladder") ||
                                foundItem.getName().equals("s cola") ||
                                foundItem.getName().equals("purple key") ||
                                foundItem.getName().equals("boss key")) {
                            if (!playerInv.contains(foundItem.getName())) {
                                playerInv.addElement(foundItem.getName());
                            }

                        }

                    }
                }

            } catch (Exception ea) {
                ea.printStackTrace();
            }
        }

        if (e.getSource() == talk) {

            try {
                String name = npcInvList.getSelectedValue();
                String[] npcName = name.split(" ", 0);
                String firstName = npcName[1];
                String lastName = npcName[0];
                player.talk(firstName, lastName, locationVar);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } catch (Exception ab) {
                ab.printStackTrace();
            }

        }

        if (e.getSource() == equip) {

            try {
                player.equip();
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);

            } catch (Exception ac) {
                ac.printStackTrace();
            }

        }

        if (e.getSource() == heal) {
            String heal = playerInvList.getSelectedValue();
            player.heal(heal);
            text = player.getLastAction().get(player.getLastAction().size() - 1);
            actionField.setText(text);
        }
        if (e.getSource() == drop) {
            String drop = playerInvList.getSelectedValue();
            player.drop(drop, " ", locationVar);
            playerInv.removeElement(drop);
            text = player.getLastAction().get(player.getLastAction().size() - 1);
            actionField.setText(text);

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
        String windowInfo = "<html>Heavily reinforced metal, <br> " +
                "maybe you can wiggle and <br> " +
                "'use' the 'window bars</html>";
        String trashBinInfo = "<html>a small trash bin...maybe <br> " +
                "you can 'use' the 'trash bin' <br> " +
                "to find something useful...</html>";
        String bedInfo = "<html>Full-size bed sits directly under window <br> " +
                "with uncomfortable mattress with exposed loose spring. <br>" +
                "Maybe 'use' the 'bed mattress' and get a spring ... </html>";
        String wallRubbleInfo = "<html>The wall seems to be falling apart, <br>" +
                "maybe you can 'use' the 'wall rubble' <br>" +
                "to find something useful.</html>";
        String deskInfo = "<html>Old worn out wooden desk with a 'desk drawer' you may be able to 'use'</html>";

        UIManager.put("ToolTip.foreground", Color.PINK);
        UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));
        if (e.getSource() == windowBars) {
            windowBars.setToolTipText(windowInfo);
        }
        if (e.getSource() == bed) {
            bed.setToolTipText(bedInfo);
        }
        if (e.getSource() == thrashBin) {
            thrashBin.setToolTipText(trashBinInfo);
        }
        if (e.getSource() == desk) {
            desk.setToolTipText(deskInfo);
        }
        if (e.getSource() == wallRubble) {
            wallRubble.setToolTipText(wallRubbleInfo);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void cage1ToWestHall() {
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(true);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
        //2=west hall
    }

    public void WestHallToCage1() {
        gameWindow[1].setVisible(true);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void WestHallToNorthHall() {
        gameWindow[2].setVisible(false);
        gameWindow[5].setVisible(true);//5=north hall
        gameWindow[1].setVisible(false);
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NorthHallToWestHall() {
        gameWindow[5].setVisible(false);
        gameWindow[2].setVisible(true);
        gameWindow[1].setVisible(false);
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NorthHallToNorthCage() {
        gameWindow[5].setVisible(false);
        gameWindow[8].setVisible(true);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NorthCageToNorthHall() {
        gameWindow[5].setVisible(true);
        gameWindow[8].setVisible(false);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NorthHallToEastHall() {
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(true);//6 = east hall
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void EastHallToNorthHall() {
        gameWindow[5].setVisible(true);
        gameWindow[6].setVisible(false);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    /////
    public void WestHallToSWHall() {
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(true);//3 = southwest hall
        gameWindow[1].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SWHallToWestHall() {
        gameWindow[2].setVisible(true);
        gameWindow[3].setVisible(false);
        gameWindow[1].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SWHallToStorage() {
        gameWindow[3].setVisible(false);
        gameWindow[9].setVisible(true); //9 = storage room
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void StorageToSWHall() {
        gameWindow[3].setVisible(true);
        gameWindow[9].setVisible(false);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[4].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SWHallToSouthHall() {
        gameWindow[3].setVisible(false);
        gameWindow[4].setVisible(true);//4 = south hall
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SouthToSWHall() {
        gameWindow[4].setVisible(false);
        gameWindow[3].setVisible(true);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SouthToCage1() {
        gameWindow[4].setVisible(false);
        gameWindow[1].setVisible(true);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void cage1ToSouthHall() {
        gameWindow[4].setVisible(true);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[7].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void southNpcCageToSouthHall() {
        gameWindow[4].setVisible(true);
        gameWindow[7].setVisible(false);// 7= south cage
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SouthHallToSouthNpcCage() {
        gameWindow[4].setVisible(false);
        gameWindow[7].setVisible(true);
        gameWindow[1].setVisible(false);
        gameWindow[2].setVisible(false);
        gameWindow[3].setVisible(false);
        gameWindow[5].setVisible(false);
        gameWindow[6].setVisible(false);
        gameWindow[8].setVisible(false);
        gameWindow[9].setVisible(false);
        gameWindow[10].setVisible(false);
        gameWindow[11].setVisible(false);
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(false);
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);
        gameWindow[17].setVisible(false);
        gameWindow[18].setVisible(false);
        gameWindow[19].setVisible(false);
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void EastHallToSWHall2F() {
        gameWindow[6].setVisible(false);
        gameWindow[10].setVisible(true);//10 = 2FL South West hall
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SWHall2FToEastHall() {
        gameWindow[6].setVisible(true);
        gameWindow[10].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void SWestHall2FToSouthHall2F() {
        gameWindow[11].setVisible(true);//
        gameWindow[10].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void southHall2FToSWHall2F() {
        gameWindow[10].setVisible(true);
        gameWindow[11].setVisible(false);// 11=South Hall 2nd FL
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void southHall2FToBreakRoom() {
        gameWindow[11].setVisible(false);// 11=South Hall 2nd FL
        gameWindow[21].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
    }

    public void breakRoomToSouthHall() {
        gameWindow[11].setVisible(true);// 11=South Hall 2nd FL
        gameWindow[21].setVisible(false);//20 = break room
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
    }

    public void southHall2FToCorridor() {
        gameWindow[11].setVisible(false);// 11=South Hall 2nd FL
        gameWindow[12].setVisible(true);//12=corridor
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void corridorToSouthHall2F() {
        gameWindow[11].setVisible(true);//
        gameWindow[12].setVisible(false);//
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void corridorToNorthHall2F() {
        gameWindow[12].setVisible(false);
        gameWindow[13].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);

    }

    public void northHall2FToCorridor() {
        gameWindow[12].setVisible(true);
        gameWindow[13].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NorthHall2FToNorthEastHall() {
        gameWindow[13].setVisible(false);
        gameWindow[14].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void northEastHall2fToNorthHall2F() {
        gameWindow[13].setVisible(true);
        gameWindow[14].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NEHall2fToNEVent() {
        gameWindow[14].setVisible(false);
        gameWindow[18].setVisible(true);//north east vent
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1

        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1

        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NEVentToNEHall2f() {
        gameWindow[14].setVisible(true);
        gameWindow[18].setVisible(false);//north east vent
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1

        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NEVentToNVent() {
        gameWindow[18].setVisible(false);
        gameWindow[17].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NVentToNEVent() {
        gameWindow[18].setVisible(true);
        gameWindow[17].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NVentToNWVent() {
        gameWindow[17].setVisible(false);
        gameWindow[16].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NWVentToNVent() {
        gameWindow[17].setVisible(true);
        gameWindow[16].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);

        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void NWVentToStorageRoom2f() {
        gameWindow[16].setVisible(false);
        gameWindow[20].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);

        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1

        gameWindow[21].setVisible(false);
    }

    public void StorageRoom2fToNWVent() {
        gameWindow[16].setVisible(true);
        gameWindow[20].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);

        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1

        gameWindow[21].setVisible(false);
    }

    public void storageRoom2fToWVent() {
        gameWindow[20].setVisible(false);
        gameWindow[15].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1

        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1

        gameWindow[21].setVisible(false);
    }
    public void WVentToStorageRoom2f() {
        gameWindow[20].setVisible(true);
        gameWindow[15].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1

        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1

        gameWindow[21].setVisible(false);
    }
    public void storage2FToNorthHall(){
        gameWindow[20].setVisible(false);
        gameWindow[13].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1

        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1

        gameWindow[21].setVisible(false);
    }
    public void NorthHallToStorageRoom2F(){
        gameWindow[20].setVisible(true);
        gameWindow[13].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1

        gameWindow[14].setVisible(false);//1=cage1
        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1

        gameWindow[21].setVisible(false);
    }

    public void WVentToSWHall2F() {
        gameWindow[15].setVisible(false);
        gameWindow[10].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1

        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1

        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }
    public void SWHall2FToWVent(){
        gameWindow[15].setVisible(true);
        gameWindow[10].setVisible(false);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1

        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1
        gameWindow[14].setVisible(false);//1=cage1

        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1
        gameWindow[19].setVisible(false);//1=cage1
        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    public void winGame() {
        gameWindow[14].setVisible(false);
        gameWindow[19].setVisible(true);
        gameWindow[1].setVisible(false);//1=cage1
        gameWindow[2].setVisible(false);//1=cage1
        gameWindow[3].setVisible(false);//1=cage1
        gameWindow[4].setVisible(false);//1=cage1
        gameWindow[5].setVisible(false);//1=cage1
        gameWindow[6].setVisible(false);//1=cage1
        gameWindow[7].setVisible(false);//1=cage1
        gameWindow[8].setVisible(false);//1=cage1
        gameWindow[9].setVisible(false);//1=cage1
        gameWindow[10].setVisible(false);//1=cage1
        gameWindow[11].setVisible(false);//1=cage1
        gameWindow[12].setVisible(false);//1=cage1
        gameWindow[13].setVisible(false);//1=cage1

        gameWindow[15].setVisible(false);
        gameWindow[16].setVisible(false);//1=cage1
        gameWindow[17].setVisible(false);//1=cage1
        gameWindow[18].setVisible(false);//1=cage1

        gameWindow[20].setVisible(false);
        gameWindow[21].setVisible(false);
    }

    //////////////////////
    public void panelForCinematic() {
        //First Lest HALLS
        gameWindow[2] = new JPanel();
        gameWindow[2].setPreferredSize(new Dimension(600, 500));
        gameWindow[2].setLayout(null);
        gameWindow[2].setOpaque(false);

        westHall = new ImageIcon(url.imageGetter("westHall.jpeg"));
        gameLabel[2] = new JLabel(westHall);
        gameLabel[2].setBounds(0, 0, 600, 500);
        gameWindow[2].add(gameLabel[2]);

        gameWindow[3] = new JPanel();
        gameWindow[3].setPreferredSize(new Dimension(600, 500));
        gameWindow[3].setLayout(null);
        gameWindow[3].setOpaque(false);

        southWestHall = new ImageIcon(url.imageGetter("southWestHall.png"));
        gameLabel[3] = new JLabel(southWestHall);
        gameLabel[3].setBounds(0, 0, 600, 500);
        gameWindow[3].add(gameLabel[3]);

        gameWindow[4] = new JPanel();
        gameWindow[4].setPreferredSize(new Dimension(600, 500));
        gameWindow[4].setLayout(null);
        gameWindow[4].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[4] = new JLabel(southHall);
        gameLabel[4].setBounds(0, 0, 600, 500);
        gameWindow[4].add(gameLabel[4]);

        gameWindow[5] = new JPanel();
        gameWindow[5].setPreferredSize(new Dimension(600, 500));
        gameWindow[5].setLayout(null);
        gameWindow[5].setOpaque(false);

        northHall = new ImageIcon(url.imageGetter("northHall.png"));
        gameLabel[5] = new JLabel(northHall);
        gameLabel[5].setBounds(0, 0, 600, 500);
        gameWindow[5].add(gameLabel[5]);

        gameWindow[6] = new JPanel();
        gameWindow[6].setPreferredSize(new Dimension(600, 500));
        gameWindow[6].setLayout(null);
        gameWindow[6].setOpaque(false);

        eastHall = new ImageIcon(url.imageGetter("eastHall.png"));
        gameLabel[6] = new JLabel(eastHall);
        gameLabel[6].setBounds(0, 0, 600, 500);
        gameWindow[6].add(gameLabel[6]);
////////1st floor Cages - Rooms/////

        gameWindow[7] = new JPanel();
        gameWindow[7].setPreferredSize(new Dimension(600, 500));
        gameWindow[7].setLayout(null);
        gameWindow[7].setOpaque(false);

        southCage = new ImageIcon(url.imageGetter("southCageInmate.png"));
        gameLabel[7] = new JLabel(southCage);
        gameLabel[7].setBounds(0, 0, 600, 500);
        gameWindow[7].add(gameLabel[7]);

        gameWindow[8] = new JPanel();
        gameWindow[8].setPreferredSize(new Dimension(600, 500));
        gameWindow[8].setLayout(null);
        gameWindow[8].setOpaque(false);

        northCage = new ImageIcon(url.imageGetter("northCageInmate.png"));
        gameLabel[8] = new JLabel(northCage);
        gameLabel[8].setBounds(0, 0, 600, 500);
        gameWindow[8].add(gameLabel[8]);

        gameWindow[9] = new JPanel();
        gameWindow[9].setPreferredSize(new Dimension(600, 500));
        gameWindow[9].setLayout(null);
        gameWindow[9].setOpaque(false);

        storageRoom = new ImageIcon(url.imageGetter("storageroom.jpeg"));
        gameLabel[9] = new JLabel(storageRoom);
        gameLabel[9].setBounds(0, 0, 600, 500);
        gameWindow[9].add(gameLabel[9]);
        //////2nd Floor halls and vents////

        gameWindow[10] = new JPanel();
        gameWindow[10].setPreferredSize(new Dimension(600, 500));
        gameWindow[10].setLayout(null);
        gameWindow[10].setOpaque(false);

        southWestHall2F = new ImageIcon(url.imageGetter("southWestHall2F.png"));
        gameLabel[10] = new JLabel(southWestHall2F);
        gameLabel[10].setBounds(0, 0, 600, 500);
        gameWindow[10].add(gameLabel[10]);

        gameWindow[11] = new JPanel();
        gameWindow[11].setPreferredSize(new Dimension(600, 500));
        gameWindow[11].setLayout(null);
        gameWindow[11].setOpaque(false);

        southHall2F = new ImageIcon(url.imageGetter("southHall2F.png"));
        gameLabel[11] = new JLabel(southHall2F);
        gameLabel[11].setBounds(0, 0, 600, 500);
        gameWindow[11].add(gameLabel[11]);

        gameWindow[12] = new JPanel();
        gameWindow[12].setPreferredSize(new Dimension(600, 500));
        gameWindow[12].setLayout(null);
        gameWindow[12].setOpaque(false);

        prisonCorridor = new ImageIcon(url.imageGetter("prisonCorridor.png"));
        gameLabel[12] = new JLabel(prisonCorridor);
        gameLabel[12].setBounds(0, 0, 600, 500);
        gameWindow[12].add(gameLabel[12]);

        gameWindow[13] = new JPanel();
        gameWindow[13].setPreferredSize(new Dimension(600, 500));
        gameWindow[13].setLayout(null);
        gameWindow[13].setOpaque(false);

        northHall2F = new ImageIcon(url.imageGetter("northHall2F.png"));
        gameLabel[13] = new JLabel(northHall2F);
        gameLabel[13].setBounds(0, 0, 600, 500);
        gameWindow[13].add(gameLabel[13]);

        gameWindow[14] = new JPanel();
        gameWindow[14].setPreferredSize(new Dimension(600, 500));
        gameWindow[14].setLayout(null);
        gameWindow[14].setOpaque(false);

        northEast2F = new ImageIcon(url.imageGetter("northEastHall2ndFL.jpeg"));
        gameLabel[14] = new JLabel(northEast2F);
        gameLabel[14].setBounds(0, 0, 600, 500);
        gameWindow[14].add(gameLabel[14]);
////
        gameWindow[15] = new JPanel();
        gameWindow[15].setPreferredSize(new Dimension(600, 500));
        gameWindow[15].setLayout(null);
        gameWindow[15].setOpaque(false);

        ventW = new ImageIcon(url.imageGetter("vent1.png"));
        gameLabel[15] = new JLabel(ventW);
        gameLabel[15].setBounds(0, 0, 600, 500);
        gameWindow[15].add(gameLabel[15]);

        gameWindow[16] = new JPanel();
        gameWindow[16].setPreferredSize(new Dimension(600, 500));
        gameWindow[16].setLayout(null);
        gameWindow[16].setOpaque(false);

        ventNW = new ImageIcon(url.imageGetter("vent2.png"));
        gameLabel[16] = new JLabel(ventNW);
        gameLabel[16].setBounds(0, 0, 600, 500);
        gameWindow[16].add(gameLabel[16]);

        gameWindow[17] = new JPanel();
        gameWindow[17].setPreferredSize(new Dimension(600, 500));
        gameWindow[17].setLayout(null);
        gameWindow[17].setOpaque(false);

        ventN = new ImageIcon(url.imageGetter("vent2.png"));
        gameLabel[17] = new JLabel(ventN);
        gameLabel[17].setBounds(0, 0, 600, 500);
        gameWindow[17].add(gameLabel[17]);

        gameWindow[18] = new JPanel();
        gameWindow[18].setPreferredSize(new Dimension(600, 500));
        gameWindow[18].setLayout(null);
        gameWindow[18].setOpaque(false);

        ventNE = new ImageIcon(url.imageGetter("vent1.png"));
        gameLabel[18] = new JLabel(ventNE);
        gameLabel[18].setBounds(0, 0, 600, 500);
        gameWindow[18].add(gameLabel[18]);
//////LEVEL 2 Rooms and Exit//////
        gameWindow[19] = new JPanel();
        gameWindow[19].setPreferredSize(new Dimension(600, 500));
        gameWindow[19].setLayout(null);
        gameWindow[19].setOpaque(false);

        prisonExit = new ImageIcon(url.imageGetter("escapedTheGame.png"));
        gameLabel[19] = new JLabel(prisonExit);
        gameLabel[19].setBounds(0, 0, 600, 500);
        gameWindow[19].add(gameLabel[19]);

        gameWindow[20] = new JPanel();
        gameWindow[20].setPreferredSize(new Dimension(600, 500));
        gameWindow[20].setLayout(null);
        gameWindow[20].setOpaque(false);

        storage2F = new ImageIcon(url.imageGetter("2ndFloorStorage.png"));
        gameLabel[20] = new JLabel(storage2F);
        gameLabel[20].setBounds(0, 0, 600, 500);
        gameWindow[20].add(gameLabel[20]);

        gameWindow[21] = new JPanel();
        gameWindow[21].setPreferredSize(new Dimension(600, 500));
        gameWindow[21].setLayout(null);
        gameWindow[21].setOpaque(false);

        breakRoom = new ImageIcon(url.imageGetter("breakRoom.png"));
        gameLabel[21] = new JLabel(breakRoom);
        gameLabel[21].setBounds(0, 0, 600, 500);
        gameWindow[21].add(gameLabel[21]);
    }

    public void mapGenerator() {
        mapPanelLocation[2] = new JPanel();
        mapPanelLocation[2].setLayout(null);
        mapPanelLocation[2].setOpaque(false);

        westHall = new ImageIcon(url.imageGetter("westHall.jpeg"));
        maps[2] = new JLabel(westHall);
        maps[2].setBounds(0, 0, 600, 500);

        mapPanelLocation[2] = new JPanel();
        mapPanelLocation[3].setLayout(null);
        mapPanelLocation[3].setOpaque(false);

        southWestHall = new ImageIcon(url.imageGetter("southHall.png"));
        maps[3] = new JLabel(southWestHall);
        maps[3].setBounds(0, 0, 600, 500);

        mapPanelLocation[4] = new JPanel();
        mapPanelLocation[4].setLayout(null);
        mapPanelLocation[4].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        maps[4] = new JLabel(southHall);
        maps[4].setBounds(0, 0, 600, 500);

        mapPanelLocation[5] = new JPanel();
        mapPanelLocation[5].setLayout(null);
        mapPanelLocation[5].setOpaque(false);

        northHall = new ImageIcon(url.imageGetter("northHall.png"));
        maps[5] = new JLabel(northHall);
        maps[5].setBounds(0, 0, 600, 500);

        mapPanelLocation[6] = new JPanel();
        mapPanelLocation[6].setLayout(null);
        mapPanelLocation[6].setOpaque(false);

        eastHall = new ImageIcon(url.imageGetter("eastHall.png"));
        maps[6] = new JLabel(eastHall);
        maps[6].setBounds(0, 0, 600, 500);
////////1st floor Cages - Rooms/////
        mapPanelLocation[7] = new JPanel();
        mapPanelLocation[7].setLayout(null);
        mapPanelLocation[7].setOpaque(false);

        southCage = new ImageIcon(url.imageGetter("southCageInmate.png"));
        maps[7] = new JLabel(southCage);
        maps[7].setBounds(0, 0, 600, 500);

        mapPanelLocation[8] = new JPanel();
        mapPanelLocation[8].setLayout(null);
        mapPanelLocation[8].setOpaque(false);

        northCage = new ImageIcon(url.imageGetter("northCageInmate.png"));
        maps[8] = new JLabel(northCage);
        maps[8].setBounds(0, 0, 600, 500);

        mapPanelLocation[9] = new JPanel();
        mapPanelLocation[9].setLayout(null);
        mapPanelLocation[9].setOpaque(false);

        storageRoom = new ImageIcon(url.imageGetter("storageroom.jpeg"));
        maps[9] = new JLabel(storageRoom);
        maps[9].setBounds(0, 0, 600, 500);
        //////2nd Floor halls and vents////
        mapPanelLocation[10] = new JPanel();
        mapPanelLocation[10].setLayout(null);
        mapPanelLocation[10].setOpaque(false);

        southWestHall2F = new ImageIcon(url.imageGetter("southWestHall2F.png"));
        maps[10] = new JLabel(southWestHall2F);
        maps[10].setBounds(0, 0, 600, 500);

        mapPanelLocation[11] = new JPanel();
        mapPanelLocation[11].setLayout(null);
        mapPanelLocation[11].setOpaque(false);

        southHall2F = new ImageIcon(url.imageGetter("southHall2F.png"));
        maps[11] = new JLabel(southHall2F);
        maps[11].setBounds(0, 0, 600, 500);

        mapPanelLocation[12] = new JPanel();
        mapPanelLocation[12].setLayout(null);
        mapPanelLocation[12].setOpaque(false);

        prisonCorridor = new ImageIcon(url.imageGetter("prisonCorridor.png"));
        maps[12] = new JLabel(prisonCorridor);
        maps[12].setBounds(0, 0, 600, 500);

        mapPanelLocation[13] = new JPanel();
        mapPanelLocation[13].setLayout(null);
        mapPanelLocation[13].setOpaque(false);

        northHall2F = new ImageIcon(url.imageGetter("northHall.png"));
        maps[13] = new JLabel(northHall2F);
        maps[13].setBounds(0, 0, 600, 500);

        mapPanelLocation[14] = new JPanel();
        mapPanelLocation[14].setLayout(null);
        mapPanelLocation[14].setOpaque(false);

        northEast2F = new ImageIcon(url.imageGetter("northEastHall2ndFL.jpeg"));
        maps[14] = new JLabel(northEast2F);
        maps[14].setBounds(0, 0, 600, 500);
////
        mapPanelLocation[15] = new JPanel();
        mapPanelLocation[15].setLayout(null);
        mapPanelLocation[15].setOpaque(false);

        ventW = new ImageIcon(url.imageGetter("vent1.png"));
        maps[15] = new JLabel(ventW);
        maps[15].setBounds(0, 0, 600, 500);

        mapPanelLocation[16] = new JPanel();
        mapPanelLocation[16].setLayout(null);
        mapPanelLocation[16].setOpaque(false);

        ventNW = new ImageIcon(url.imageGetter("vent2.png"));
        maps[16] = new JLabel(ventNW);
        maps[16].setBounds(0, 0, 600, 500);

        mapPanelLocation[17] = new JPanel();
        mapPanelLocation[17].setLayout(null);
        mapPanelLocation[17].setOpaque(false);

        ventN = new ImageIcon(url.imageGetter("vent2.png"));
        maps[17] = new JLabel(ventN);
        maps[17].setBounds(0, 0, 600, 500);

        mapPanelLocation[18] = new JPanel();
        mapPanelLocation[18].setLayout(null);
        mapPanelLocation[18].setOpaque(false);

        ventNE = new ImageIcon(url.imageGetter("vent1.png"));
        maps[18] = new JLabel(ventNE);
        maps[18].setBounds(0, 0, 600, 500);
//////LEVEL 2 Rooms and Exit//////
        mapPanelLocation[19].setLayout(null);
        mapPanelLocation[19].setOpaque(false);

        prisonExit = new ImageIcon(url.imageGetter("escapedTheGame.png"));
        maps[19] = new JLabel(prisonExit);
        maps[19].setBounds(0, 0, 600, 500);

        mapPanelLocation[20].setLayout(null);
        mapPanelLocation[20].setOpaque(false);

        storage2F = new ImageIcon(url.imageGetter("2ndFloorStorage.png"));
        maps[20] = new JLabel(storage2F);
        maps[20].setBounds(0, 0, 600, 500);

        mapPanelLocation[21].setLayout(null);
        mapPanelLocation[21].setOpaque(false);

        breakRoom = new ImageIcon(url.imageGetter("breakRoom.png"));
        maps[21] = new JLabel(breakRoom);
        maps[21].setBounds(0, 0, 600, 500);
    }
   //hello
    //mm
}
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
import java.lang.Character;
import java.util.List;
import java.util.Objects;

public class PlayWindow extends JPanel implements MouseListener, ActionListener {
    private FileGetter url = new FileGetter();
    private MusicPlayer gameMusic = new MusicPlayer();

    int bgNum;

    public int playerMaxLife;
    public int playerLife;

    public int hasOrangeKey;
    public int hasChocolate;
    public int hasBedSpring;
    public int hasBrick;
    public int hasGuardUniform;
    public int hasKeyCard;

    JPanel inventoryPanel;
    JPanel playerActionPanel;
    JTextArea actionField;
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
    private ImageIcon SouthWest2F, south2F, centralCorridor, north2F, northEast2F, ventW, ventNW, ventN, ventNE;
    //MAIN Panel
    private JPanel panel, bottomPanel, topPanel, centerPanel;
    private JPanel[] gameWindow = new JPanel[15];
    private JLabel[] gameLabel = new JLabel[15];
    private JLabel backgroundPic, textBGPic;
    //TOP PANEL///
    private JLabel location, weapon, HP, disguised;
    private JButton help;
    private JToggleButton volume;
    private JSlider minMaxVolume;
    private ImageIcon displayImage, textBackground;
    //BOTTOM///
    private JButton quitBtn;
    ImageIcon lifeIcon;
    //CENTER PANEL///
    private JPanel centerSouthPanel, centerEastPanel, centerWestPanel;
    //CENTER BOTTOM PANEL
    private JPanel actionFieldPanel;
    private JButton take, look, talk, attack, use, equip, heal, drop;
    private JToggleButton unlock;
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

    private String text;
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
    private ImageIcon chocolate, brick, orangeKey, bedSpring;
    private JPanel lifePanel;
    private JLabel[] heartLabel = new JLabel[4];


    public PlayWindow(JFrame frame) {
        gameMusic.setFile("gamePlaySong.wav");
        gameMusic.play();
        gameMusic.loopSound();
        topPanel(frame);
        bottomPanel(frame);
        centerPanel(frame);
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

        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        quitBtn.setBounds(600, 350, 150, 100);
        quitBtn.addActionListener(e -> {
            int userInput = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the the game?",
                    "Caged", JOptionPane.YES_NO_OPTION);
            if (userInput == 0) {
                gameMusic.turnOff();
                panel.setVisible(false);
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

    public void centerPanel(JFrame frame) {
        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 620);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        cinematicBackground();
        createCenterEastPanels();
        createCenterSouthPanel();
        createCenterWestPanel();
        centerPanel.add(gameWindow[1], BorderLayout.CENTER);
        centerPanel.add(centerEastPanel, BorderLayout.EAST);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);
        centerPanel.add(centerWestPanel, BorderLayout.WEST);
    }


    public void cinematicBackground() {
        gameWindow[1] = new JPanel();
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
        //windowBars.setOpaque(false);
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
        activeMap();
        createDirectionalButtons();
        centerEastPanel.add(mapSupportPanel, BorderLayout.NORTH);
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

    public void activeMap() {
        mapSupportPanel = new JPanel();
        mapSupportPanel.setOpaque(false);
        mapSupportPanel.setPreferredSize(new Dimension(300, 200));
        JTextArea mapArea = new JTextArea();
        mapArea.setSize(300, 200);
        mapArea.setLineWrap(true);
        mapArea.setWrapStyleWord(true);
    }

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
//        east.setEnabled(false);

        west = new JButton(westImg);
        west.setOpaque(false);
        west.setBorderPainted(false);
        west.setBorder(null);
        west.setPreferredSize(new Dimension(80, 120));
        west.setFocusPainted(false);
        west.addActionListener(this);
        west.setActionCommand("west");
//        west.setEnabled(false);
        directionalPanel.add(east, BorderLayout.EAST);
        directionalPanel.add(west, BorderLayout.WEST);
        directionalPanel.add(north, BorderLayout.NORTH);
        directionalPanel.add(south, BorderLayout.SOUTH);
        movePlayer();
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

    private void movePlayer() {
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.locationKeyValue(node.get("room").get(playerLocation).get("Moves"), player, doors);
    }

    public void resetNpcHP(){
        if (player.getCurrentLocation().equals("North Hall - 2F")){
            InventoryGlobal.enemyHP = 40;
        }else {
            InventoryGlobal.enemyHP = 30;
        }
    }

    // Creates toggle listener
    boolean selected = false; // this is used by the actionPerformed listener for directional functions

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
                player.unlock(" ", "north", locationVar, doors);
                selected = false;
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } else {
                player.move("north", locationVar, doors);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                locationStatus();
                playerInvStatus();
                resetNpcHP();
            }
        } else if (e.getSource() == south) {
            if (selected) {
                player.unlock(" ", "south", locationVar, doors);
                selected = false;
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
            } else {
                player.move("south", locationVar, doors);
                text = player.getLastAction().get(player.getLastAction().size() - 1);
                actionField.setText(text);
                locationStatus();
                playerInvStatus();
                resetNpcHP();
            }
        } else if (e.getSource() == east) {
            if (selected) {
                player.unlock(" ", "east", locationVar, doors);
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
                if (player.getCurrentLocation().equals("Exit")) {
                    // THIS IS WHERE WE SHOW THE VICTORY SCREEN!!!
                }
            }
        } else if (e.getSource() == west) {
            if (selected) {
                player.unlock(" ", "west", locationVar, doors);
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
                if(!playerInv.contains(child)){
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
        if(e.getSource()  == drop){
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

    public void generatePictureLocations() {
        //1-5 are HALLS
        gameWindow[2].setLayout(null);
        gameWindow[2].setOpaque(false);

        westHall = new ImageIcon(url.imageGetter("westHall.jpeg"));
        gameLabel[2] = new JLabel(westHall);
        gameLabel[2].setBounds(0, 0, 600, 500);

        gameWindow[3].setLayout(null);
        gameWindow[3].setOpaque(false);

        southWestHall = new ImageIcon(url.imageGetter("SouthWestHall.bmp"));
        gameLabel[3] = new JLabel(southWestHall);
        gameLabel[3].setBounds(0, 0, 600, 500);

        gameWindow[4].setLayout(null);
        gameWindow[4].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[4] = new JLabel(southHall);
        gameLabel[4].setBounds(0, 0, 600, 500);

        gameWindow[5].setLayout(null);
        gameWindow[5].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[5] = new JLabel(southHall);
        gameLabel[5].setBounds(0, 0, 600, 500);

        gameWindow[6].setLayout(null);
        gameWindow[6].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[6] = new JLabel(southHall);
        gameLabel[6].setBounds(0, 0, 600, 500);

        gameWindow[7].setLayout(null);
        gameWindow[7].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[7] = new JLabel(southHall);
        gameLabel[7].setBounds(0, 0, 600, 500);

        gameWindow[8].setLayout(null);
        gameWindow[8].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[8] = new JLabel(southHall);
        gameLabel[8].setBounds(0, 0, 600, 500);

        gameWindow[9].setLayout(null);
        gameWindow[9].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[9] = new JLabel(southHall);
        gameLabel[9].setBounds(0, 0, 600, 500);

        gameWindow[10].setLayout(null);
        gameWindow[10].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[10] = new JLabel(southHall);
        gameLabel[10].setBounds(0, 0, 600, 500);

        gameWindow[11].setLayout(null);
        gameWindow[11].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[11] = new JLabel(southHall);
        gameLabel[11].setBounds(0, 0, 600, 500);

        gameWindow[12].setLayout(null);
        gameWindow[12].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[12] = new JLabel(southHall);
        gameLabel[12].setBounds(0, 0, 600, 500);

        gameWindow[13].setLayout(null);
        gameWindow[13].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[13] = new JLabel(southHall);
        gameLabel[13].setBounds(0, 0, 600, 500);

        gameWindow[14].setLayout(null);
        gameWindow[14].setOpaque(false);

        southHall = new ImageIcon(url.imageGetter("southHall.png"));
        gameLabel[14] = new JLabel(southHall);
        gameLabel[14].setBounds(0, 0, 600, 500);


    }
    //Caged got us locked up
}
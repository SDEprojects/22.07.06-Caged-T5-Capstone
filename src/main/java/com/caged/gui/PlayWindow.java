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

    JPanel inventoryPanel;
    JPanel playerActionPanel;

    ImageIcon northImg, southImg, eastImg, westImg;

    ImageIcon bedImg, wallImg, windowImg, deskImg, scene1;

    JTextArea actionField;

    JButton north, south, east, west;
    JButton inv1, inv2, inv3, inv4;
    JButton take, look, talk, attack, use, unlock, equip, heal;

    //MAIN Panel
    JPanel panel, bottomPanel, topPanel, centerPanel;
    JPanel[] gameWindow = new JPanel[15];
    JLabel[] gameLabel = new JLabel[15];
    JLabel backgroundPic, textBGPic;
    //TOP PANEL///
    JLabel location, weapon, HP, disguised;
    JButton help;
    JToggleButton volume;
    JSlider minMaxVolume;
    ImageIcon displayImage, textBackground;
    //BOTTOM///
    JButton quitBtn;
    //CENTER PANEL///
    JPanel centerMidPanel, centerSouthPanel, centerEastPanel, centerWestPanel;
    JLabel mapLabel;

    DefaultListModel<String> inv = new DefaultListModel<>();
    DefaultListModel<String> reactionInv = new DefaultListModel<>();
    DefaultListModel<String> itemInv = new DefaultListModel<>();
    DefaultListModel<String> playerInv = new DefaultListModel<>();

    JList<String> itemInvList = new JList<>(itemInv);
    JList<String> reactionList = new JList<>(reactionInv);
    JList<String> playerInvList = new JList<>(playerInv);
    JList<String> roomInvList;
    JList<String> npcInvList;

    String text;

    FileGetter url = new FileGetter();
    MusicPlayer gameMusic = new MusicPlayer();

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
    private JPanel directionalPanel;
    private JPanel[] mapPanels = new JPanel[20];
    private JPanel actionFieldPanel;

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

        displayImage = new ImageIcon(url.imageGetter("gamePlayBG.jpeg"));
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

        minMaxVolume = new JSlider(-60, 6);
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
        bottomPanel.setBounds(0, 700, 1200, 150);
        bottomPanel.setOpaque(false);

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
        bottomPanel.add(quitBtn);
    }

    public void centerPanel(JFrame frame) {
        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 620);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        createCenterMidPanel();
        createCenterEastPanels();
        createCenterSouthPanel();
        createCenterWestPanel();
        centerPanel.add(centerMidPanel, BorderLayout.CENTER);
        centerPanel.add(centerEastPanel, BorderLayout.EAST);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);
        centerPanel.add(centerWestPanel, BorderLayout.WEST);
    }

    public void createCenterMidPanel() {
        centerMidPanel = new JPanel();
        centerMidPanel.setLayout(new BorderLayout());
        centerMidPanel.setOpaque(false);
        cinematicBackground();
        centerMidPanel.add(gameWindow[1]);
    }

    public void cinematicBackground() {
        gameWindow[1] = new JPanel();
        gameWindow[1].setBounds(0, 0, 600, 600);
        scene1 = new ImageIcon(url.imageGetter("prisonCell.png"));
        mapLabel = new JLabel(scene1);

        gameWindow[1].add(mapLabel);
    }


    public void createCenterWestPanel() {
        centerWestPanel = new JPanel();
        centerWestPanel.setOpaque(false);
        centerWestPanel.setPreferredSize(new Dimension(300, 500));
        createInventoryPanel();
        createRoomInventoryList();
        createNPCList();
        centerWestPanel.add(roomInvList);
        centerWestPanel.add(reactionList);
        centerWestPanel.add(itemInvList);
        centerWestPanel.add(playerInvList);
        itemInvList.setSize(100, 100);
        reactionList.setSize(100, 100);
        playerInvList.setSize(100, 100);
        centerWestPanel.add(npcInvList);
        centerWestPanel.add(inventoryPanel, BorderLayout.SOUTH);
    }

    public void createInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(300, 200));
        inventoryPanel.setLayout(new FlowLayout());
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
        windowImg = new ImageIcon(url.imageGetter("window.jpg"));
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
        DefaultListModel<String> inv = new DefaultListModel<>();
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.key(node.get("room").get(playerLocation).get("NPCs"), InventoryGlobal.npcList);

        for (String item : InventoryGlobal.npcList) {
            inv.addElement(item);
        }
        npcInvList = new JList<>(inv);
        npcInvList.setBounds(100, 100, 75, 75);
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
        unlock = new JButton("Unlock");
        unlock.setFont(new Font("Arial", Font.BOLD, 20));
        unlock.setSize(50, 50);
        unlock.addActionListener(this);
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
            playerInv.clear();
        }
    }

    private void movePlayer() {
        String playerLocation = player.getCurrentLocation();
        KeyValueParser.locationKeyValue(node.get("room").get(playerLocation).get("Moves"), player, doors);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == north) {
            player.move("north", locationVar, doors);
            text = player.getLastAction().get(player.getLastAction().size() - 1);
            actionField.setText(text);
            locationStatus();
            playerInvStatus();
        } else if (e.getSource() == south) {
            player.move("south", locationVar, doors);
            text = player.getLastAction().get(player.getLastAction().size() - 1);
            actionField.setText(text);
            locationStatus();
            playerInvStatus();
        } else if (e.getSource() == east) {
            player.move("east", locationVar, doors);
            text = player.getLastAction().get(player.getLastAction().size() - 1);
            actionField.setText(text);
            locationStatus();
            playerInvStatus();
        } else if (e.getSource() == west) {
            player.move("west", locationVar, doors);
            text = player.getLastAction().get(player.getLastAction().size() - 1);
            actionField.setText(text);
            locationStatus();
            playerInvStatus();
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
                if (InventoryGlobal.itemList.contains(itemInvList.getSelectedValue())) {
                    // TODO: don't let player take it twice
                    playerInv.add(itemInvList.getSelectedIndex(), child);
                }

            } catch (Exception a) {
                a.printStackTrace();
            }
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
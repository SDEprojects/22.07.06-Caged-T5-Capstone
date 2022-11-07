package com.caged.gui;

import com.caged.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class TestMainWindow implements ActionListener, MouseListener {
    private JFrame frame;
    //private JLayeredPane layeredPane;
    private JPanel panel;
    private JButton newGameBtn;
    private JButton quitBtn;
    private ImageIcon displayImage;
    private JLabel label;
    private JLabel confirmLabel;
    private FileGetter url = new FileGetter();

    //PlayWindow//////////////////////////////////////////////////////////////////
    private JPanel playPanel, topPanel, centerPanel, bottomPanel, inventoryPanel;
    private JPanel centerEastPanel, centerWestPanel, centerMidPanel,centerSouthPanel;

    private JLabel label2;
    private ImageIcon displayImage2;
    private ImageIcon northImg,southImg,eastImg,westImg;
    private ImageIcon bedImg, wallImg, windowImg,deskImg,map;

    private JLabel location, weapon, HP, disguised, mapLabel;

    private JTextArea actionField, hoverText;

    private JButton help, quitBtn2;
    private JButton north,south,east,west;
    private JButton inv1,inv2,inv3,inv4;

    private JToggleButton volume;

    private FileGetter url2 = new FileGetter();

    // GAME VARIABLES
    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    //List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths
    MusicPlayer gameMusic = new MusicPlayer();
    private JLayeredPane layeredPane;

    public void executeMainWindow(){
        createFrame();
        frame.setVisible(true);
//        mainPanel();
//        createBtns();
//        createLabel();
        //panelHandler();
    }
    public void createFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The Caged");
        frame.setSize(1200 , 900);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBounds(0,0,1200,900);
        panel.setLayout(null);
        frame.getContentPane().add(panel);

        displayImage = new ImageIcon(url.imageGetter("MainGameDIsplay.jpg"));
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);
        panel.add(label);


        newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(this);
        quitBtn  = new JButton("Quit");
        quitBtn.addActionListener(this);
        newGameBtn.setBounds(380, 400, 100, 50);
        quitBtn.setBounds(500, 400, 100 ,50);
        label.add(newGameBtn);
        label.add(quitBtn);



        /////////PLAY PANEL//////////////
        playPanel = new JPanel();
        playPanel.setSize(1200, 900);
        playPanel.setLayout(null);



        displayImage2 = new ImageIcon(url2.imageGetter("MainGameDIsplay.jpg"));
        label2 = new JLabel(displayImage2);
        label2.setBounds(0, 0, 1200, 900);
        playPanel.add(label2);

        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(false);
        playPanel.add(topPanel);

        centerPanel = new JPanel();
        centerPanel.setBounds(0, 70, 1200, 600);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
        playPanel.add(centerPanel);

        centerEastPanel = new JPanel();
        centerEastPanel.setOpaque(false);
        centerEastPanel.setPreferredSize(new Dimension(300,480));
        centerEastPanel.setLayout(new BorderLayout());
        centerEastPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerPanel.add(centerEastPanel, BorderLayout.EAST);

        centerMidPanel = new JPanel();
        centerMidPanel.setLayout(new BorderLayout());
        centerMidPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerMidPanel.setOpaque(false);
        centerPanel.add(centerMidPanel, BorderLayout.CENTER);

        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(600, 200));
        inventoryPanel.setLayout( new FlowLayout(FlowLayout.CENTER, 15, 40));
        inventoryPanel.setOpaque(false);
        centerMidPanel.add(inventoryPanel, BorderLayout.NORTH);

        bedImg = new ImageIcon("resources/bed.png");
        inv1 = new JButton(bedImg);
        inv1.setOpaque(false);
        inv1.setBorderPainted(false);
        inv1.setBorder(null);
        inv1.setFocusPainted(false);
        inv1.addMouseListener(this);
        inventoryPanel.add(inv1);

        wallImg = new ImageIcon("resources/wall.jpg");
        inv2 = new JButton(wallImg);
        inv2.setOpaque(false);
        inv2.setBorderPainted(false);
        inv2.setBorder(null);
        inv2.addMouseListener(this);
        inventoryPanel.add(inv2);

        windowImg = new ImageIcon("resources/window.jpg");
        inv3 = new JButton(windowImg);
        inv3.setOpaque(false);
        inv3.setBorderPainted(false);
        inv3.setBorder(null);
        inv3.addMouseListener(this);
        inventoryPanel.add(inv3);

        deskImg = new ImageIcon("resources/desk.jpeg");
        inv4 = new JButton(deskImg);
        inv4.setOpaque(false);
        inv4.setBorderPainted(false);
        inv4.setBorder(null);
        inv4.addMouseListener(this);
        inventoryPanel.add(inv4);

        map = new ImageIcon("resources/map.jpeg");
        mapLabel = new JLabel(map);
        centerMidPanel.add(mapLabel, BorderLayout.CENTER);

        centerWestPanel = new JPanel();
        centerWestPanel.setOpaque(false);
        centerWestPanel.setPreferredSize(new Dimension(300,480));
        centerWestPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerPanel.add(centerWestPanel, BorderLayout.WEST);

        centerSouthPanel = new JPanel();
        centerSouthPanel.setOpaque(false);
        centerSouthPanel.setPreferredSize(new Dimension(1200,120));
        centerSouthPanel.setLayout(new FlowLayout());
        centerSouthPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);

        northImg = new ImageIcon(url2.imageGetter("north.png"));
        north = new JButton(northImg);
        north.setPreferredSize(new Dimension(100,190));
        north.setOpaque(false);
        north.setBorderPainted(false);
        north.setBorder(null);
        north.setFocusPainted(false);
        centerEastPanel.add(north, BorderLayout.NORTH);

        southImg = new ImageIcon(url2.imageGetter("south.png"));
        south = new JButton(southImg);
        south.setOpaque(false);
        south.setBorderPainted(false);
        south.setBorder(null);
        south.setFocusPainted(false);
        south.setPreferredSize(new Dimension(100,190));
        centerEastPanel.add(south, BorderLayout.SOUTH);

        eastImg = new ImageIcon(url2.imageGetter("east.png"));
        east = new JButton(eastImg);
        east.setBorderPainted(false);
        east.setOpaque(false);
        east.setBorder(null);
        east.setPreferredSize(new Dimension(130,195));
        east.setFocusPainted(false);
        centerEastPanel.add(east, BorderLayout.EAST );

        westImg = new ImageIcon(url2.imageGetter("west.png"));
        west = new JButton(westImg);
        west.setOpaque(false);
        west.setBorderPainted(false);
        west.setBorder(null);
        west.setPreferredSize(new Dimension(130,195));
        west.setFocusPainted(false);
        centerEastPanel.add(west, BorderLayout.WEST);

        location = new JLabel("Location: " + player.getCurrentLocation());
        location.setForeground(Color.ORANGE);
        topPanel.add(location);

        weapon = new JLabel(" || Weapon: " + player.getWeapon());
        weapon.setForeground(Color.ORANGE);
        topPanel.add(weapon);


        HP = new JLabel(" || HP: " + player.getHitPoints());
        HP.setForeground(Color.ORANGE);
        topPanel.add(HP);

        disguised = new JLabel(" || Disguised: " + player.getEquipment());
        disguised.setForeground(Color.ORANGE);
        topPanel.add(disguised);

        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 700, 1200, 90);
        bottomPanel.setOpaque(false);
        playPanel.add(bottomPanel);

        help = new JButton("Help");
        topPanel.add(help);

        quitBtn2 = new JButton("Quit Game");
        quitBtn2.addActionListener(this);
        bottomPanel.add(quitBtn2);

        volume = new JToggleButton("Music ON");
        volume.setForeground(Color.GREEN);
        gameMusic.play();
        volume.addActionListener(this);
        topPanel.add(volume);

        String text = "You  awaken and found yourself crying in the cage!";
        actionField = new JTextArea(50, 40);
        actionField.setFont(new Font("Arial", Font.BOLD, 14));
        actionField.setText(text);
        actionField.setLineWrap(true);
        actionField.setWrapStyleWord(true);
        actionField.setOpaque(false);
        actionField.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
        centerSouthPanel.add(actionField);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGameBtn){
            frame.removeAll();
            //frame.getContentPane().add(playPanel);
            frame.repaint();
            frame.revalidate();




        }
        if(e.getSource() == quitBtn){
            int userInput=  JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?", "Caged", JOptionPane.YES_NO_OPTION);
            if(userInput == 0){
                frame.dispose();
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

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TestMainWindow window= new TestMainWindow();
                window.executeMainWindow();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}

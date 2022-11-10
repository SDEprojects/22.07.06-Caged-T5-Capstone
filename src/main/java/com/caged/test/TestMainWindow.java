package com.caged.test;

import com.caged.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class TestMainWindow extends JPanel{
    //PlayWindow//////////////////////////////////////////////////////////////////
    private JPanel playPanel, topPanel, centerPanel, bottomPanel, inventoryPanel;
    private JPanel centerEastPanel, centerWestPanel, centerMidPanel, centerSouthPanel;

    private JLabel label2;
    private ImageIcon displayImage2;
    private ImageIcon northImg, southImg, eastImg, westImg;
    private ImageIcon bedImg, wallImg, windowImg, deskImg, map;

    private JLabel location, weapon, HP, disguised, mapLabel;

    private JTextArea actionField, hoverText;

    private JButton help, quitBtn2;
    private JButton north, south, east, west;
    private JButton inv1, inv2, inv3, inv4;

    private JToggleButton volume;

    private FileGetter url2 = new FileGetter();

    // GAME VARIABLES
    YAMLReader yamlReader = new YAMLReader(); //initiates the yaml loader
    Player player = yamlReader.playerLoader(); //sets default player stats
    LocationGetter locationVar = yamlReader.locationLoader(); //TODO: used for map update
    //List<Doors> doors = yamlReader.doorLoader(); // TODO: used for viewable paths
    MusicPlayer gameMusic = new MusicPlayer();

    public TestMainWindow(JFrame frame){
        createFrame(frame);
        frame.setVisible(true);
    }

    public void createFrame(JFrame frame){
        /////////PLAY PANEL//////////////
        playPanel = new JPanel();

        playPanel.setLayout(null);


        displayImage2 = new ImageIcon(url2.imageGetter("MainGameDIsplay.jpg"));
        label2 = new JLabel(displayImage2);
        label2.setBounds(0, 0, 1200, 900);


        topPanel = new JPanel();
        topPanel.setBounds(0, 20, 1200, 50);
        topPanel.setOpaque(false);

        label2.add(topPanel);
        playPanel.add(label2);
        frame.add(playPanel);

    }
}
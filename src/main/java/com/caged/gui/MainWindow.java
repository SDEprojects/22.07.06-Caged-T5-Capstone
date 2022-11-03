package com.caged.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame;
    JButton newGameBtn;
    JButton quitBtn;
    ImageIcon displayImage;
    JLabel label;


    public void execute (){
        createBtns();
        createLabel();
        createFrame();

    }

    public void createFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The Caged");
        frame.setSize(900, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.add(label);
        frame.setVisible(true);
    }

    public void createBtns (){

        newGameBtn = new JButton("New Game");
        quitBtn  = new JButton("Quit");
        newGameBtn.setBounds(380, 400, 100, 50);
        quitBtn.setBounds(500, 400, 100 ,50);

    }

    public void createLabel(){
        displayImage = new ImageIcon("resources/MainGameDisplay.jpg");
        label = new JLabel(displayImage);
        label.setBounds(0,0, 900, 600);
        label.add(newGameBtn);
        label.add(quitBtn);

    }


}

package com.caged.gui;

import com.caged.FileGetter;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MainWindow{
    JFrame frame;
    JButton newGameBtn;
    JButton quitBtn;
    ImageIcon displayImage;
    JLabel label;
    JLabel confirmLabel;
    FileGetter url = new FileGetter();

    public MainWindow(){
        createFrame();
        frame.setVisible(true);
    }

    public void createFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The Caged");
        frame.setSize(1200, 900);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);


        displayImage = new ImageIcon(url.imageGetter("MainGameDIsplay.jpg"));
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);
        frame.add(label);

        newGameBtn = new JButton("New Game");
        newGameBtn.setFont(new Font("Arial",Font.BOLD,20));
        newGameBtn.setBounds(400, 400, 150, 100);
        newGameBtn.addActionListener(e -> {
            frame.dispose();
            new PlayWindow();

        });
        label.add(newGameBtn);

        quitBtn  = new JButton("Quit");
        quitBtn.setFont(new Font("Arial",Font.BOLD,20));
        quitBtn.setBounds(600, 400, 150 ,100);
        quitBtn.addActionListener(e -> {
            int userInput=  JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?",
                    "Caged", JOptionPane.YES_NO_OPTION);
            if(userInput == 0){
                frame.dispose();
            }
        });
        label.add(quitBtn);
    }
}
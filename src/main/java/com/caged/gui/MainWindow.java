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

    public void execute (){
        createBtns();
        createLabel();
        createFrame();

    }

    public void createFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The Caged");
        frame.setSize(1200, 900);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.add(label);
        frame.setVisible(true);
    }

    public void createBtns (){

        newGameBtn = new JButton("New Game");
        newGameBtn.setBounds(400, 400, 150, 100);
        newGameBtn.addActionListener(e -> {
            frame.dispose();
            PlayWindow play = new PlayWindow();
            play.execute();
        });
        quitBtn  = new JButton("Quit");
        quitBtn.setBounds(600, 400, 150 ,100);
        quitBtn.addActionListener(e -> {
            int userInput=  JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?",
                    "Caged", JOptionPane.YES_NO_OPTION);
            if(userInput == 0){
                frame.dispose();
            }
        });
    }

    public void createLabel(){
        displayImage = new ImageIcon(url.imageGetter("Boredom Slayer.jpeg"));
        label = new JLabel(displayImage);
        label.setBounds(0,0, 1200, 900);
        label.add(newGameBtn);
        label.add(quitBtn);

    }
}

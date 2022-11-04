package com.caged.gui;

import com.caged.FileGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MainWindow implements ActionListener {
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
        frame.setSize(900, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.add(label);
        frame.setVisible(true);
    }

    public void createBtns (){

        newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(this);
        quitBtn  = new JButton("Quit");
        quitBtn.addActionListener(this);
        newGameBtn.setBounds(380, 400, 100, 50);
        quitBtn.setBounds(500, 400, 100 ,50);

    }

    public void createLabel(){
        displayImage = new ImageIcon(url.imageGetter("MainGameDIsplay.jpg"));
        label = new JLabel(displayImage);
        label.setBounds(0,0, 900, 600);
        label.add(newGameBtn);
        label.add(quitBtn);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGameBtn){
            frame.dispose();
            PlayWindow play = new PlayWindow();
            play.execute();
        }
        if(e.getSource() == quitBtn){
           int userInput=  JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?", "Caged", JOptionPane.YES_NO_OPTION);
           if(userInput == 0){
               frame.dispose();
           }
        }
    }

}

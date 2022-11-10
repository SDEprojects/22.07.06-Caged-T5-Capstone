package com.caged.gui;

import com.caged.FileGetter;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JPanel{
    JPanel panel;
    JButton newGameBtn;
    JButton quitBtn;
    ImageIcon displayImage;
    JLabel label;

    FileGetter url = new FileGetter();

    public MainWindow(JFrame frame){
        initialize(frame);
        frame.setVisible(true);
    }

    public void initialize(JFrame frame){
        panel = new JPanel();
        panel.setLayout(null);


        displayImage = new ImageIcon(url.imageGetter("prison.jpeg"));
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);

        newGameBtn = new JButton("New Game");
        newGameBtn.setFont(new Font("Arial",Font.BOLD,20));
        newGameBtn.setBounds(400, 400, 150, 100);
        newGameBtn.addActionListener(e -> {
            panel.setVisible(false);
            new PlayWindow(frame);
//            panel.revalidate();
//            panel.repaint();

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
        panel.add(newGameBtn);
        panel.add(quitBtn);
        panel.add(label);

        frame.add(panel);
    }
}
package com.caged.gui;

import com.caged.FileGetter;
import com.caged.MusicPlayer;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JPanel{
    JPanel panel;
    JButton newGameBtn;
    JButton quitBtn;
    ImageIcon displayImage;
    JLabel label, title;
    JTextArea thanksTo;
    FileGetter url = new FileGetter();
    MusicPlayer gameMusic = new MusicPlayer();

    public MainWindow(JFrame frame){
        gameMusic.setFile("MainPageSong.wav");
        gameMusic.play();
        initialize(frame);
        frame.setVisible(true);
    }

    public void initialize(JFrame frame){
        panel = new JPanel();
        panel.setLayout(null);


        displayImage = new ImageIcon(url.imageGetter("caged.png"));
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);

        title = new JLabel("WELCOME TO CAGED");
        title.setFont(new Font("arial", Font.BOLD, 65));
        title.setBounds(250, 440, 800, 60);
        title.setBorder((BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        title.setForeground(Color.red);

        thanksTo = new JTextArea("A game by:\n" +
                "GROUP 1\n" +
                "    &\n" +
                "GROUP5");

        thanksTo.setBounds(525, 550, 200, 500);
        thanksTo.setFont(new Font("SansSerif", Font.BOLD, 20));
        thanksTo.setBackground(Color.white);
        thanksTo.setForeground(Color.red);
        thanksTo.setLineWrap(true);
        thanksTo.setWrapStyleWord(true);
        thanksTo.setOpaque(false);
        thanksTo.setEditable(true);

        newGameBtn = new JButton("New Game");
        newGameBtn.setFont(new Font("Arial",Font.BOLD,20));
        newGameBtn.setBounds(400, 700, 150, 100);
        newGameBtn.addActionListener(e -> {
            panel.setVisible(false);
            gameMusic.turnOff();
            new StoryScreen(frame);
        });
        label.add(newGameBtn);

        quitBtn  = new JButton("Quit");
        quitBtn.setFont(new Font("Arial",Font.BOLD,20));
        quitBtn.setBounds(600, 700, 150 ,100);
        quitBtn.addActionListener(e -> {
            int userInput=  JOptionPane.showConfirmDialog(frame, "Are you your you want to quit?",
                    "Caged", JOptionPane.YES_NO_OPTION);
            if(userInput == 0){
                frame.dispose();
            }
        });
        panel.add(newGameBtn);
        panel.add(quitBtn);
        panel.add(thanksTo);
        panel.add(title);
        panel.add(label);

        frame.add(panel);
    }
}
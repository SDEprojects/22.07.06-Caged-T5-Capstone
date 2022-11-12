package com.caged.gui;

import com.caged.FileGetter;
import com.caged.MusicPlayer;

import javax.swing.*;
import java.awt.*;

public class StoryScreen extends JPanel {

    JTextArea storyWindow;
    private JButton continueButton;
    private ImageIcon displayImage;
    JLabel label;
    FileGetter url = new FileGetter();

    public StoryScreen(JFrame frame) {
        initialize(frame);
    }

    public void initialize(JFrame frame) {

        this.setLayout(null);

        displayImage = new ImageIcon(url.imageGetter("storyBG.png"));
        label = new JLabel(displayImage);
        label.setBounds(0, 0, 1200, 900);

        storyWindow = new JTextArea("You are a successful person with a wonderful family driving home from work." +
                " Your car was running low on gas, so you decided to stop at your local gas station. " +
                " You chose pump 9 to fill up your gas, but the credit card machine was out of order, " +
                " so you had to go inside the building to pay for gas.  As you about to pay…..Darkness…." +
                " You found yourself waking up inside the cell.\n\n" +
                "The object of the game is to escape the building alive to go home to your family\n " +
                "who is waiting excitedly to celebrate your birthday.");
        storyWindow.setSize(1200,600);
        storyWindow.setFont(new Font("SansSerif Bold", Font.BOLD, 40));
        storyWindow.setBackground(Color.white);
        storyWindow.setForeground(Color.red);
        storyWindow.setLineWrap(true);
        storyWindow.setWrapStyleWord(true);
        storyWindow.setOpaque(false);
        storyWindow.setEditable(true);

        continueButton = new JButton("CONTINUE");
        continueButton.setFont(new Font("Arial",Font.BOLD,20));
        continueButton.setBounds(500, 700, 150, 100);
        continueButton.addActionListener(e -> {
            this.setVisible(false);
            //gameMusic.turnOff();
            new PlayWindow(frame);
        });
        this.add(continueButton);
        this.add(storyWindow);
        this.add(label);
        frame.add(this);

    }
}


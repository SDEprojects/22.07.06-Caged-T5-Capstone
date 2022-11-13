package com.caged.gui;


import com.caged.FileGetter;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen {
    JFrame frame;
    JPanel panel;
    JPanel panel1;
    JLabel image, message, title;
    JProgressBar progressBar;
    ImageIcon displayImage;
    FileGetter url = new FileGetter();

    public LoadingScreen() {
        createGUI();
    }

    public void createGUI() {

        frame = new JFrame("Caged");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        //frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        panel = new JPanel();
        panel.setLayout(null);

        panel1 = new JPanel();
        panel1.setLayout(null);


        displayImage = new ImageIcon(url.imageGetter("prisonRelease.gif"));
        image = new JLabel(displayImage);
        image.setSize(1200, 900);


        title = new JLabel("WELCOME TO CAGED");
        title.setFont(new Font("arial", Font.BOLD, 65));
        title.setBounds(250, 440, 800, 60);
        title.setBorder((BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        title.setForeground(Color.red);


        message = new JLabel();
        message.setBounds(525, 540, 200, 40);//Setting the size and location of the label
        message.setForeground(Color.red);//Setting foreground Color
        message.setFont(new Font("arial", Font.BOLD, 20));//Setting font properties


        progressBar = new JProgressBar();//Creating an object of JProgressBar
        progressBar.setBounds(400, 520, 400, 30);//Setting Location and size
        progressBar.setStringPainted(true);//Setting String painted property
        progressBar.setValue(0);//setting progress bar current value


        panel.add(progressBar);
        panel.add(title);
        panel.add(message);
        panel.add(image);
        frame.add(panel);

        frame.setVisible(true);

        int progress = 0;//Creating an integer variable and initializing it to 0

        while (progress <= 100) {

            progressBar.setValue(progress);
            try {
                Thread.sleep(50);
            } catch (Exception ev) {
                ev.printStackTrace();
            }
            progress++;
            if (progress == 100) {
                panel.setVisible(false);
                new MainWindow(frame);
            }
        }
    }
}

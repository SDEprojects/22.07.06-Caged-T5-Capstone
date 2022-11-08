package com.caged.gui;

import com.apple.eawt.Application;
import com.caged.FileGetter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadingScreen {
    JFrame frame;
    JLabel image, message, text;
    JProgressBar progressBar;
    ImageIcon displayImage;
    FileGetter url = new FileGetter();

    public LoadingScreen() throws IOException {
        createGUI();
    }

    public void createGUI() throws IOException {


        frame = new JFrame("Caged");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        displayImage = new ImageIcon("resources/prisonRelease.gif");
        image = new JLabel(displayImage);
        image.setSize(1200, 900);
        frame.add(image);

        text = new JLabel("WELCOME TO CAGED");
        text.setFont(new Font("arial", Font.BOLD, 45));
        text.setBounds(350, 440, 600, 40);
        text.setBorder((BorderFactory.createEmptyBorder(4,4,4,4)));
        text.setForeground(Color.red);
        image.add(text);

        message = new JLabel();
        message.setBounds(525, 540, 200, 40);//Setting the size and location of the label
        message.setForeground(Color.red);//Setting foreground Color
        message.setFont(new Font("arial", Font.BOLD, 20));//Setting font properties
        //message.setVisible(false);
        image.add(message);

        progressBar = new JProgressBar();//Creating an object of JProgressBar
        progressBar.setBounds(400, 520, 400, 30);//Setting Location and size
        progressBar.setBorderPainted(true);//Setting border painted property
        progressBar.setStringPainted(true);//Setting String painted property
        progressBar.setBackground(Color.WHITE);//setting background color
        progressBar.setForeground(Color.BLACK);//setting foreground color
        progressBar.setValue(0);//setting progress bar current value
        //progressBar.setVisible(false)
        image.add(progressBar);

        int i = 0;//Creating an integer variable and intializing it to 0

        while (i <= 100) {
            try {
                Thread.sleep(50);//Pausing execution for 50 milliseconds
                progressBar.setValue(i);//Setting value of Progress Bar
                message.setText("LOADING " + i + "%");//Setting text of the message JLabel

                i++;
                if(i == 100){
                    frame.dispose();
                    new MainWindow();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File dockImage = new File("resources/CagedLogo.png");
        Image image = ImageIO.read(dockImage);
        Application.getApplication().setDockIconImage(image);
    }
}

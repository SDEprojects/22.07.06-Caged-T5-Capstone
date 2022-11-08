package com.caged.gui;

import com.caged.FileGetter;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen {
    MainWindow secondWindow = new MainWindow();
    JFrame frame;
    JLabel image, message, text;
    JProgressBar progressBar;
    ImageIcon displayImage;
    FileGetter url = new FileGetter();


    public void mainFrame() {
        addText();
        addProgressBar();
        addMessage();
        addImage();
        createGUI();
        runningPBar();


    }

    public void createGUI() {
        frame = new JFrame("Caged");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.add(image);
        frame.setVisible(true);
    }

    public void addImage() {
        displayImage = new ImageIcon("resources/prisonRelease.gif");
        image = new JLabel(displayImage);
        image.setSize(1200, 900);
        image.add(text);
        image.add(progressBar);
        image.add(message);

    }

    public void addText() {
        text = new JLabel("WELCOME TO CAGED");
        text.setFont(new Font("arial", Font.BOLD, 45));
        text.setBounds(200, 440, 600, 40);
        text.setBorder((BorderFactory.createEmptyBorder(4,4,4,4)));
        text.setForeground(Color.red);

    }


    public void addMessage() {
        message = new JLabel();
        message.setBounds(400, 540, 200, 40);//Setting the size and location of the label
        message.setForeground(Color.red);//Setting foreground Color
        message.setFont(new Font("arial", Font.BOLD, 20));//Setting font properties
        //message.setVisible(false);

    }

    public void addProgressBar() {
        progressBar = new JProgressBar();//Creating an object of JProgressBar
        progressBar.setBounds(300, 520, 400, 30);//Setting Location and size
        progressBar.setBorderPainted(true);//Setting border painted property
        progressBar.setStringPainted(true);//Setting String painted property
        progressBar.setBackground(Color.WHITE);//setting background color
        progressBar.setForeground(Color.BLACK);//setting foreground color
        progressBar.setValue(0);//setting progress bar current value
        //progressBar.setVisible(false);

    }

    public void runningPBar() {

        int i = 0;//Creating an integer variable and intializing it to 0

        while (i <= 100) {
            try {
                Thread.sleep(50);//Pausing execution for 50 milliseconds
                progressBar.setValue(i);//Setting value of Progress Bar
                message.setText("LOADING " + Integer.toString(i) + "%");//Setting text of the message JLabel

                i++;
                if(i == 100){
                    frame.dispose();
                    secondWindow.execute();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

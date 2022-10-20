package com.caged;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainMenu {

    public void mainMenu(){
        try {
            File myObj = new File("resources/MainMenu.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
package com.caged;

import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class SplashScreen {

    FileGetter fileGetter = new FileGetter();

    public void splash(){
        //File myObj = new File("./resources/cagedsplash.txt");
        Scanner myReader = new Scanner(fileGetter.fileGetter("cagedsplash.txt"));
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
    }
}
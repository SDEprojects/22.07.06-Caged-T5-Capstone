package com.caged;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class MainMenu {

    FileGetter fileGetter = new FileGetter();

    public void mainMenu(){
        //File myObj = new File(Objects.requireNonNull(this.getClass().getResource("")).getPath());
        Scanner myReader = new Scanner(fileGetter.fileGetter("MainMenu.txt"));
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
    }


}
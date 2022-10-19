package com.caged;

import java.io.Console;
import java.util.Scanner;

public class GameControl {

    private boolean playGame = true;
    Scanner in = new Scanner(System.in);

    public void runGame() {
//        Console.clear();
//         research clear method
//        SplashScreen.show();
//        Intro.show();
//        Intro.startOption();
        playGame();
    }

    private void playGame(){
        while (playGame){
            System.out.println("User input:");
            String userChoice = in.nextLine();
            String lowUser = userChoice.toLowerCase();
            if (lowUser.equals("quit")) {
                playGame = false;
            }
        }
    }

}
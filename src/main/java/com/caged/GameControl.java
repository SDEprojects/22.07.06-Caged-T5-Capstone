package com.caged;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GameControl {

    private boolean userInput = false;
    private Scanner scanner = new Scanner(System.in);
    private boolean playGame = true;
    Scanner in = new Scanner(System.in);
    TextParser textParser = new TextParser();
    YAMLReader yamlReader = new YAMLReader();
    //Player player = new Player();

    public void runGame() {
//        Console.clear();
//         research clear method
//        SplashScreen.show();
//        Intro.show();
//        Intro.startOption();
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        try {
//            Player player =  mapper.readValue(new File("./resources/player.yml"), Player.class);
//            System.out.println("New player: " + player.getName());
//            playGame(player);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        yamlReader.introLoader();
        playGame(yamlReader.playerLoader());
    }

    private void playGame(Player player){
        while (playGame){
            System.out.println("\n>>>>");
            String userChoice = in.nextLine();
            String lowUser = userChoice.toLowerCase();
            if (lowUser.equals("quit")) {
                playGame = false;
            }
            else {
                String[] action = textParser.textParser(lowUser);
                player.playerActions(action[0],action[1]);
            }
        }
    }

    public void newGame() {
        while (!userInput) {
            System.out.print("\n>>>> ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("new game")) {
                System.out.println(" #### You are CAGED #### ");    //Game starts - starting with Game Intro

            } else {
                System.out.println("Invalid input, please enter valid input");
                userInput = true;
            }
        }
    }

}
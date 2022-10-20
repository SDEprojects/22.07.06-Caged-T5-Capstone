package com.caged;

import java.util.Scanner;

public class GameControl {

    private boolean userInput = false;
    private final Scanner scanner = new Scanner(System.in);
    private final boolean playGame = true;
    Scanner in = new Scanner(System.in);
    TextParser textParser = new TextParser();
    YAMLReader yamlReader = new YAMLReader();
    SplashScreen splashScreen = new SplashScreen();
    MainMenu mainMenu = new MainMenu();
    Console console = new Console();

    public void runGame() {
        console.clear();
//         research clear method
        splashScreen.splash();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        console.clear();
        mainMenu.mainMenu();
        mainMenuOptions();
        console.clear();
        playGame(yamlReader.playerLoader());
    }

    private void playGame(Player player) {
        while (playGame) {
            System.out.print(">>>>");
            String userChoice = in.nextLine();
            String lowUser = userChoice.toLowerCase();
            if (lowUser.equals("quit")) {
                quitConfirm();
            } else {
                String[] action = textParser.textParser(lowUser);
                player.playerActions(action[0], action[1]);
            }
        }
    }

    public void mainMenuOptions() {
        while (!userInput) {
            System.out.print("\n>>>> ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("new game")) {
                yamlReader.introLoader();
                userInput = true;
            } else if (input.equals("quit")) {
                quitConfirm();
            }
            else {
                System.out.println("Invalid input, please enter valid input");
            }
        }
    }

    public void quitConfirm(){
        System.out.println("Do you really want to quit?");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("yes")){
            System.exit(0);
        }
        else {
            System.out.println("Didn't say yes...Still caged...");
        }
    }

}
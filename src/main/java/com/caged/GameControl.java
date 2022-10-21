package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameControl<K, V> {

    private boolean userInput = false;
    private final Scanner scanner = new Scanner(System.in);
    private final boolean playGame = true;
    Scanner in = new Scanner(System.in);
    TextParser textParser = new TextParser();
    YAMLReader yamlReader = new YAMLReader();
    SplashScreen splashScreen = new SplashScreen();
    MainMenu mainMenu = new MainMenu();
    Console console = new Console();
    YAMLMapper mapper = new YAMLMapper();

    public void runGame() {
        console.clear();
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
        playGame(yamlReader.playerLoader(), yamlReader.locationLoader());
    }

    private void playGame(Player player, LocationGetter location) {
        while (playGame) {
            JsonNode node = mapper.valueToTree(location);
            String playerLocation = player.getCurrentLocation();
            console.clear();
            System.out.println("\nYou are in " + playerLocation);
            System.out.println("\nItems seen in room: ");
            System.out.println(node.get("room").get(playerLocation).get("Inventory").toString());
            System.out.println("\nRooms you can move to: ");
            System.out.println(node.get("room").get(playerLocation).get("Moves").toString());
            System.out.print("\n>>>>");
            String userChoice = in.nextLine();
            String lowUser = userChoice.toLowerCase();
            if (lowUser.equals("quit")) {
                quitConfirm();
            } else {
                String[] action = textParser.textParser(lowUser);
                player.playerActions(action[0], action[1], location);
            }
        }
    }

    public void mainMenuOptions() {
        while (!userInput) {
            System.out.print("\n>>>> ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("new game")) {
                yamlReader.introLoader();
                System.out.println("Hit enter to start");
                String enter = scanner.nextLine().toLowerCase();
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
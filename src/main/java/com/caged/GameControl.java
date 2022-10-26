package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.lang.reflect.Array;
import java.util.Iterator;
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
        HitEnter.enter();
        console.clear();
        mainMenu.mainMenu();
        mainMenuOptions();
        console.clear();
        yamlReader.objective();
        HitEnter.enter();
        playGame(yamlReader.playerLoader(), yamlReader.locationLoader());
    }

    private void playGame(Player player, LocationGetter location) {
        while (playGame) {
            JsonNode node = mapper.valueToTree(location);
            String playerLocation = player.getCurrentLocation();
            PlayerStatus.currentStatus(player);
            System.out.println("\nThings seen in room: ");
            KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory"));
            KeyValueParser.key(node.get("room").get(playerLocation).get("NPCs"));
            System.out.println("\nDirections you can move: ");
            KeyValueParser.keyValue(node.get("room").get(playerLocation).get("Moves"));
            //System.out.println(node.get("room").get(playerLocation).get("Moves").toString());
            System.out.print("\n>>>>");
            String userChoice = in.nextLine();
            String lowUser = userChoice.toLowerCase();
            String[] action = textParser.textParser(lowUser);
            player.playerActions(action[0], action[1], action[2], location);
        }
    }

    public void mainMenuOptions() {
        while (!userInput) {
            System.out.print("\n>>>> ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("new game")) {
                yamlReader.introLoader();
                System.out.println("\n\u001b[36mHit enter to start....\u001b[0m");
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
        System.out.println("\u001b[30m\u001b[41mDo you really want to quit?\u001b[0m");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("yes")){
            System.exit(0);
        }
        else {
            System.out.println("Didn't say yes...Still caged...");
        }
    }
}
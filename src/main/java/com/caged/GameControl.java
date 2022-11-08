package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import javax.sound.sampled.LineUnavailableException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameControl<K, V> {

    private boolean userInput = false;
    private final Scanner scanner = new Scanner(System.in);
    private boolean playGame = true;
    Scanner in = new Scanner(System.in);
    TextParser textParser = new TextParser(); // unnecessary?
    YAMLReader yamlReader = new YAMLReader(); //migrated
    SplashScreen splashScreen = new SplashScreen();
    MainMenu mainMenu = new MainMenu();
    Console console = new Console();
    YAMLMapper mapper = new YAMLMapper();
    GameMap playerMap1 = new GameMap();
    GameMap playerMap2 = new GameMap();
    MusicPlayer music = new MusicPlayer();
    String lastAction = "";

    // Primary game run function - fires from Main
    public void runGame() throws LineUnavailableException {
        // Title screen
        console.clear();
        splashScreen.splash();
        HitEnter.enter();
        //**************************/

        // Main menu screen - Play or Quit
        console.clear();
        mainMenu.mainMenu();
        mainMenuOptions();
        //**************************/

        // Initializes game 'framework' - only runs if quitConfirm is not true from mainMenuOptions
        console.clear();
        yamlReader.objective();
        HitEnter.enter();
        playerMap1.build();
        playerMap2.build();
        music.setFile("bgmusic.wav");
        music.play();
        //**************************/


        // Pass framework variables to the playGame method for gameplay
        playGame(yamlReader.playerLoader(), yamlReader.locationLoader(), yamlReader.doorLoader());
    }

    // Driving method for 'turns'
    private void playGame(Player player, LocationGetter location, List<Doors> doors) throws LineUnavailableException {
        // Game Loop
        while (player.isPlayGame()) {
            console.clear();

            // This is the banner display at the top of the terminal screen
            PlayerStatus.currentStatus(player);

            // Create JSON nodes, create player location and check for 'phase' (i.e. floor)
            // then update the map1/map2 appropriately
            JsonNode node = mapper.valueToTree(location);
            String playerLocation = player.getCurrentLocation();
            if (node.get("room").get(playerLocation).get("Phase").intValue()==1){
                playerMap1.positionUpdate(player, location);
            }
            else {
                playerMap2.positionUpdate(player, location);
            }

            // This will become a JPanels w/ text field output (remove println portions)
            System.out.println("\nThings seen in room: ");
            KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory"));
            System.out.println("\nPeople seen in room: ");
            KeyValueParser.key(node.get("room").get(playerLocation).get("NPCs"));
            System.out.println("\nDirections you can move: ");
            // TODO: provides movable directions to screen:
            KeyValueParser.locationKeyValue(node.get("room").get(playerLocation).get("Moves"), player, doors);
            System.out.println("\nLast action taken: "+player.getLastAction().get(player.getLastAction().size()-1));
            System.out.print(">>>>");
            String userChoice = in.nextLine(); // obsolete - becomes event handling
            String lowUser = userChoice.toLowerCase(); // obsolete - restrict input to event handling

            // Input action
            String[] action = textParser.textParser(lowUser);

            // Update function
            player.playerActions(action[0], action[1], action[2], location, doors, playerMap1, playerMap2, music);
        }
        System.out.println("Congrats you made it to the " + player.getCurrentLocation() + "!");
        System.out.println("");
        quitConfirm();
        HitEnter.enter();
        runGame();
    }

    public void mainMenuOptions() {
        while (!userInput) {
            System.out.print("\n>>>> ");
            String input = scanner.nextLine().toLowerCase();
            // Runs the introduction and pauses (goes back to runGame line
            if (input.equals("new game")) {
                yamlReader.introLoader();
                System.out.println("\n\u001b[36mHit enter to start....\u001b[0m");
                String enter = scanner.nextLine().toLowerCase();
                userInput = true;
            }
            //**************************/
            else if (input.equals("quit")) {
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
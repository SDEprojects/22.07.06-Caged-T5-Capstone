package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.*;

class Player {

    private String name;
    private String currentLocation;
    private int HitPoints;
    private List<Item> Inventory = new ArrayList<>();
    private List<Item> foundItems = new ArrayList<>();
    YAMLMapper mapper = new YAMLMapper();
    private final Scanner scanner = new Scanner(System.in);

    public Player(String name, String currentLocation, int HitPoints) {
        setHitPoints(HitPoints);
        setCurrentLocation(currentLocation);
        setName(name);
    }

    public Player(){

    }

    //functions
    public void playerActions(String verb, String noun, String nounPrefix, LocationGetter location, List<Doors> doors){
        switch (verb) {
            case "move":
                move(noun, location, doors);
                break;
            case "take":
                take(noun, nounPrefix, location);
                break;
            case "look":
                look(noun, location);
                break;
            case "use":
                use(noun, nounPrefix, location);
                break;
            case "help":
                helpCommand();
                break;
            case "quit":
                quitConfirm();
                break;
            case "talk":
                talk(noun, nounPrefix, location);
                break;
            case "inventory":
                checkInventory();
                break;
            case "drop":
                drop(noun, nounPrefix, location);
                break;
            case "attack":
                attack(noun, nounPrefix, location);
                break;
            case "open":
                open(noun, nounPrefix, location, doors);
                break;
            default:
        }
    }

    private void attack(String firstName, String lastName, LocationGetter location) {
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        CharacterPlayer player = new CharacterPlayer(getHitPoints());
        CharacterEnemy enemy = new CharacterEnemy(20);
        Console console = new Console();
        double flee = 0.2;

        try {
            if(node.get("room").get(playerLocation).get("NPCs").get(lastName + " " + firstName).has("enemy")){
                System.out.println("You attacked " + lastName + " " + firstName + "\u001B[31m\u001B[1m \nPrepare for battle!!\u001b[0m");
                System.out.println("Type fight to battle\nType run to run away");
                int playerHp = (player.getHp() + player.defence() * 5);
                int npcHp = (enemy.getHp() + enemy.defence() * 5);
                while (true){
                    String userInput = Console.readInput(">>>>");
                    if (Objects.equals(userInput, "fight")){
                        System.out.println("Players HP: " + playerHp);
                        System.out.println("NPC HP: " + npcHp);
                        console.clear();
                        playerHp = playerHp - enemy.attack();
                        npcHp = npcHp - enemy.attack();
                        if (npcHp < 0){
                            System.out.println("\u001b[36mYou won the battle!");
                            break;
                        }if (playerHp < 0){
                            System.out.println("\u001B[31m\u001B[1mYou LOSE!\u001b[0m");
                            break;
                        }
                    }
                    if (Objects.equals(userInput, "run")){
                        if(flee > Math.random()) {
                            System.out.println("You have successfully ran away!");
                            break;
                        }else{
                            System.out.println("You failed to escape the fight. Good luck on your battle!");
                        }
                    }
                }
            }else{
                System.out.println("You can't attack that!");
            }
        } catch (Exception e) {
            System.out.println("Nothing happen...");;
        }
    }

    private void talk(String lastName, String firstName, LocationGetter location) {
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        YAMLReader yamlReader = new YAMLReader();
        List<String> chatList = new ArrayList<>(yamlReader.randChat());
        String [] rand;

        try {
            if (node.get("room").get(playerLocation).get("NPCs").get(firstName + " " + lastName).has("chat")){
                List<String> newChatList = chatList.subList(3,4);
                rand = newChatList.get(0).split(",");
                List<String> randText = new ArrayList<>(List.of(rand));
                Collections.shuffle(randText);
                System.out.println(randText.get(0).replaceAll("\\[", "").replaceAll("\\]", ""));



                //System.out.println(node.get("room").get(playerLocation).get("NPCs").get(firstName + " " + lastName).get("chat"));
            }
        } catch (Exception e) {
            System.out.println(firstName + " " + lastName + " no response to the that name");
        }
    }


    private void move(String direction, LocationGetter location, List<Doors> doors){
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        try {
            JsonNode doorNode =node.get("room").get(playerLocation).get("Moves").get(direction).get("door");
            if (!doorNode.isNull()) {
                Doors door = doors.stream().filter(doorSeek -> doorSeek.getDoorName().equals(doorNode.textValue())).findFirst().orElse(null);
                if (door.isLocked()){
                    System.out.println("door is locked!");
                }
                else {
                    setCurrentLocation(node.get("room").get(playerLocation).get("Moves").get(direction).get("location").textValue());
                    System.out.println("Player moves " + direction);
                }
            }
            else {
                setCurrentLocation(node.get("room").get(playerLocation).get("Moves").get(direction).get("location").textValue());
                System.out.println("Player moves " + direction);
            }
        } catch (Exception e) {
            System.out.println("Direction not available...");
            HitEnter.enter();
        }
    }

    public void open(String target, String direction, LocationGetter location,  List<Doors> doors) {
        try {
            String playerLocation = getCurrentLocation();
            JsonNode node = mapper.valueToTree(location);
            JsonNode doorNode = null;
            try {
                doorNode = node.get("room").get(playerLocation).get("Moves").get(direction).get("door");
            } catch (Exception e) {
                System.out.println("There is no locked door to open there... \nTo open a door type 'open [direction] door' against valid locked door direction!");
                return;
            }
            JsonNode finalDoorNode = doorNode;
            Doors door = doors.stream().filter(doorSeek -> doorSeek.getDoorName().equals(finalDoorNode.textValue())).findFirst().orElse(null);
            assert door != null;
            List<String> keys = door.getKeys();
            for (String key : keys
            ) {
                try {
                    Item item = getInventory().stream().filter(i -> i.getName().equals(key)).findFirst().orElse(null);
                    assert item != null;
                    if (item.name.equals(key)) {
                        door.setLocked(false);
                        System.out.println("Door unlocked using the " + key + "!");
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
            if (door.isLocked()) {
                System.out.println("Unable to open... \nMust be missing something to unlock the door with!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't open door, maybe wrong command!");
        }
    }


    private void take(String item, String itemPrefix, LocationGetter location) { //private
        String playerLocation = getCurrentLocation();
        try {
            for (Item i :
                    foundItems) {
                if (i.name.equals(item) && !i.isTaken && i.currentLocation.equals(playerLocation)) {
                    Inventory.add(i);
                    i.setTaken(true);
                    i.setCurrentLocation("Inventory");
                    System.out.println(item + " has been taken!");
                } else if (i.name.equals(itemPrefix + " " + item) && !i.isTaken && i.currentLocation.equals(playerLocation)) {
                    Inventory.add(i);
                    i.setTaken(true);
                    i.setCurrentLocation("Inventory");
                    System.out.println(itemPrefix + " " + item + " has been taken!");
                }
            }
        } catch (Exception e) {
            System.out.println("You did not see the item");
        }
    }

    private void drop(String item, String itemPrefix, LocationGetter location){
        String playerLocation = getCurrentLocation();
        try {
            for (Item i :
                    foundItems) {
                if (i.name.equals(item) && i.isTaken && i.currentLocation.equals("Inventory")) {
                    Inventory.remove(i);
                    i.setTaken(false);
                    i.setCurrentLocation(playerLocation);
                    System.out.println(item + " has been dropped, in "+ playerLocation+"!");
                }
                else if (i.name.equals(itemPrefix + " " + item) && i.isTaken && i.currentLocation.equals("Inventory")) {
                        Inventory.remove(i);
                        i.setTaken(false);
                        i.setCurrentLocation(playerLocation);
                        System.out.println(itemPrefix+" " + item + " has been dropped, in "+ playerLocation+"!");
                }
            }
        } catch (Exception e) {
            System.out.println("You do not have that item!");
        }
    }


    private void use(String subThing, String parentThing, LocationGetter location){
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        try {
            System.out.println(node.get("room").get(playerLocation).get("Inventory").get(parentThing).get(subThing).textValue());
            JsonNode nodeItem = node.get("room").get(playerLocation).get("Inventory").get(parentThing).get("items").get(subThing);
            int itemFound = 0;
            if (node.get("room").get(playerLocation).get("Inventory").get(parentThing).has("items")) {
                for (Item i :
                        foundItems) {
                    if (i.name.equals(nodeItem.get("name").textValue())) {
                        itemFound = 1;
                    }
                }
                if (itemFound == 0) {
                    Item foundItem = new Item(nodeItem.get("name").textValue(), nodeItem.get("description").textValue(), nodeItem.get("strength").intValue(), nodeItem.get("opens").textValue(), playerLocation, false, playerLocation);
                    foundItems.add(foundItem);
                    System.out.println("You found " + foundItem.name + "!");
                }
            }
        } catch (Exception e) {
            System.out.println("Tried to use " + parentThing + " " + subThing);
            System.out.println("nothing new found");
        }
    }

    private void look(String thing, LocationGetter location){ //private
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        if (node.get("room").get(playerLocation).get("Inventory").has(thing)){
            System.out.println(node.get("room").get(playerLocation).get("Inventory").get(thing).get("description").textValue());
        }
        else {
            System.out.println("Thing not found...");
        }
    }

    private void helpCommand() { //private

        YAMLReader yamlReader = new YAMLReader();
        List<String> action = yamlReader.help();
        List<String> help = new ArrayList<>(action);
        //String values = String.join(", ", help);
        System.out.println("The available commands are: ");
        for (String h : help) {
            System.out.println(h);

        }
        Scanner scanner = new Scanner(System.in);
        HitEnter.enter();
    }


    private void quitConfirm(){ //private
        System.out.println("\u001b[30m\u001b[41mDo you really want to quit?\u001b[0m");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("yes")){
            System.exit(0);
        }
        else {
            System.out.println("Didn't say yes...Still caged...");
        }
    }

    private void checkInventory(){
        System.out.println("\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m");
        System.out.println("\u001b[36m                           WHAT YOU HAVE IN YOUR STASH\u001B[0m");
        System.out.println("\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m");

        for (Item stash : Inventory) {
            System.out.println("Item: " + stash.getName() + " | Description: " + stash.getDescription() + " | Strength: " + stash.getStrength());
        }
    }




    //getter & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getHitPoints() {
        return HitPoints;
    }

    public void setHitPoints(int hitPoints) {
        HitPoints = hitPoints;
    }

    public List<Item> getInventory() {
        return Inventory;
    }

    public void setInventory(List<Item> inventory) {
        Inventory = inventory;
    }

    public List<Item> getFoundItems() {
        return foundItems;
    }

    public void setFoundItems(List<Item> foundItems) {
        this.foundItems = foundItems;
    }
}
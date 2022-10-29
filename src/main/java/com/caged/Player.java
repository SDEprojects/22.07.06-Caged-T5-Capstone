package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.*;

class Player {

    private String name;
    private String currentLocation;
    private String equipment;
    private String weapon;
    private int HitPoints;
    private List<Item> Inventory = new ArrayList<>();
    private List<Item> foundItems = new ArrayList<>();
    YAMLMapper mapper = new YAMLMapper();
    private final Scanner scanner = new Scanner(System.in);
    private List<String> lastAction = new ArrayList<>();

    public Player(String name, String currentLocation, int HitPoints, String equipment,String weapon, List<String> lastAction) {
        setHitPoints(HitPoints);
        setCurrentLocation(currentLocation);
        setName(name);
        setEquipment(equipment);
        setWeapon(weapon);
        setLastAction(lastAction);
    }

    public Player() {

    }

    //functions
    public void playerActions(String verb, String noun, String nounPrefix, LocationGetter location, List<Doors> doors, GameMap playerMap, MusicPlayer music) {
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
            case "map":
                mapCommand(playerMap);
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
            case "equip":
                equip();
                break;
            case "play":
                playMusic(music);
                break;
            case "stop":
                stopMusic(music);
                break;
            case "log":
                log();
                break;
            default:
        }
    }

    private void equip() {
        for (Item item : Inventory) {
            if (item.getName().contains("guard uniform")) {
                setEquipment(item.getName());
                lastAction.add("Equipped the guard uniform!");
            }
            if (item.getName().contains("brick")) {
                setWeapon(item.getName());
                lastAction.add("Equipped the brick!");
            }

        }
    }

    private void playMusic(MusicPlayer music) {
        music.play();
        System.out.println("Play music");
        lastAction.add("Playing music");
    }

    private void stopMusic(MusicPlayer music) {
        music.turnOff();
        System.out.println("quit music");
        lastAction.add("Turned off music");
    }

    private void attack(String firstName, String lastName, LocationGetter location) {
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        CharacterPlayer player = new CharacterPlayer(getHitPoints());
        int hp = node.get("room").get(playerLocation).get("NPCs").get(lastName + " " + firstName).get("hp").intValue();
        Item item = Inventory.stream()
                .filter(i -> i.getName().equals("brick"))
                .findFirst()
                .orElse(null);
        int multiplier = 1;
        if (weapon.equals("brick")) {
            multiplier = item.strength;
        }

        CharacterEnemy enemy = new CharacterEnemy(hp);
        double flee = 0.6;

        try {
            if (node.get("room").get(playerLocation).get("NPCs").get(lastName + " " + firstName).has("enemy")) {
                System.out.println("You attacked " + lastName + " " + firstName + "\u001B[31m\u001B[1m \nPrepare for battle!!\u001b[0m");
                System.out.println("Type fight to battle\nType run to run away");
                int playerHp = player.getHp();
                int npcHp = enemy.getHp();
                while (true) {
                    String userInput = Console.readInput(">>>>");
                    if (Objects.equals(userInput, "fight")) {
                        System.out.println("Players HP: " + playerHp);
                        System.out.println("NPC HP: " + npcHp);
                        playerHp = playerHp - enemy.attack(1);
                        npcHp = npcHp - player.attack(multiplier);
                        if (npcHp < 0) {
                            System.out.println("\u001b[36mYou won the battle!");
                            lastAction.add("Tried to fight...You won the battle!...");
                            try {
                                JsonNode nodeItem = node.get("room").get(playerLocation).get("NPCs").get(lastName + " " + firstName).get("NPC Inventory");
                                Item foundItem = new Item(nodeItem.get("name").textValue(), nodeItem.get("description").textValue(), nodeItem.get("strength").intValue(), nodeItem.get("opens").textValue(), playerLocation, false, playerLocation);
                                foundItems.add(foundItem);
                                System.out.println(lastName + " " + firstName + " dropped " + foundItem.name + "!");
                                lastAction.add("You won the battle!... " + lastName + " " + firstName + " was knocked out and dropped " + foundItem.name + "!");
                            } catch (Exception ignored) {

                            }
                            break;
                        }
                        if (playerHp < 0) {
                            System.out.println("\u001B[31m\u001B[1mYou LOSE!\u001b[0m");
                            setCurrentLocation("Cage 1");
                            setHitPoints(1);
                            lastAction.add("Tried to fight...you lost and were thrown back into the cage!...back to crying in cage...");
                            break;
                        }
                    }
                    if (Objects.equals(userInput, "run")) {
                        if (flee > Math.random()) {
                            System.out.println("You have successfully ran away!");
                            lastAction.add("Tried to fight...\"You have successfully ran away!\"...");
                            break;
                        } else {
                            System.out.println("You failed to escape the fight. Good luck on your battle!");
                        }
                    }
                    setHitPoints(playerHp);
                }
            } else {
                System.out.println("You can't attack that!");
                lastAction.add("Tried to fight...\"You can't attack that!\"...");
            }
        } catch (Exception e) {
            System.out.println("Nothing happened...");
            lastAction.add("Tried to fight...Nothing happened...");
        }
    }

    private void talk(String lastName, String firstName, LocationGetter location) {
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        YAMLReader yamlReader = new YAMLReader();
        List<String> chatList = new ArrayList<>(yamlReader.randChat());
        String[] rand;

        try {
            if (node.get("room").get(playerLocation).get("NPCs").get(firstName + " " + lastName).has("chat")) {
                List<String> newChatList = chatList.subList(3, 4);
                rand = newChatList.get(0).split(",");
                List<String> randText = new ArrayList<>(List.of(rand));
                Collections.shuffle(randText);
                String chat = randText.get(0).replaceAll("\\[", "").replaceAll("\\]", "");
                System.out.println(chat);
                lastAction.add("Chatted with " + node.get("room").get(playerLocation).get("NPCs").get(firstName + " " + lastName).textValue() + ", they responded: " + chat);
            }
        } catch (Exception e) {
            System.out.println(firstName + " " + lastName + " no response to the that name");
            lastAction.add(firstName + " " + lastName + " no response to the that name");
        }
    }

    private void move(String direction, LocationGetter location, List<Doors> doors) {
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        try {
            JsonNode doorNode = node.get("room").get(playerLocation).get("Moves").get(direction).get("door");
            if (!doorNode.isNull()) {
                Doors door = doors.stream().filter(doorSeek -> doorSeek.getDoorName().equals(doorNode.textValue())).findFirst().orElse(null);
                if (door.isLocked()) {
                    System.out.println("door is locked... try 'open [direction] door' to find out more...");
                    lastAction.add("door is locked... try 'open [direction] door' to find out more...");
                } else {
                    setCurrentLocation(node.get("room").get(playerLocation).get("Moves").get(direction).get("location").textValue());
                    System.out.println("Player moves " + direction);
                    lastAction.add("Player moves " + direction);
                }
            } else {
                setCurrentLocation(node.get("room").get(playerLocation).get("Moves").get(direction).get("location").textValue());
                System.out.println("Player moves " + direction);
                lastAction.add("Player moves " + direction);
            }
        } catch (Exception e) {
            System.out.println("Direction not available...");
            lastAction.add("Direction not available...");
            HitEnter.enter();
        }
    }

    public void open(String target, String direction, LocationGetter location, List<Doors> doors) {
        try {
            String playerLocation = getCurrentLocation();
            JsonNode node = mapper.valueToTree(location);
            JsonNode doorNode = null;
            try {
                doorNode = node.get("room").get(playerLocation).get("Moves").get(direction).get("door");
            } catch (Exception e) {
                System.out.println("There is no locked door to open there... \nTo open a door type 'open [direction] door' against valid locked door direction!");
                lastAction.add("There is no locked door to open there... \nTo open a door type 'open [direction] door' against valid locked door direction!");
                return;
            }
            JsonNode finalDoorNode = doorNode;
            Doors door = doors.stream().filter(doorSeek -> doorSeek.getDoorName().equals(finalDoorNode.textValue())).findFirst().orElse(null);
            assert door != null;
            List<String> keys = door.getKeys();
            String doorDescription = door.getDescription();
            for (String key : keys
            ) {
                try {
                    Item item = getInventory().stream().filter(i -> i.getName().equals(key)).findFirst().orElse(null);
                    assert item != null;
                    if (item.name.equals(key)) {
                        door.setLocked(false);
                        System.out.println("Door unlocked using the " + key + "!");
                        lastAction.add("Door unlocked using the " + key + "!");
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
            if (door.isLocked()) {
                System.out.println("Unable to open... "+ doorDescription);
                lastAction.add("Unable to open... "+ doorDescription);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't open door, maybe wrong command!");
            lastAction.add("Couldn't open door, maybe wrong command!");
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
                    lastAction.add(item + " has been taken!");
                } else if (i.name.equals(itemPrefix + " " + item) && !i.isTaken && i.currentLocation.equals(playerLocation)) {
                    Inventory.add(i);
                    i.setTaken(true);
                    i.setCurrentLocation("Inventory");
                    System.out.println(itemPrefix + " " + item + " has been taken!");
                    lastAction.add(itemPrefix + " " + item + " has been taken!");
                }
            }
        } catch (Exception e) {
            System.out.println("You did not see the item");
            lastAction.add("You did not see the item");
        }
    }

    private void drop(String item, String itemPrefix, LocationGetter location) {
        String playerLocation = getCurrentLocation();
        try {
            for (Item i :
                    foundItems) {
                if (i.name.equals(item) && i.isTaken && i.currentLocation.equals("Inventory")) {
                    Inventory.remove(i);
                    i.setTaken(false);
                    i.setCurrentLocation(playerLocation);
                    System.out.println(item + " has been dropped, in " + playerLocation + "!");
                    lastAction.add(item + " has been dropped, in " + playerLocation + "!");
                } else if (i.name.equals(itemPrefix + " " + item) && i.isTaken && i.currentLocation.equals("Inventory")) {
                    Inventory.remove(i);
                    i.setTaken(false);
                    i.setCurrentLocation(playerLocation);
                    System.out.println(itemPrefix + " " + item + " has been dropped, in " + playerLocation + "!");
                    lastAction.add(itemPrefix + " " + item + " has been dropped, in " + playerLocation + "!");
                }
            }
        } catch (Exception e) {
            System.out.println("You do not have that item!");
            lastAction.add("You do not have that item!");
        }
    }


    private void use(String subThing, String parentThing, LocationGetter location) {
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
                    lastAction.add(node.get("room").get(playerLocation).get("Inventory").get(parentThing).get(subThing).textValue()+ "...You found " + foundItem.name + "!");
                }
            }
        } catch (Exception e) {
            System.out.println("Tried to use " + parentThing + " " + subThing);
            System.out.println("nothing new found");
            lastAction.add(node.get("room").get(playerLocation).get("Inventory").get(parentThing).get(subThing).textValue() + "...nothing new found");
        }
    }

    private void look(String thing, LocationGetter location) { //private
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        if (node.get("room").get(playerLocation).get("Inventory").has(thing)) {
            System.out.println(node.get("room").get(playerLocation).get("Inventory").get(thing).get("description").textValue());
            lastAction.add(node.get("room").get(playerLocation).get("Inventory").get(thing).get("description").textValue());
        } else {
            System.out.println("Thing not found...");
            lastAction.add("Thing not found...");
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

    private void log() {
        for (String log:getLastAction()
             ) {
            System.out.println(log);
        }
        HitEnter.enter();
    }

    private void mapCommand(GameMap playerMap) {
        System.out.println("\n\u001b[47m\u001b[30m- - - - - - - - - - - Current Position(MAP) - - - - - - - - - - -\u001b[0m\n");
        System.out.println("Player is currently in " + getCurrentLocation());
        System.out.println();
        playerMap.show();
        System.out.println("\nMap Key:");
        System.out.println("Places been: \u001b[36m\u001b[46m[O]\u001b[0m Current Location:\u001b[32m\u001b[42m[X]\u001b[0m");
        System.out.println("\n\u001b[47m\u001b[30m- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\u001b[0m");
        HitEnter.enter();
    }


    private void quitConfirm() { //private
        System.out.println("\u001b[30m\u001b[41mDo you really want to quit?\u001b[0m");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("yes")) {
            System.exit(0);
        } else {
            System.out.println("Didn't say yes...Still caged...");
        }
    }

    private void checkInventory() {
        System.out.println("\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m");
        System.out.println("\u001b[36m                           WHAT YOU HAVE IN YOUR STASH\u001B[0m");
        System.out.println("\u001b[32m* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\u001B[0m");

        for (Item stash : Inventory) {
            System.out.println("Item: " + stash.getName() + " | Description: " + stash.getDescription() + " | Strength: " + stash.getStrength());
        }
        HitEnter.enter();
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
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

    public List<String> getLastAction() {
        return lastAction;
    }

    public void setLastAction(List<String> lastAction) {
        this.lastAction = lastAction;
    }
}
package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

    private String name;
    private String currentLocation;
    private int HitPoints;
    private List<String> Inventory;
    private List<Item> foundItems;
    YAMLMapper mapper = new YAMLMapper();
    private final Scanner scanner = new Scanner(System.in);



    public Player(String name,String currentLocation, int HitPoints, List<String> Inventory, List<Item> foundItems){
        setInventory(Inventory);
        setHitPoints(HitPoints);
        setCurrentLocation(currentLocation);
        setName(name);
        setFoundItems(foundItems);
    }

    public Player(){

    }

    //functions
    public void playerActions(String verb, String noun, String nounPrefix, LocationGetter location){ //add
        switch (verb) {
            case "move":
                move(noun, location);
                break;
            case "take":
                take(noun, nounPrefix, location);
                System.out.println("Taking "+ noun +"!");
                break;
            case "look":
                look(noun, location);
                break;
            case "use": //new
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
            default:
        }
    }

    public void talk(String lastName, String firstName, LocationGetter location) {
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        try {
            if (node.get("room").get(playerLocation).get("NPCs").get(firstName + " " + lastName).has("chat")){
                System.out.println(node.get("room").get(playerLocation).get("NPCs").get(firstName + " " + lastName).get("chat"));
            }
        } catch (Exception e) {
            System.out.println(firstName + " " + lastName + " no response to the that name");
        }
    }


    private void move(String direction, LocationGetter location){ //private
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        if (node.get("room").get(playerLocation).get("Moves").has(direction)){
            System.out.println("Player moves " + direction);
            setCurrentLocation(node.get("room").get(playerLocation).get("Moves").get(direction).textValue());
        }
        else {
            System.out.println("Direction not available...");
            HitEnter.enter();
        }
    }

    private void take(String item, String itemPrefix, LocationGetter location){ //private
//        List<String> inventory = getInventory();
//        String playerLocation = getCurrentLocation();
//        String compoundItem = itemPrefix+" "+item;
//        JsonNode node = mapper.valueToTree(location);
//        if (node.get("items").has(item)) {
//            inventory.add(node.get("items").get(item).get("name").textValue());
//            System.out.println("Player takes " + item);
//        }
//        else if (node.get("items").has(compoundItem)){
//            inventory.add(node.get("items").get(compoundItem).get("name").textValue());
//            System.out.println("Player takes " + compoundItem);
//        }
//        else {
//            System.out.println("Item not found");
//        }
//        System.out.println("Players items: " + getInventory());
        System.out.println("Taking item");
    }

    private void use(String subThing, String parentThing, LocationGetter location){
        String playerLocation = getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        if (node.get("room").get(playerLocation).get("Inventory").has(parentThing)){
            if (node.get("room").get(playerLocation).get("Inventory").get(parentThing).has(subThing)){
                System.out.println(node.get("room").get(playerLocation).get("Inventory").get(parentThing).get(subThing).textValue());
                if (node.get("room").get(playerLocation).get("Inventory").get(parentThing).has("items")){
                    if (foundItems.contains(node.get("room").get(playerLocation).get("Inventory").get(parentThing).has("items"))){
                        System.out.println("nothing happens!");
                    }
                    else {
                        System.out.println("Found something!");
                        KeyValueParser.key(node.get("room").get(playerLocation).get("Inventory").get(parentThing).get("items"));
                        Item foundItem = new Item("name", "description", 0, "null", "locationFound", false);
                        foundItems.add(foundItem);
                        System.out.println(foundItems);
                    }

                }
            }
            else {
                System.out.println(node.get("room").get(playerLocation).get("Inventory").get(parentThing).get(subThing).textValue()+ " not found!");
            }
        }
        else {
            System.out.println("Tried to use "+ parentThing +" "+ subThing);
            System.out.println("Thing not found");
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
        HitEnter.enter();
    }

    private void helpCommand(){ //private

        Intro command = new Intro();
        List<String> action = command.help();
        List<String> help = new ArrayList<>(action);
        String values = String.join(", ", help);
        System.out.println("The available commands are: " + values + ".");
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

    public List<String> getInventory() {
        return Inventory;
    }

    public void setInventory(List<String> inventory) {
        Inventory = inventory;
    }

    public List<Item> getFoundItems() {
        return foundItems;
    }

    public void setFoundItems(List<Item> foundItems) {
        this.foundItems = foundItems;
    }
}
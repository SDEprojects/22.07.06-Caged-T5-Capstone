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
    YAMLMapper mapper = new YAMLMapper();
    private final Scanner scanner = new Scanner(System.in);



    public Player(String name,String currentLocation, int HitPoints, List<String> Inventory){
        setInventory(Inventory);
        setHitPoints(HitPoints);
        setCurrentLocation(currentLocation);
        setName(name);
    }

    public Player(){

    }

    //functions
    public void playerActions(String verb, String noun, LocationGetter location){
        switch (verb) {
            case "move":
                move(noun, location);
                break;
            case "take":
                take(noun);
                System.out.println("Taking "+ noun +"!");
                break;
            case "look":
                look(noun, location);
                break;
            case "help":
                helpCommand();
                break;
            case "quit":
                quitConfirm();
                break;
            case "talk":
                talk(noun);
            default:
        }
    }

    private void talk(String talk) {
        System.out.println("talking");
    }

    public void move(String direction, LocationGetter location){
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

    public void take(String item){
        System.out.println("Player takes " + item);
    }

    public void look(String thing, LocationGetter location){
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

    public void helpCommand(){

        Intro command = new Intro();
        List<String> action = command.help();
        List<String> help = new ArrayList<>(action);
        String values = String.join(", ", help);
        System.out.println("The available commands are: " + values + ".");
        Scanner scanner = new Scanner(System.in);
        HitEnter.enter();
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
}
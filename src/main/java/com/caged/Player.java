package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.List;

class Player {

    private String name;
    private String currentLocation;
    private int HitPoints;
    private List<String> Inventory;
    YAMLMapper mapper = new YAMLMapper();

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
            default:
        }
    }

    public void move(String direction, LocationGetter location){
        JsonNode node = mapper.valueToTree(location);
        String playerLocation = getCurrentLocation();
        if (node.get("room").get(playerLocation).get("Moves").has(direction)){
            System.out.println("Player moves " + direction);
            setCurrentLocation(node.get("room").get(playerLocation).get("Moves").get(direction).textValue());
        }
        else {
            System.out.println("Direction not available...");
        }
    }

    public void take(String item){
        System.out.println("Player takes " + item);
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
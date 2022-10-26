package com.caged;

class Item {

    String name;
    String description;
    int strength;
    String opens;
    String locationFound;
    boolean isTaken;
    String currentLocation;

    public Item(String name, String description, int strength, String opens, String locationFound, boolean isTaken, String currentLocation) {
        setName(name);
        setDescription(description);
        setStrength(strength);
        setOpens(opens);
        setLocationFound(locationFound);
        setTaken(isTaken);
        setCurrentLocation(currentLocation);
    }

    public String getLocationFound() {
        return locationFound;
    }

    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
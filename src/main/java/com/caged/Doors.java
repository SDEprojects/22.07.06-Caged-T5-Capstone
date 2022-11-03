package com.caged;

import java.security.PrivateKey;
import java.util.List;

public class Doors {

    private String doorName;
    private boolean isLocked;
    private List<String> keys;
    private String description;

    public Doors(String doorName, boolean isLocked, List<String> keys, String description) {
        setDoorName(doorName);
        setLocked(isLocked);
        setKeys(keys);
        setDescription(description);
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

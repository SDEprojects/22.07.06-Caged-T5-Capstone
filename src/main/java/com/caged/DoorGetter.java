package com.caged;

import java.util.Map;

class DoorGetter<K, V>  {
    private Map<K,V> doors;

    public DoorGetter(Map<K,V> room) {
        setDoors(room);
    }

    public DoorGetter() {

    }

    public Map<K, V> getDoors() {
        return doors;
    }

    public void setDoors(Map<K, V> doors) {
        this.doors = doors;
    }
}

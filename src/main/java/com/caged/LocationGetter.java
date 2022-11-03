package com.caged;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationGetter<K, V> {
    private Map<K,V> room;

    public LocationGetter(Map<K,V> room) {
        setRoom(room);
    }

    public LocationGetter() {

    }

    public Map<K, V> getRoom() {
        return room;
    }

    public void setRoom(Map<K, V> room) {
        this.room = room;
    }
}
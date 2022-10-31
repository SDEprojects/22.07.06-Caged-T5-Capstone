package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class KeyValueParser {

    public static void keyValue(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            System.out.println("\u001b[35m" + entry.getKey() + "\u001b[0m" + "  ---->  "+entry.getValue());
        }
    }

    public static void key(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            System.out.println("\u001b[35m" + entry.getKey()+ "\u001b[0m");
        }
    }

    public static void value(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            System.out.println(entry.getValue());
        }
    }

    public static void locationKeyValue(JsonNode node, Player player, List<Doors> doors) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        List<Item> items = player.getFoundItems();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            String location = node.get(entry.getKey()).get("location").textValue();
            Doors door = doors.stream().filter(doorSeek -> doorSeek.getDoorName().equals(node.get(entry.getKey()).get("door").textValue())).findFirst().orElse(null);
            if (!node.get(entry.getKey()).get("hidden").booleanValue()) {
                if (node.get(entry.getKey()).get("door").isNull()){
                    System.out.println("\u001b[35m" + entry.getKey() + "\u001b[0m" + "  ---->  " + location);
                }
                else {
                    //Doors door = doors.stream().filter(doorSeek -> doorSeek.getDoorName().equals(node.get(entry.getKey()).get("door").textValue())).findFirst().orElse(null);
                    if (door.isLocked()){
                        System.out.println("\u001b[35m" + entry.getKey() + "\u001b[0m" + "  ---->  " + location + "  ---->  ***DOOR LOCKED***");
                    }
                    else {
                        System.out.println("\u001b[35m" + entry.getKey() + "\u001b[0m" + "  ---->  " + location + "  ---->  DOOR UNLOCKED!");
                    }
                    //System.out.println("\u001b[35m" + entry.getKey() + "\u001b[0m" + "  ---->  " + reaction)
                }
            } else if (node.get(entry.getKey()).get("hidden").booleanValue()) {
                for (Item i :
                        items) {
                    if (i.name.equals(node.get(entry.getKey()).get("key").textValue())) {
                        door.setLocked(false);
                        String reaction = node.get(entry.getKey()).get("reaction").textValue();
                        System.out.println("\u001b[35m" + entry.getKey() + "\u001b[0m" + "  ---->  " + reaction);
                    }
                }

            }
        }
    }


}
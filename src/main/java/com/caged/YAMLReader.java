package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class YAMLReader {

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    FileGetter fileGetter = new FileGetter();


    public Player playerLoader(){
        try {
            Player player = mapper.readValue(fileGetter.yamlGetter("player.yml"), Player.class);
            return player;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DictionaryGetter dictionaryLoader(){
        try {
            DictionaryGetter dictionary =  mapper.readValue(fileGetter.yamlGetter("dictionary.yml"), DictionaryGetter.class);
            return dictionary;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CharacterNPCGetter characterNPCLoader(){
        try {
            CharacterNPCGetter CharacterNPC =  mapper.readValue(fileGetter.yamlGetter("CharacterNPC.yml"), CharacterNPCGetter.class);
            return CharacterNPC;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemGetter itemLoader(){
        try {
            ItemGetter item =  mapper.readValue(fileGetter.yamlGetter("Items.yml"), ItemGetter.class);
            return item;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocationGetter locationLoader(){
        try {
            LocationGetter location =  mapper.readValue(fileGetter.yamlGetter("location.yml"), LocationGetter.class);
            return location;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void introLoader() {
        try {
            Information info = mapper.readValue(fileGetter.yamlGetter("Game_info.yml"), Information.class);
            String result = info.getStory().get(0);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void objective() {
        try {
            Information obj = mapper.readValue(fileGetter.yamlGetter("Game_info.yml"), Information.class);
            String result = obj.getObjective().get(0);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> help() {
        List<String> result = new ArrayList<>();
        try {
            DictionaryGetter info = mapper.readValue(fileGetter.yamlGetter("dictionary.yml"), DictionaryGetter.class);
            JsonNode helpNode = mapper.valueToTree(info);
            for (JsonNode node : helpNode) {
                result.add(node.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }return  result;
    }

    public List<Doors> doorLoader() {
        List<Doors> result = new ArrayList<>();
        DoorGetter doors = null;
        try {
            doors = mapper.readValue(fileGetter.yamlGetter("doors.yml"), DoorGetter.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode node = mapper.valueToTree(doors);
        JsonNode doorNode = node.get("doors");
        for (JsonNode door : doorNode) {
            List<String> keys= new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> nodes = door.get("keys").fields();
            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
                keys.add(entry.getValue().textValue());
            }
            Doors newDoor = new Doors(door.get("name").textValue(), door.get("locked").booleanValue(), keys, door.get("description").textValue());
            result.add(newDoor);
        }
        return result;
    }

    public List<String> randChat () {
        List<String> result = new ArrayList<>();

        try {
            Information chat = mapper.readValue(fileGetter.yamlGetter("Game_info.yml"), Information.class);
            JsonNode chatNode = mapper.valueToTree(chat);
            for (JsonNode node : chatNode){
                result.add(node.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }return result;
    }

}
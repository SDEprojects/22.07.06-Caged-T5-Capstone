package com.caged;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;

class YAMLReader {

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public Player playerLoader(){
        try {
            Player player =  mapper.readValue(new File("./resources/player.yml"), Player.class);
            return player;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DictionaryGetter dictionaryLoader(){
        try {
            DictionaryGetter dictionary =  mapper.readValue(new File("./resources/dictionary.yml"), DictionaryGetter.class);
            return dictionary;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CharacterNPCGetter characterNPCLoader(){
        try {
            CharacterNPCGetter CharacterNPC =  mapper.readValue(new File("./resources/CharacterNPC.yml"), CharacterNPCGetter.class);
            return CharacterNPC;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemGetter itemLoader(){
        try {
            ItemGetter item =  mapper.readValue(new File("./resources/Items.yml"), ItemGetter.class);
            return item;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocationGetter locationLoader(){
        try {
            LocationGetter location =  mapper.readValue(new File("./resources/Location.yml"), LocationGetter.class);
            return location;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void introLoader() {
        try {
            Information info = mapper.readValue(new File("./resources/Game_info.yml"), Information.class);
            String result = info.getStory().get(0);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
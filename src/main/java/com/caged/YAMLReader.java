package com.caged;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

class YAMLReader {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public Player playerLoader(){
        try {
            Player player =  mapper.readValue(new File("./resources/player.yml"), Player.class);
            System.out.println("New player: " + player.getName());
            return player;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String introLoader() {
        String result = "";
        try {
            Information info = mapper.readValue(new File("./resources/Game_info.yml"), Information.class);
            result = info.getStory().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
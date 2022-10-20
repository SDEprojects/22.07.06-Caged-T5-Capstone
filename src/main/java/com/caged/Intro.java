package com.caged;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Intro {
    public String storyIntro(){
        String result = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Information info = objectMapper.readValue(new File("./resources/Game_info.yml"), Information.class);
            result = info.getStory().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static void main(String[] args) throws InterruptedException {
        Intro story = new Intro();
        String txt = story.storyIntro();
        for(String word : txt.split("\n")){
            Thread.sleep(2000);
            System.out.println(word);
        }
    }

}
package com.caged;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

class TextParser {



    public String[] textParser(String text){
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        try {
//            DictionaryGetter dg =  mapper.readValue(new File("./resources/dictionary.yml"), DictionaryGetter.class);
//            System.out.println(dg.getTake().get(1));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        text = text.replaceAll("(?i)[^a-z]"," ");
        //System.out.println(text);
        String sentence[] = text.split(" ");
        String verb = sentence[0].toLowerCase();
        String noun = sentence[sentence.length-1].toLowerCase();
        //System.out.println("Verb: " + verb);
        //System.out.println("Noun: " + noun);
        String sentenceArray[] = {verb, noun};
        return sentenceArray;
    }

    public static void main(String[] args){

        TextParser textParser = new TextParser();
        textParser.textParser("grab a key");
    }
}

package com.caged;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

class TextParser {



    public String[] textParser(String text){
        text = text.replaceAll("(?i)[^a-z]"," ");
        String sentence[] = text.split(" ");
        String verb = sentence[0].toLowerCase();
        String noun = sentence[sentence.length-1].toLowerCase();
        //String input = "grab";
        YAMLReader yamlReader = new YAMLReader();
        List move = yamlReader.dictionaryLoader().getMove();
        List take = yamlReader.dictionaryLoader().getTake();
        List help = yamlReader.dictionaryLoader().getHelp();
        if (move.contains(verb)){
            verb = "move";
        }
        else if (take.contains(verb)){
            verb = "take";
        }
        else if (help.contains(verb)){
            verb = "help";
        }
        else {
            System.out.println("Unrecognized Command");
        }
        String sentenceArray[] = {verb, noun};
        return sentenceArray;
    }

}

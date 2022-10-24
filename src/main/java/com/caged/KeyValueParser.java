package com.caged;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

class KeyValueParser {

    public static void keyValue(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            System.out.println(entry.getKey()+"  ---->  "+entry.getValue());
        }
    }

    public static void key(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            System.out.println(entry.getKey());
        }
    }

    public static void value(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> nodes = node.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            System.out.println(entry.getValue());
        }
    }

}
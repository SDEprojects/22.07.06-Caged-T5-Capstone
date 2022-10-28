package com.caged;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
class GameMap {

    List<String> a = new ArrayList<>();
    List<String> b = new ArrayList<>();
    List<String> c = new ArrayList<>();
    List<String> d = new ArrayList<>();
    List<String> e = new ArrayList<>();
    YAMLMapper mapper = new YAMLMapper();

    public GameMap() {

    }

    public void show(){
        System.out.println(getA());
        System.out.println(getB());
        System.out.println(getC());
        System.out.println(getD());
        System.out.println(getE());
    }

    public void build(){
        a.addAll(Arrays.asList("[ ]","[ ]","[ ]","[ ]"));
        b.addAll(Arrays.asList("[ ]","[ ]","[ ]","[ ]"));
        c.addAll(Arrays.asList("[ ]","[ ]","[ ]","[ ]"));
        d.addAll(Arrays.asList("[ ]","[ ]","[ ]","[ ]"));
        e.addAll(Arrays.asList("[ ]","[ ]","[ ]","[ ]"));
    }

    public void positionUpdate(Player player, LocationGetter location) {
        String playerLocation = player.getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        String myLocation = node.get("room").get(playerLocation).get("grid").textValue(); // c0, asText()
        //String[] part = myLocation.split("(?<=\\D)(?=\\d)");
        String yAxis = myLocation.substring(0,1);
        int xAxis = Integer.parseInt(myLocation.substring(1,2));
        switch (yAxis){
            case ("a"):
                a.set(xAxis, "[X]");
                break;
            case ("b"):
                b.set(xAxis, "[X]");
                break;
            case ("c"):
                c.set(xAxis, "[X]");
                break;
            case ("d"):
                d.set(xAxis, "[X]");
                break;
            case ("e"):
                e.set(xAxis, "[X]");
                break;
            default:
                System.out.println("Location broke");
        }
    }

    public List<String> getA() {
        return a;
    }

    public void setA(List<String> a) {
        this.a = a;
    }

    public List<String> getB() {
        return b;
    }

    public void setB(List<String> b) {
        this.b = b;
    }

    public List<String> getC() {
        return c;
    }

    public void setC(List<String> c) {
        this.c = c;
    }

    public List<String> getD() {
        return d;
    }

    public void setD(List<String> d) {
        this.d = d;
    }

    public List<String> getE() {
        return e;
    }

    public void setE(List<String> e) {
        this.e = e;
    }
}
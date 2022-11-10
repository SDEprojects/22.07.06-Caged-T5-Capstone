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
    List<String> moveLog = new ArrayList<>();

    public GameMap() {

    }

    public void show(){
        for (String i : getA()){
            System.out.print(i);
        }
        System.out.println();
        for (String i : getB()){
            System.out.print(i);
        }
        System.out.println();
        for (String i : getC()){
            System.out.print(i);
        }
        System.out.println();
        for (String i : getD()){
            System.out.print(i);
        }
        System.out.println();
        for (String i : getE()){
            System.out.print(i);
        }
//        System.out.println(getA().toString());
//        System.out.println(getB().toString());
//        System.out.println(getC().toString());
//        System.out.println(getD().toString());
//        System.out.println(getE().toString());
    }

    public void build(){
        a.addAll(Arrays.asList("\u001b[30m\u001b[40m[ ]","[ ]","[ ]","[ ]"," \u001b[0m"));
        b.addAll(Arrays.asList("\u001b[30m\u001b[40m[ ]","[ ]","[ ]","[ ]"," \u001b[0m"));
        c.addAll(Arrays.asList("\u001b[30m\u001b[40m[ ]","[ ]","[ ]","[ ]"," \u001b[0m"));
        d.addAll(Arrays.asList("\u001b[30m\u001b[40m[ ]","[ ]","[ ]","[ ]"," \u001b[0m"));
        e.addAll(Arrays.asList("\u001b[30m\u001b[40m[ ]","[ ]","[ ]","[ ]"," \u001b[0m"));
    }

    public void positionUpdate(Player player, LocationGetter location) {
        String playerLocation = player.getCurrentLocation();
        JsonNode node = mapper.valueToTree(location);
        String myLocation = node.get("room").get(playerLocation).get("grid").textValue(); // c0, asText()
        moveLog.add(myLocation);
        for (String s:moveLog
             ) {
            String yAxis = s.substring(0,1);
            int xAxis = Integer.parseInt(s.substring(1,2));
            switch (yAxis){
                case ("a"):
                    a.set(xAxis, "\u001b[36m\u001b[46m[O]\u001b[30m\u001b[40m");
                    break;
                case ("b"):
                    b.set(xAxis, "\u001b[36m\u001b[46m[O]\u001b[30m\u001b[40m");
                    break;
                case ("c"):
                    c.set(xAxis, "\u001b[36m\u001b[46m[O]\u001b[30m\u001b[40m");
                    break;
                case ("d"):
                    d.set(xAxis, "\u001b[36m\u001b[46m[O]\u001b[30m\u001b[40m");
                    break;
                case ("e"):
                    e.set(xAxis, "\u001b[36m\u001b[46m[O]\u001b[30m\u001b[40m");
                    break;
                default:
                    System.out.println("Location broke");
            }
        }
        //String[] part = myLocation.split("(?<=\\D)(?=\\d)");
        String yAxis = myLocation.substring(0,1);
        int xAxis = Integer.parseInt(myLocation.substring(1,2));
        switch (yAxis){
            case ("a"):
                a.set(xAxis, "\u001b[32m\u001b[42m[X]\u001b[30m\u001b[40m");
                break;
            case ("b"):
                b.set(xAxis, "\u001b[32m\u001b[42m[X]\u001b[30m\u001b[40m");
                break;
            case ("c"):
                c.set(xAxis, "\u001b[32m\u001b[42m[X]\u001b[30m\u001b[40m");
                break;
            case ("d"):
                d.set(xAxis, "\u001b[32m\u001b[42m[X]\u001b[30m\u001b[40m");
                break;
            case ("e"):
                e.set(xAxis, "\u001b[32m\u001b[42m[X]\u001b[30m\u001b[40m");
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
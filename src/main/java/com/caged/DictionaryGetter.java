package com.caged;

import java.util.List;

class DictionaryGetter {
    private List<String> move;
    private List<String> take;
    private List<String> help;
    private List<String> look;
    private List<String> quit;
    private List<String> map;
    private List<String> talk;
    private List<String> attack;
    private List<String> use;
    private List<String> inventory;
    private List<String> drop;
    private List<String> open;



    public DictionaryGetter(List<String> move, List<String> take, List<String> help, List<String> map, List<String> look, List<String> quit, List<String> talk, List<String> attack, List<String> use, List<String> inventory, List<String> drop, List<String> open){
        setMove(move);
        setTake(take);
        setHelp(help);
        setLook(look);
        setQuit(quit);
        setMap(map);
        setTalk(talk);
        setAttack(attack);
        setUse(use);
        setInventory(inventory);
        setDrop(drop);
        setOpen(open);
    }

    public DictionaryGetter(){

    }

    public List<String> getMove() {
        return move;
    }

    public void setMove(List<String> move) {
        this.move = move;
    }

    public List<String> getTake() {
        return take;
    }

    public void setTake(List<String> take) {
        this.take = take;
    }

    public List<String> getHelp() {
        return help;
    }

    public void setHelp(List<String> help) {
        this.help = help;
    }

    public List<String> getMap() {
        return map;
    }

    public void setMap(List<String> map) {
        this.map = map;
    }

    public List<String> getLook() {
        return look;
    }

    public void setLook(List<String> look) {
        this.look = look;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public List<String> getQuit() {
        return quit;
    }

    public void setQuit(List<String> quit) {
        this.quit = quit;
    }

    public List<String> getTalk() {
        return talk;
    }

    public void setTalk(List<String> talk) {
        this.talk = talk;
    }

    public List<String> getAttack() {
        return attack;
    }

    public void setAttack(List<String> attack) {
        this.attack = attack;
    }

    public List<String> getUse() {
        return use;
    }

    public void setUse(List<String> use) {
        this.use = use;
    }

    public List<String> getDrop() {
        return drop;
    }

    public void setDrop(List<String> drop) {
        this.drop = drop;
    }

    public List<String> getOpen() {
        return open;
    }

    public void setOpen(List<String> open) {
        this.open = open;
    }
}

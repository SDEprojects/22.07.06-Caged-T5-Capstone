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
    private List<String> unlock;
    private List<String> equip;
    private List<String> play;
    private List<String> stop;
    private List<String> log;
    private List<String> heal;


    public DictionaryGetter(
            List<String> move, List<String> take, List<String> help, List<String> map,
            List<String> look, List<String> quit, List<String> talk, List<String> attack,
            List<String> use, List<String> inventory, List<String> drop, List<String> unlock,
            List<String> equip, List<String> play, List<String> stop, List<String> log,
            List<String> heal
    ) {
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
        setUnlock(unlock);
        setEquip(equip);
        setPlay(play);
        setStop(stop);
        setLog(log);
        // TODO: added heal for testing - JS
        setHeal(heal);
    }

    public DictionaryGetter() {

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

    public List<String> getUnlock() {
        return unlock;
    }

    public void setUnlock(List<String> unlock) {
        this.unlock = unlock;
    }

    public List<String> getEquip() {
        return equip;
    }

    public void setEquip(List<String> equip) {
        this.equip = equip;
    }

    public List<String> getPlay() {
        return play;
    }

    public void setPlay(List<String> play) {
        this.play = play;
    }

    public List<String> getStop() {
        return stop;
    }

    public void setStop(List<String> stop) {
        this.stop = stop;
    }

    public List<String> getLog() {
        return log;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }
    // TODO: added heal for testing - JS
    public List<String> getHeal() {
        return heal;
    }

    public void setHeal(List<String> heal) {
        this.heal = heal;
    }
}

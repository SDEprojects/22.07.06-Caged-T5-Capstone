package com.caged;

public class CharacterEnemy implements Character{

    private int hp;

    public CharacterEnemy(int hp) {
        this.hp = hp;
    }

    @Override
    public int attack() {
        return (int)(Math.random() * 4);
    }

    @Override
    public int defence() {
        return (int)(Math.random() * 4);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

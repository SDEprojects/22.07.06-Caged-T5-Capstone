package com.caged;

public class CharacterEnemy implements Character{

    private int hp;

    public CharacterEnemy(int hp) {
        this.hp = hp;
    }

    @Override
    public int attack(int attack) {
        return (int)(Math.random() * 4) + attack;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

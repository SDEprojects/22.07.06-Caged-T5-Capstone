package com.caged;

import java.util.List;

class DictionaryGetter {
    private List<String> move;
    private List<String> take;
    private List<String> help;

    public DictionaryGetter(List<String> move,List<String> take,List<String> help){
        setMove(move);
        setTake(take);
        setHelp(help);
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
}

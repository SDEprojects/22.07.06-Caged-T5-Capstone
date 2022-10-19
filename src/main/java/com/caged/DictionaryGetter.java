package com.caged;

import java.util.List;

class DictionaryGetter {
    private List<String> move;
    private List<String> take;

    public DictionaryGetter(List<String> move,List<String> take){
        setMove(move);
        setTake(take);
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
}

package com.caged;

import java.util.List;

class DictionaryGetter {
    private List<String> move;
    private List<String> take;
    private List<String> help;
    private List<String> look;
    private List<String> quit;

    public DictionaryGetter(List<String> move, List<String> take, List<String> help, List<String> look, List<String> quit){
        setMove(move);
        setTake(take);
        setHelp(help);
        setLook(take);
        setQuit(quit);
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

    public List<String> getLook() {
        return look;
    }

    public void setLook(List<String> look) {
        this.look = look;
    }

    public List<String> getQuit() {
        return quit;
    }

    public void setQuit(List<String> quit) {
        this.quit = quit;
    }
}

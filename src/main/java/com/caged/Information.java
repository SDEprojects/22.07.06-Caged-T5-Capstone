package com.caged;

import java.util.List;

public class Information {
    private List<String> story;
    private List<String> objective;
    private List<String> player;

    public Information() {
        super();
    }

    public Information(List<String> story, List<String> objective, List<String> player) {
        setStory(story);
        setObjective(objective);
        setPlayer(player);
    }

    public List<String> getStory() {
        return story;
    }

    public void setStory(List<String> story) {
        this.story = story;
    }

    public List<String> getObjective() {
        return objective;
    }

    public void setObjective(List<String> objective) {
        this.objective = objective;
    }

    public List<String> getPlayer() {
        return player;
    }

    public void setPlayer(List<String> player) {
        this.player = player;
    }
}

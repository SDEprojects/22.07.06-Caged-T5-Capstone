package com.caged;

import java.util.Map;

class CharacterNPCGetter <K, V> {
    private Map<K, V> npc;

    public CharacterNPCGetter(Map<K, V> npc) {
        setNpc(npc);
    }

    public Map<K, V> getNpc() {
        return npc;
    }

    public void setNpc(Map<K, V> npc) {
        this.npc = npc;
    }
}
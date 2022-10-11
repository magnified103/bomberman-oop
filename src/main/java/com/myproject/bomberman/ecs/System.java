package com.myproject.bomberman.ecs;

public class System {

    private World parentWorld;

    public World getParentWorld() {
        return parentWorld;
    }

    public void setParentWorld(World parentWorld) {
        this.parentWorld = parentWorld;
    }
}

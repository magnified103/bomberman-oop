package com.myproject.bomberman;

public abstract class System {

    private World parentWorld;

    public World getParentWorld() {
        return parentWorld;
    }

    public void setParentWorld(World parentWorld) {
        this.parentWorld = parentWorld;
    }

    public void update(double tpf) {

    }
}

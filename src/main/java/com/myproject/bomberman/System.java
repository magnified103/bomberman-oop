package com.myproject.bomberman;

public abstract class System {

    private World parentWorld;

    public World getParentWorld() {
        return parentWorld;
    }

    public void setParentWorld(World parentWorld) {
        this.parentWorld = parentWorld;
    }

    private boolean paused;

    public System() {
        paused = false;
    }

    public void update(double tpf) {

    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
}

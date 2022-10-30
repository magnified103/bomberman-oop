package com.myproject.bomberman.core;

public abstract class System {

    private World parentWorld;

    public World getParentWorld() {
        return parentWorld;
    }

    public void setParentWorld(World parentWorld) {
        this.parentWorld = parentWorld;
    }

    private boolean paused;

    protected System() {
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

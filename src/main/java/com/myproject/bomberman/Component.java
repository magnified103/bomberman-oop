package com.myproject.bomberman;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {

    private List<Entity> linkage;
    private World parentWorld;

    Component() {
        linkage = new ArrayList<>();
    }

    public List<Entity> getLinkage() {
        return linkage;
    }

    public void unsafeDetachFrom(Entity entity) {
        linkage.remove(entity);
    }

    public World getParentWorld() {
        return parentWorld;
    }

    public void setParentWorld(World parentWorld) {
        this.parentWorld = parentWorld;
    }
}
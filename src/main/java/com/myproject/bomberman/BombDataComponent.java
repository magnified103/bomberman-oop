package com.myproject.bomberman;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BombDataComponent extends TimerComponent {

    private int blastRadius;
    private Entity bomber;
    private Set<Entity> entitiesCanStepOn;
    private boolean initialized;

    public BombDataComponent(double time, int blastRadius, Entity bomber) {
        super(time);
        this.blastRadius = blastRadius;
        this.bomber = bomber;
        this.initialized = false;
        this.entitiesCanStepOn = new HashSet<>();
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public Entity getBomber() {
        return bomber;
    }

    public void setEntitiesCanStepOn(Set<Entity> entitiesCanStepOn) {
        this.entitiesCanStepOn = entitiesCanStepOn;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void retain(Collection<Entity> entities) {
        entitiesCanStepOn.retainAll(entities);
    }

    public boolean canStepOn(Entity entity) {
        return entitiesCanStepOn.contains(entity);
    }
}

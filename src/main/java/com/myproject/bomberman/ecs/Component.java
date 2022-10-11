package com.myproject.bomberman.ecs;

public class Component {

    private Entity parentEntity;

    public Entity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(Entity parentEntity) {
        this.parentEntity = parentEntity;
    }
}

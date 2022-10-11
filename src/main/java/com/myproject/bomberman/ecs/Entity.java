package com.myproject.bomberman.ecs;

import java.util.List;

public class Entity {

    private Integer id;
    private List<Component> componentList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Component> getComponentList() {
        return componentList;
    }
}

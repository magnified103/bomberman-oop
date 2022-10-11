package com.myproject.bomberman.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private static final int INITIAL_ID_POOL_SIZE = 10;

    private List<System> systemPool;
    private Map<Integer, Entity> entityMap;
    private List<Component> componentPool;
    private List<Integer> spareId;

    private int entitiesCount;

    public World() {
        systemPool = new ArrayList<>();
        entityMap = new HashMap<>();
        componentPool = new ArrayList<>();
        spareId = new ArrayList<>();
        for (int i = 0; i < INITIAL_ID_POOL_SIZE; i++) {
            spareId.add(i);
        }
        entitiesCount = 0;
    }

    public Entity spawnEntity() {
        if (spareId.isEmpty()) {
            // double the pool size
            for (int i = entitiesCount; i < entitiesCount * 2; i++) {
                spareId.add(i);
            }
        }
        // get the last id and remove from the pool
        Integer id = spareId.get(spareId.size() - 1);
        spareId.remove(spareId.size() - 1);
        // create new entity
        Entity entity = new Entity();
        // assign the id to the newly created entity
        entity.setId(id);
        // map the id back to entity
        entityMap.put(id, entity);
        // increase counter by one
        entitiesCount++;
        return entity;
    }

    public void removeEntity(Integer id) {
        // decrease counter by one
        entitiesCount--;
        // insert back to id pool
        spareId.add(id);
        // get entity
        Entity entity = entityMap.get(id);
        // get component list
        List<Component> componentList = entity.getComponentList();
        // detach all component
        for (Component component : componentList) {
            component.setParentEntity(null);
        }
        // clear the list
        componentList.clear();
    }

    public void addSystem(System system) {
        systemPool.add(system);
        system.setParentWorld(this);
    }

    public void addComponent(Component component) {
        componentPool.add(component);
    }

    public <T extends System> T getSystemByType(Class<T> type) {
        for (System system : systemPool) {
            if (system.getClass() == type) {
                return (T) system;
            }
        }
        return null;
    }

    public <T extends Component> List<T> getComponentsByType(Class<T> type) {
        List<T> components = new ArrayList<>();
        for (Component component : componentPool) {
            if (component.getClass() == type) {
                components.add((T) component);
            }
        }
        return components;
    }
}

package com.myproject.bomberman;

import com.almasb.fxgl.logging.Logger;

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
    private List<System> singletonPool;

    public World() {
        systemPool = new ArrayList<>();
        entityMap = new HashMap<>();
        componentPool = new ArrayList<>();
        spareId = new ArrayList<>();
        for (int i = 0; i < INITIAL_ID_POOL_SIZE; i++) {
            spareId.add(i);
        }
        entitiesCount = 0;
        singletonPool = new ArrayList<>();
    }

    public Entity spawnEntity() {
        if (spareId.isEmpty()) {
            // double the pool size
            for (int i = entitiesCount; i < entitiesCount * 2; i++) {
                spareId.add(i);
            }
        }
        Integer id = spareId.get(spareId.size() - 1);
        spareId.remove(spareId.size() - 1);
        Entity entity = new Entity();
        entity.setId(id);
        entityMap.put(id, entity);
        entitiesCount++;
        entity.setParentWorld(this);
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

    public <T extends System> T getSingletonSystem(Class<T> type) {
        for (System system : singletonPool) {
            if (system.getClass() == type) {
                return (T) system;
            }
        }
        throw new RuntimeException(String.format("Singleton %s not found.", type.getName()));
    }

    public void setSingletonSystem(System system) {
        for (System singleton : singletonPool) {
            if (singleton.getClass() == system.getClass()) {
                throw new RuntimeException(String.format("Singleton %s already exists.",
                        system.getClass().getName()));
            }
        }
        singletonPool.add(system);
        system.setParentWorld(this);
    }

    public void addComponent(Component component) {
        if (componentPool.contains(component)) {
            Logger.get(World.class).info("Attempted to add duplicated component.");
        }
        componentPool.add(component);
    }

    public List<System> getSystemPool() {
        return systemPool;
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

    @SafeVarargs
    public final List<Entity> getEntitiesByType(Class<? extends Component>... types) {
        List<Entity> entityList = new ArrayList<>();
        for (Map.Entry<Integer, Entity> entry : entityMap.entrySet()) {
            Entity entity = entry.getValue();
            if (entity.hasComponents(types)) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public void update(double tpf) {
        for (System system : systemPool) {
            system.update(tpf);
        }
        for (System system : singletonPool) {
            system.update(tpf);
        }
    }
}

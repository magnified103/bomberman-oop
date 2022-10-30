package com.myproject.bomberman.core;

import com.myproject.bomberman.core.utils.EntityList;

import java.util.*;

public class World {

    private static final int INITIAL_ID_POOL_SIZE = 10;

    private List<System> systemPool;
    private Map<Integer, Entity> entityMap;
    private List<Component> componentPool;
    private Map<Class<? extends Component>, List<Component>> componentPoolByType;
    private List<Integer> spareId;
    private int entitiesCount;
    private List<Component> singletonComponentPool;

    public World() {
        systemPool = new ArrayList<>();
        entityMap = new HashMap<>();
        componentPool = new ArrayList<>();
        componentPoolByType = new HashMap<>();
        spareId = new ArrayList<>();
        for (int i = 0; i < INITIAL_ID_POOL_SIZE; i++) {
            spareId.add(i);
        }
        entitiesCount = 0;
        singletonComponentPool = new ArrayList<>();
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

    public void removeEntity(Entity entity) {
        entity.destroyFxglEntity();
        Integer id = entity.getId();
        entitiesCount--;
        spareId.add(id);
        entity.detachAllComponents();
        entityMap.remove(id);
    }

    public void removeEntityComponents(Entity entity) {
        List<Component> componentList = new ArrayList<>(entity.getComponentList());
        removeEntity(entity);
        for (Component component : componentList) {
            if (component.getLinkage().isEmpty()) {
                removeComponent(component);
            }
        }
    }

    public void removeEntityById(Integer id) {
        removeEntity(entityMap.get(id));
    }

    public void clearAllEntitiesAndComponents() {
        List<Entity> entityList = new ArrayList<>(entityMap.values());
        for (Entity entity : entityList) {
            removeEntity(entity);
        }
        List<Component> componentList = new ArrayList<>(componentPool);
        for (Component component : componentList) {
            removeComponent(component);
        }
    }

    public boolean contains(Entity entity) {
        return entityMap.containsValue(entity);
    }

    public void addSystem(System system) {
        for (System other : systemPool) {
            if (other.getClass() == system.getClass()) {
                throw new RuntimeException(String.format("System %s already exists.", system.getClass().getName()));
            }
        }
        systemPool.add(system);
        system.setParentWorld(this);
    }

    public <T extends System> T getSystem(Class<T> type) {
        for (System system : systemPool) {
            if (system.getClass() == type) {
                return type.cast(system);
            }
        }
        throw new RuntimeException(String.format("System %s not found.", type.getName()));
    }

    public void pauseSystem(Class<? extends System> type) {
        for (System system : systemPool) {
            if (system.getClass() == type) {
                system.pause();
            }
        }
    }

    public void resumeSystem(Class<? extends System> type) {
        for (System system : systemPool) {
            if (system.getClass() == type) {
                system.resume();
            }
        }
    }

    public void addComponent(Component component) {
        if (componentPool.contains(component)) {
            throw new RuntimeException("Attempted to add duplicated component.");
        }
        componentPool.add(component);
        if (!componentPoolByType.containsKey(component.getClass())) {
            componentPoolByType.put(component.getClass(), new ArrayList<>());
        }
        componentPoolByType.get(component.getClass()).add(component);
        component.setParentWorld(this);
    }

    public void removeComponent(Component component) {
        if (!componentPool.contains(component) && !singletonComponentPool.contains(component)) {
            throw new RuntimeException("Attempted to remove a non-existence component.");
        }
        List<Entity> linkage = new ArrayList<>(component.getLinkage());
        for (Entity entity : linkage) {
            entity.detach(component);
        }
        component.setParentWorld(null);
        componentPool.remove(component);
        if (componentPoolByType.containsKey(component.getClass())) {
            componentPoolByType.get(component.getClass()).remove(component);
        }
        singletonComponentPool.remove(component);
    }

    public <T extends Component> T getSingletonComponent(Class<T> type) {
        for (Component component : singletonComponentPool) {
            if (component.getClass() == type) {
                return type.cast(component);
            }
        }
        throw new RuntimeException(String.format("Singleton %s not found.", type.getName()));
    }

    public void setSingletonComponent(Component component) {
        for (int i = 0; i < singletonComponentPool.size(); i++) {
            Component singleton = singletonComponentPool.get(i);
            if (singleton.getClass() == component.getClass()) {
                singletonComponentPool.set(i, component);
                component.setParentWorld(this);
                return;
            }
        }
        singletonComponentPool.add(component);
        component.setParentWorld(this);
    }

    public List<System> getSystemPool() {
        return systemPool;
    }

    public <T extends Component> List<T> getComponentsByType(Class<T> type) {
        List<T> components = new ArrayList<>();
        if (componentPoolByType.containsKey(type)) {
            components.addAll((Collection<T>) componentPoolByType.get(type));
        }
        for (Component component : singletonComponentPool) {
            if (component.getClass() == type) {
                components.add(type.cast(component));
            }
        }
        return components;
    }

    public <T extends Component> List<T> getComponentsBySuperType(Class<T> type) {
        List<T> components = new ArrayList<>();
        componentPoolByType.forEach((componentType, componentList) -> {
            if (type.isAssignableFrom(componentType)) {
                components.addAll((Collection<T>) componentList);
            }
        });
        for (Component component : singletonComponentPool) {
            if (type.isAssignableFrom(component.getClass())) {
                components.add(type.cast(component));
            }
        }
        return components;
    }

    @SafeVarargs
    public final List<Entity> getEntitiesByTypes(Class<? extends Component>... types) {
        List<Entity> entityList = new ArrayList<>();
        for (Map.Entry<Integer, Entity> entry : entityMap.entrySet()) {
            Entity entity = entry.getValue();
            if (entity.has(types)) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public <A extends Component, B extends Component> EntityList<A, B, Component> getEntitiesByTypes
            (Class<A> typeA, Class<B> typeB) {
        EntityList<A, B, Component> entityList = new EntityList<>(typeA, typeB, Component.class);
        for (Map.Entry<Integer, Entity> entry : entityMap.entrySet()) {
            Entity entity = entry.getValue();
            if (entity.has(typeA, typeB)) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public <A extends Component, B extends Component, C extends Component> EntityList<A, B, C> getEntitiesByTypes
            (Class<A> typeA, Class<B> typeB, Class<C> typeC) {
        EntityList<A, B, C> entityList = new EntityList<>(typeA, typeB, typeC);
        for (Map.Entry<Integer, Entity> entry : entityMap.entrySet()) {
            Entity entity = entry.getValue();
            if (entity.has(typeA, typeB, typeC)) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public void update(double tpf) {
        List<System> currentSystemPool = new ArrayList<>(systemPool);
        for (System system : currentSystemPool) {
            if (!system.isPaused()) {
                system.update(tpf);
            }
        }
    }
}
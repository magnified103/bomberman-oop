package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private World parentWorld;
    private Integer id;
    private List<Component> componentList;
    private com.almasb.fxgl.entity.Entity fxglEntity;

    Entity() {
        componentList = new ArrayList<>();
        fxglEntity = FXGL.entityBuilder().buildAndAttach();
    }

    public void initFxglEntity() {
        fxglEntity = FXGL.entityBuilder().buildAndAttach();
    }

    public void destroyFxglEntity() {
        fxglEntity.getWorld().removeEntity(fxglEntity);
    }

    public World getParentWorld() {
        return parentWorld;
    }

    public void setParentWorld(World parentWorld) {
        this.parentWorld = parentWorld;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    @SafeVarargs
    public final boolean has(Class<? extends Component>... types) {
        for (Class<? extends Component> type : types) {
            boolean found = false;
            for (Component component : componentList) {
                if (component.getClass() == type) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public <T extends Component> T getComponentByType(Class<T> type) {
        for (Component component : componentList) {
            if (component.getClass() == type) {
                return (T) component;
            }
        }
        throw new RuntimeException(String.format("%s not found.", type.getName()));
    }

    public <T extends Component> T attach(T component) {
        if (component.getParentWorld() != parentWorld) {
            throw new RuntimeException("Attempted to attach an unknown component.");
        }
        for (Component attachedComponent : componentList) {
            if (attachedComponent.getClass() == component.getClass()) {
                throw new RuntimeException(String.format("%s was attached previously.",
                        attachedComponent.getClass().getName()));
            }
        }
        componentList.add(component);
        component.getLinkage().add(this);
        if (component.getClass() == FxglBoundingBoxComponent.class) {
            ((FxglBoundingBoxComponent) component).setFxglComponent(fxglEntity.getBoundingBoxComponent());
        }
        if (component.getClass() == FxglTransformComponent.class) {
            ((FxglTransformComponent) component).setFxglComponent(fxglEntity.getTransformComponent());
        }
        if (component.getClass() == FxglViewComponent.class) {
            ((FxglViewComponent) component).setFxglComponent(fxglEntity.getViewComponent());
        }
        return component;
    }

    public <T extends Component> T addAndAttach(T component) {
        parentWorld.addComponent(component);
        return attach(component);
    }

    public void detach(Component component) {
        if (!componentList.contains(component)) {
            throw new RuntimeException(String.format("%s not found.", component.getClass()));
        }
        componentList.remove(component);
        component.unsafeDetachFrom(this);
        if (component.getClass() == FxglBoundingBoxComponent.class) {
            ((FxglBoundingBoxComponent) component).setFxglComponent(null);
        }
        if (component.getClass() == FxglTransformComponent.class) {
            ((FxglTransformComponent) component).setFxglComponent(null);
        }
        if (component.getClass() == FxglViewComponent.class) {
            ((FxglViewComponent) component).setFxglComponent(null);
        }
    }

    public void detach(Class<? extends Component> type) {
        Component component = null;
        for (Component attachedComponent : componentList) {
            if (attachedComponent.getClass() == type) {
                component = attachedComponent;
            }
        }
        if (component == null) {
            throw new RuntimeException(String.format("%s not found.", type.getName()));
        }
        detach(component);
    }

    public void detachAllComponents() {
        List<Component> components = new ArrayList<>(componentList);
        for (Component component : components) {
            detach(component);
        }
    }
}
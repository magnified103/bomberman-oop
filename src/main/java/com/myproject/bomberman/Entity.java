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
    public final boolean hasComponents(Class<? extends Component>... types) {
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

    public void attachComponent(Component component) {
        for (Component attachedComponent : componentList) {
            if (attachedComponent.getClass() == component.getClass()) {
                throw new RuntimeException(String.format("%s was attached previously.",
                        attachedComponent.getClass().getName()));
            }
        }
        componentList.add(component);
        component.setParentEntity(this);
        parentWorld.addComponent(component);
        if (component.getClass() == BoundingBoxComponent.class) {
            ((BoundingBoxComponent) component).setFxglComponent(fxglEntity.getBoundingBoxComponent());
        }
        if (component.getClass() == TransformComponent.class) {
            ((TransformComponent) component).setFxglComponent(fxglEntity.getTransformComponent());
        }
        if (component.getClass() == ViewComponent.class) {
            ((ViewComponent) component).setFxglComponent(fxglEntity.getViewComponent());
        }
    }
}

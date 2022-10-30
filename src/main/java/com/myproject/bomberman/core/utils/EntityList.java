package com.myproject.bomberman.core.utils;

import com.myproject.bomberman.core.Component;
import com.myproject.bomberman.core.Entity;

import java.util.ArrayList;

public class EntityList<
        A extends Component,
        B extends Component,
        C extends Component
        > extends ArrayList<Entity> {

    private final Class<A> typeA;
    private final Class<B> typeB;
    private final Class<C> typeC;

    public EntityList(Class<A> typeA, Class<B> typeB, Class<C> typeC) {
        this.typeA = typeA;
        this.typeB = typeB;
        this.typeC = typeC;
    }

    public void forEach(Consumer3<A, B, C> action) {
        super.forEach((entity) -> {
            action.accept(
                    entity.getComponentByType(typeA),
                    entity.getComponentByType(typeB),
                    entity.getComponentByType(typeC)
            );
        });
    }

    public void forEach(Consumer2<A, B> action) {
        super.forEach((entity) -> {
            action.accept(
                    entity.getComponentByType(typeA),
                    entity.getComponentByType(typeB)
            );
        });
    }

    @FunctionalInterface
    public interface Consumer2<A, B> {
        void accept(A a, B b);
    }

    @FunctionalInterface
    public interface Consumer3<A, B, C> {
        void accept(A a, B b, C c);
    }
}
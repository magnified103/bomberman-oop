package com.myproject.bomberman.components;

import com.myproject.bomberman.core.Entity;

public class FlameDataComponent extends TimerComponent {

    private Entity bomber;

    public FlameDataComponent(double time, Entity bomber) {
        super(time);
        this.bomber = bomber;
    }

    public Entity getBomber() {
        return bomber;
    }
}

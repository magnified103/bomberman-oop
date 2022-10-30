package com.myproject.bomberman.systems;

import com.myproject.bomberman.components.TimerComponent;
import com.myproject.bomberman.core.System;

public class TimerSystem extends System {

    @Override
    public void update(double tpf) {
        getParentWorld().getComponentsBySuperType(TimerComponent.class).forEach((component) -> {
            component.tick(tpf);

            if (component.isFinished()) {
                component.onFinish(tpf);
            }
        });
    }
}

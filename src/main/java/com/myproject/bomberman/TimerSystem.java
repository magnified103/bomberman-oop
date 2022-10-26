package com.myproject.bomberman;

import java.util.List;

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

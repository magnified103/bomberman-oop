package com.myproject.bomberman;

import java.util.List;

public class TimerSystem extends System {

    @Override
    public void update(double tpf) {
        List<TimerComponent> componentList = getParentWorld().getComponentsBySuperType(TimerComponent.class);

        for (TimerComponent component : componentList) {
            component.tick(tpf);
        }
    }
}

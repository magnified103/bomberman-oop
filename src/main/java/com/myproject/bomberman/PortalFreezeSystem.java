package com.myproject.bomberman;

import java.util.List;

public class PortalFreezeSystem extends System {

    @Override
    public void update(double tpf) {
        List<PortalFreezeComponent> components = getParentWorld().getComponentsByType(PortalFreezeComponent.class);
        for (PortalFreezeComponent timer : components) {
            if (timer.isFinished()) {
                getParentWorld().removeComponent(timer);
                getParentWorld().setSingletonComponent(new TitleScreenComponent(4));
            }
        }
    }
}

package com.myproject.bomberman;

import java.util.List;

public class TitleScreenSystem extends System {

    @Override
    public void update(double tpf) {
        List<TitleScreenComponent> titles = getParentWorld().getComponentsByType(TitleScreenComponent.class);

        for (TitleScreenComponent title : titles) {
            if (title.isFinished()) {
                getParentWorld().removeComponent(title);
                getParentWorld().addSystem(new WalkSystem());
                getParentWorld().addSystem(new WalkAnimationSystem());
                getParentWorld().addSystem(new CollisionSystem());
                getParentWorld().addSystem(new BombDetonationSystem());
                getParentWorld().addSystem(new BombingSystem());
                getParentWorld().addSystem(new FlameSystem());
                getParentWorld().addSystem(new BrickOnFireSystem());
                getParentWorld().addSystem(new BotRandomWalkSystem());
                getParentWorld().addSystem(new DeathSystem());
                getParentWorld().addSystem(new PortalSystem());
                getParentWorld().getSingletonSystem(TerrainSystem.class).load("./Level2.txt");
            }
        }
    }
}

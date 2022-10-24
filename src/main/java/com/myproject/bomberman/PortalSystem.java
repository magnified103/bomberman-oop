package com.myproject.bomberman;

import javafx.util.Pair;

import java.util.List;

public class PortalSystem extends CollisionSystem {

    @Override
    public void update(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.PORTAL);

        for (Pair<Entity, Entity> pair : collisionPairs) {
            Entity player = pair.getKey();
            Entity portal = pair.getValue();

            if (!getParentWorld().getComponentsBySuperType(BotWalkComponent.class).isEmpty()) {
//                continue;
            }
            getParentWorld().clearOrdinarySystem();
            getParentWorld().addSystem(new TimerSystem());
            getParentWorld().setSingletonComponent(new PortalFreezeComponent(4));
        }
    }
}
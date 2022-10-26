package com.myproject.bomberman;

import java.util.List;

public class FlameSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> flameList = getParentWorld().getEntitiesByType(
                TransformComponent.class,
                FlameDataComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainUtility system = getParentWorld().getSystem(TerrainUtility.class);

        for (Entity flame : flameList) {
            FlameDataComponent data = flame.getComponentByType(FlameDataComponent.class);
            TransformComponent transform = flame.getComponentByType(TransformComponent.class);

            if (data.isFinished()) {
                if (data.getBomber() != null
                        && getParentWorld().contains(data.getBomber())
                        && data.getBomber().has(BombingComponent.class)) {
                    // reset player's bomb delays
                    data.getBomber().getComponentByType(BombingComponent.class).raiseLimitBy(1);
                }
                system.resetTile(terrain.getRowIndex(transform.getY()), terrain.getColumnIndex(transform.getX()));
                getParentWorld().removeEntityComponents(flame);
            }
        }
    }
}

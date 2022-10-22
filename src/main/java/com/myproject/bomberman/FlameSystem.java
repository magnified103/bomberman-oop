package com.myproject.bomberman;

import java.util.List;

public class FlameSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> flameList = getParentWorld().getEntitiesByType(
                FxglTransformComponent.class,
                FlameDataComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainSystem system = getParentWorld().getSingletonSystem(TerrainSystem.class);

        for (Entity flame : flameList) {
            FlameDataComponent data = flame.getComponentByType(FlameDataComponent.class);
            FxglTransformComponent transform = flame.getComponentByType(FxglTransformComponent.class);
            data.tick(tpf);

            if (data.isFinished()) {
                if (data.getBomber() != null && getParentWorld().contains(data.getBomber())) {
                    // reset player's bomb delays
                    data.getBomber().getComponentByType(BombingInputComponent.class).raiseLimitBy(1);
                }
                system.resetTile(terrain.getRowIndex(transform.getY()), terrain.getColumnIndex(transform.getX()));
                getParentWorld().removeEntityComponents(flame);
            }
        }
    }
}

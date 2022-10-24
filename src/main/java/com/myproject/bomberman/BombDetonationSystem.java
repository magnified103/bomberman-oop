package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BombDetonationSystem extends System {

    private void spawnFireTrail(int centerRowIndex, int centerColumnIndex,
                                int alpha, int beta,
                                int blastRadius, String trailBody, String trailHead) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainSystem system = getParentWorld().getSingletonSystem(TerrainSystem.class);

        // coordinates of fire path
        List<Pair<Integer, Integer>> trail = new ArrayList<>();
        boolean noHead;

        // go right
        noHead = false;
        for (int i = 1; i <= blastRadius; i++) {
            int rowIndex = centerRowIndex + alpha * i;
            int columnIndex = centerColumnIndex + beta * i;
            if (!terrain.validTile(rowIndex, columnIndex)) {
                noHead = true;
                break;
            }
            Tile tile = terrain.getTile(rowIndex, columnIndex);
            if (tile == Tile.BRICK
                    || tile == Tile.UNEXPOSED_PORTAL
                    || tile == Tile.UNEXPOSED_BOMB_ITEM
                    || tile == Tile.UNEXPOSED_FLAME_ITEM
                    || tile == Tile.UNEXPOSED_SPEED_ITEM) {
                Entity brick = terrain.getEntity(rowIndex, columnIndex);
                brick.addAndAttach(new BrickOnFireComponent(1));
                brick.getComponentByType(BrickAnimationComponent.class).fire();
                noHead = true;
                break;
            }
            if (tile == Tile.BOMB) {
                Entity bomb = terrain.getEntity(rowIndex, columnIndex);
                // chain explosion
                bomb.getComponentByType(BombDataComponent.class).reset(0);
            }
            if (tile != Tile.GRASS) {
                noHead = true;
                break;
            }
            trail.add(new Pair<>(rowIndex, columnIndex));
        }
        if (noHead) {
            for (Pair<Integer, Integer> pair : trail) {
                int rowIndex = pair.getKey();
                int columnIndex = pair.getValue();
                system.spawnFlame(rowIndex, columnIndex, 0.7, null, trailBody);
            }
        } else {
            for (int i = 0; i + 1 < trail.size(); i++) {
                int rowIndex = trail.get(i).getKey();
                int columnIndex = trail.get(i).getValue();
                system.spawnFlame(rowIndex, columnIndex, 0.7, null, trailBody);
            }
            int rowIndex = trail.get(trail.size() - 1).getKey();
            int columnIndex = trail.get(trail.size() - 1).getValue();
            system.spawnFlame(rowIndex, columnIndex, 0.7, null, trailHead);
        }
    }

    @Override
    public void update(double tpf) {
        List<Entity> bombList = getParentWorld().getEntitiesByType(
                FxglTransformComponent.class,
                BombDataComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainSystem system = getParentWorld().getSingletonSystem(TerrainSystem.class);

        for (Entity bomb : bombList) {
            BombDataComponent data = bomb.getComponentByType(BombDataComponent.class);
            FxglTransformComponent transform = bomb.getComponentByType(FxglTransformComponent.class);

            if (data.isFinished()) {
                FXGL.play("sfxExplosion.wav");
                int rowIndex = terrain.getRowIndex(transform.getY());
                int columnIndex = terrain.getColumnIndex(transform.getX());
                int blastRadius = data.getBlastRadius();

                system.resetTile(rowIndex, columnIndex);
                Entity bomber = data.getBomber();
                getParentWorld().removeEntityComponents(bomb);
                system.spawnFlame(rowIndex, columnIndex, 0.7, bomber, "flameCore.png");

                // go right
                spawnFireTrail(rowIndex, columnIndex, 0, 1, blastRadius,
                        "flameRight.png", "flameRightHead.png");

                // go left
                spawnFireTrail(rowIndex, columnIndex, 0, -1, blastRadius,
                        "flameLeft.png", "flameLeftHead.png");

                // go down
                spawnFireTrail(rowIndex, columnIndex, 1, 0, blastRadius,
                        "flameDown.png", "flameDownHead.png");

                // go up
                spawnFireTrail(rowIndex, columnIndex, -1, 0, blastRadius,
                        "flameUp.png", "flameUpHead.png");
            }
        }
    }
}

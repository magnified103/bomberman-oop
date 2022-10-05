package com.myproject.bomberman;

public final class BomberData {
    public static enum EntityType {
        FLAME,
        BOOM,
        PLAYER,
        GRASS,
        DOOR,
        WALL,
        SHELTER
    }
    public static int grid[][] = new int[100][100];
    public static final int SPEED = 100;
    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_WIDTH = 800;
    public static final int ENTITY_SIZE = 32;

    public static int FLAME_SIZE = 2;
    public static final int MOVE_SUPPORT_PIXEL = 6;
}

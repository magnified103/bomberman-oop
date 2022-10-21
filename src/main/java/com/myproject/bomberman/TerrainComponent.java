package com.myproject.bomberman;

public class TerrainComponent extends Component {

    private int numberOfRows;
    private int numberOfColumns;
    private double tileWidth;
    private double tileHeight;
    private Tile grid[][];
    private Entity entityGrid[][];

    TerrainComponent(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        grid = new Tile[numberOfRows][numberOfColumns];
        entityGrid = new Entity[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                grid[i][j] = Tile.GRASS;
            }
        }
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(double tileWidth) {
        this.tileWidth = tileWidth;
    }

    public double getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(double tileHeight) {
        this.tileHeight = tileHeight;
    }

    public Tile getTile(int i, int j) {
        return grid[i][j];
    }

    public void setTile(int i, int j, Tile type) {
        grid[i][j] = type;
    }

    public Entity getEntity(int i, int j) {
        return entityGrid[i][j];
    }

    public void setEntity(int i, int j, Entity entity) {
        entityGrid[i][j] = entity;
    }
}

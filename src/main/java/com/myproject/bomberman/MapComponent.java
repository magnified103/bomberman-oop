package com.myproject.bomberman;

public class MapComponent extends Component {

    private int numberOfRows;
    private int numberOfColumns;
    private double cellWidth;
    private double cellHeight;
    private MapCell grid[][];
    private Entity gridEntity[][];

    MapComponent(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        grid = new MapCell[numberOfRows][numberOfColumns];
        gridEntity = new Entity[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                grid[i][j] = MapCell.GRASS;
            }
        }
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
    }

    public MapCell getCell(int i, int j) {
        return grid[i][j];
    }

    public void setCell(int i, int j, MapCell type) {
        grid[i][j] = type;
    }

    public Entity getCellEntity(int i, int j) {
        return gridEntity[i][j];
    }

    public void setCellEntity(int i, int j, Entity entity) {
        gridEntity[i][j] = entity;
    }
}

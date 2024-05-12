package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Eyeball {
    
    private int row;
    private int column;
    private Direction direction;

    // Constructor
    public Eyeball(int row, int column, Direction direction) {
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Color getCurrentColor(Game game) {
        return getCurrentSquare(game).getColor();
    }

    public Shape getCurrentShape(Game game) {
        return getCurrentSquare(game).getShape();
    }

    public Square getCurrentSquare(Game game) {
        return game.getSquareAt(row, column);
    }

}
package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Eyeball {
    
    private int row;
    private int column;
    private Direction direction;

    // Constructor initialisation of eyeball's position and direction
    public Eyeball(int row, int column, Direction direction) {
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    // Get eyeball row position 
    public int getRow() {
        return row;
    }

    // Get eyeball column position
    public int getColumn() {
        return column;
    }

    // Get eyeball facing direction
    public Direction getDirection() {
        return direction;
    }

    // Set eyeball position 
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // Set eyeball facing direction
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Get colour of the square where eyeball is present
    public Color getCurrentColor(Game game) {
        return getCurrentSquare(game).getColor();
    }

    // Get the shape of the square where eyeball is present
    public Shape getCurrentShape(Game game) {
        return getCurrentSquare(game).getShape();
    }

    // Get the square object where eyeball is present
    public Square getCurrentSquare(Game game) {
        return game.getSquareAt(row, column);
    }
}
package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class PlayableSquare extends Square {

    private final Color color;
    private final Shape shape;

    // Constructor
    public PlayableSquare(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}
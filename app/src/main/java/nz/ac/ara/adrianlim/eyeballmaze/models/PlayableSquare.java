package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class PlayableSquare extends Square {

    private Color color;
    private Shape shape;

    // Constructor
    public PlayableSquare(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    // get the color of the playable square
    @Override
    public Color getColor() {
        return color;
    }

    // get the shape of the playable square
    @Override
    public Shape getShape() {
        return shape;
    }
}
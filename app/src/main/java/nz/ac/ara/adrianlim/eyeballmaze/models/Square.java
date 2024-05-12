package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Square {
    protected Color color;
    protected Shape shape;

    // Constructor
    public Square(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    // Default constructor for BlankSquare initialisation
    public Square() {
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }
}
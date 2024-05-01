package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Square {
    // Protected as it is going to be extended by PlayableSquare and BlankSquare class
    protected Color color;
    protected Shape shape;

    // Constructor for PlayableSquare initialisation
    public Square(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    // Default constructor for BlankSquare initialisation
    public Square() {
    }

    // Get color of the square
    public Color getColor() {
        return color;
    }

    // Get shape of the square
    public Shape getShape() {
        return shape;
    }
}
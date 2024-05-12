package nz.ac.ara.adrianlim.eyeballmaze.models;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class BlankSquare extends Square {
    @Override
    public Color getColor() {
        return Color.BLANK;
    }

    @Override
    public Shape getShape() {
        return Shape.BLANK;
    }
}
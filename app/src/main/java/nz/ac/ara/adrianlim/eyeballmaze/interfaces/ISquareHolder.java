package nz.ac.ara.adrianlim.eyeballmaze.interfaces;

import nz.ac.ara.adrianlim.eyeballmaze.models.Square;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public interface ISquareHolder {
	public void addSquare(Square square, int row, int column);

	public Color getColorAt(int row, int column);

	public Shape getShapeAt(int row, int column);
}

package nz.ac.ara.adrianlim.eyeballmaze.models;

import java.util.Objects;

public class Position {

    private int row;
    private int column;

    // Constructor
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        // Check that objects are the same instance
        if (this == obj) {
            return true;
        }
        // Check that object is null or different class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Cast the other object to Position and compare its row and column
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }

    // Using util.Objects hashCode() to generate a hash code for the Position object
    @Override
    public int hashCode() {
        // Objects.hash utility to generate a hash code based on row and column
        return Objects.hash(row, column);
    }
}
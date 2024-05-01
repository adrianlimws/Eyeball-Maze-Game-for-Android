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

    // Get row value
    public int getRow() {
        return row;
    }

    // Get column value
    public int getColumn() {
        return column;
    }

    // Compare two Position objects are equal
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
    
    /* 		HashMap 				        vs 		     	HashSet
     * key-value pair						|  No key-value mapping		
     * Each key is unique					|  Each element is unique
     * Doesn't allow duplicate keys			|  No duplicate elements as it will
     * BUT it allows duplicate values.  	|  not add it again.
     * Retrieve with get() or containsKey()	|  Retrieve with contains()
     * 		None of them have a specific order how the keys/element are stored.	
     * 					
     * 		<Explaination to future-self on why you choose HashSet over HashMap>
     * .Every level has a unique goal position, HashSet prevents duplication of goal position.
     *  Meaning each goal position is only stored ONCE.
     * .Only need to store position(one value/element), no need for a key-value mapping function.
     * 
     * No clue about performance between the two, that's for another time to research...
     * 
     * 	https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
     * 	https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
     */
}
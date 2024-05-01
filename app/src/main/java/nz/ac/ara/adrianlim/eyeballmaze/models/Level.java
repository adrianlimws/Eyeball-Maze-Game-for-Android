package nz.ac.ara.adrianlim.eyeballmaze.models;

import java.util.HashSet;
import java.util.Set;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Level {

	private int height;
    private int width;
    // 2D array to store squares for each level
    private Square[][] squares;
    // Set positions of the goals in a level
    private Set<Position> goals = new HashSet<>();

    private int completedGoalCount;

    // Constructor 
    // initialize the level with the given height and width
    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        this.squares = new Square[height][width];
    }

    // Square-related methods

    // Add a square with the given row and column position
    public void addSquare(Square square, int row, int column) {
        squares[row][column] = square;
    }

    // Get the square based on row and column position
    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    // Get color of the square based on row and column position
    public Color getColorAt(int row, int column) {
        return getSquare(row, column).getColor();
    }

    // Get shape of the square based on row and column position
    public Shape getShapeAt(int row, int column) {
        return getSquare(row, column).getShape();
    }

    // Goal-related methods

    // Add a goal at the given row and column position
    public void addGoal(int row, int column) {
        goals.add(new Position(row, column));
    }

    // Get count of goals in the level
    public int getGoalCount() {
        return goals.size();
    }

    // Check if there is a goal at the given row and column position
    public boolean hasGoalAt(int row, int column) {
        return goals.stream().anyMatch(goal -> goal.getRow() == row && goal.getColumn() == column);
    }

    // Get the count of completed goals
    public int getCompletedGoalCount() {
        return completedGoalCount;
    }

    // Direction/Movement methods

    // Check if the given row and column position is a valid move for eyeball
    public boolean isDirectionOK(int row, int column, Eyeball eyeball) {
        int currentRow = eyeball.getRow();
        int currentColumn = eyeball.getColumn();
        Direction currentDirection = eyeball.getDirection();

        // Check for diagonal move
        if (row != currentRow && column != currentColumn) {
            return false;
        }

        // Check if moving backwards based on the current direction
        switch (currentDirection) {
            case UP:
                return row <= currentRow;
            case DOWN:
                return row >= currentRow;
            case LEFT:
                return column <= currentColumn;
            case RIGHT:
                return column >= currentColumn;
            default:
                return false;
        }
    }


    // Check if there is a blank-free path from eyeball's current position to the given row and column position
    public boolean hasBlankFreePathTo(int row, int column, Eyeball eyeball) {
        int currentRow = eyeball.getRow();
        int currentColumn = eyeball.getColumn();

        if (row == currentRow) {
            // Moving horizontally
            int start = Math.min(currentColumn, column);
            int end = Math.max(currentColumn, column);
            for (int col = start + 1; col < end; col++) {
            	if (getSquare(row, col) instanceof BlankSquare) {
            	    return false;
            	}
            }
        } else if (column == currentColumn) {
            // Moving vertically
            int start = Math.min(currentRow, row);
            int end = Math.max(currentRow, row);
            for (int r = start + 1; r < end; r++) {
                if (getSquare(r, column) instanceof BlankSquare) {
                    return false;
                }
            }
        }

        return true;
    }

    // Get appropriate message for the given row and column position
    public Message checkDirectionMessage(int row, int column, Eyeball eyeball) {
    	if (!isDirectionOK(row, column, eyeball)) {
    		if (row != eyeball.getRow() && column != eyeball.getColumn()) {
    			return Message.MOVING_DIAGONALLY;
    		} else {
    			return Message.BACKWARDS_MOVE;
    		}
    	}
    	return Message.OK;
    }
    
    // Get appropriate message for the given row and column position, checking for blank squares in the path
    public Message checkMessageForBlankOnPathTo(int row, int column, Eyeball eyeball) {
        if (!hasBlankFreePathTo(row, column, eyeball)) {
            return Message.MOVING_OVER_BLANK;
        }
        return Message.OK;
    }

    // Check if the eyeball can move to the given row and column position
    public boolean canMoveTo(int row, int column, Eyeball eyeball, Game game) {
        if (!isDirectionOK(row, column, eyeball) || !hasBlankFreePathTo(row, column, eyeball)) {
            return false;
        }

        Square targetSquare = getSquare(row, column);
        Color currentColor = eyeball.getCurrentColor(game);
        Shape currentShape = eyeball.getCurrentShape(game);

        // Check if the target square has the same color or shape as the eyeball's current color or shape
        return targetSquare.getColor() == currentColor || targetSquare.getShape() == currentShape;
    }

    // Get the appropriate message for the given row and column position, considering all conditions
    public Message MessageIfMovingTo(int row, int column, Eyeball eyeball, Game game) {
        if (!isDirectionOK(row, column, eyeball)) {
            return checkDirectionMessage(row, column, eyeball);
        }

        if (!hasBlankFreePathTo(row, column, eyeball)) {
            return checkMessageForBlankOnPathTo(row, column, eyeball);
        }

        if (!canMoveTo(row, column, eyeball, game)) {
            return Message.DIFFERENT_SHAPE_OR_COLOR;
        }

        return Message.OK;
    }

    // Method to move the eyeball to the given row and column position
    public void moveTo(int row, int column, Eyeball eyeball) {
        
    	// Replace current square with a BlankSquare
        squares[eyeball.getRow()][eyeball.getColumn()] = new BlankSquare();

        Position currentPosition = new Position(eyeball.getRow(), eyeball.getColumn());
        Position targetPosition = new Position(row, column);

        // If current position is a goal, remove it from the goals set and replace it with BlankSquare
        if (hasGoalAt(eyeball.getRow(), eyeball.getColumn())) {
            squares[eyeball.getRow()][eyeball.getColumn()] = new BlankSquare();
            goals.remove(currentPosition);
        }

        // If the target position is a goal, remove it from goals set and increment the completedGoalCount
        if (hasGoalAt(row, column)) {
            goals.remove(targetPosition);
            completedGoalCount++;
        }

        // Calculate difference between the target row and the eyeball's current row
        int rowDiff = row - eyeball.getRow();
        int colDiff = column - eyeball.getColumn();

        // Update eyeball's direction based on the move direction
        eyeball.setDirection(Math.abs(rowDiff) > Math.abs(colDiff)
        	    ? (rowDiff < 0 ? Direction.UP : Direction.DOWN) // Vertical
        	    : (colDiff < 0 ? Direction.LEFT : Direction.RIGHT)); // Horizontal

        // Update eyeball's position
        eyeball.setPosition(row, column);
    }

    // Getters
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
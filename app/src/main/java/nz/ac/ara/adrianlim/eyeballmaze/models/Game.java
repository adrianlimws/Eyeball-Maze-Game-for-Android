package nz.ac.ara.adrianlim.eyeballmaze.models;

import java.util.ArrayList;
import java.util.List;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Game {
    // List to hold all levels
    private List<Level> levels = new ArrayList<>();
    // Refer to current level
    private Level currentLevel;
    // Refer to eyeball object 
    private Eyeball eyeball;

    // Level methods
    
//    // Create a level with specified height/width and add to levels list
//    public void addLevel(int height, int width) {
//        currentLevel = new Level(height, width);
//        levels.add(currentLevel);
//    }

    // similar to addLevel but now using 2d array for the params
    public void addLevel(String levelName, int[][] levelLayout) {
        currentLevel = new Level(levelName, levelLayout);
        levels.add(currentLevel);
    }

    public String getCurrentLevelName() {
        return currentLevel.getLevelName();
    }

    // Get current level height
    public int getLevelHeight() {
        isCurrentLevel();
        return currentLevel.getHeight();
    }

//     Get current level width
    public int getLevelWidth() {
        isCurrentLevel();
        return currentLevel.getWidth();
    }



    // Returns total number of levels
    public int getLevelCount() {
        return levels.size();
    }

    // Set current level to the level at the specified index in the levels list
    public void setLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            throw new IllegalArgumentException("Invalid level index");
        }
        currentLevel = levels.get(index);
    }
    
    // Eyeball methods
    
    // Adds eyeball to current level at specified position and face direction
    public void addEyeball(int row, int column, Direction direction) {
        isCurrentLevel();
        checkEyeballPosition(row, column);
        eyeball = new Eyeball(row, column, direction);
    }

    // Get eyeball row position
    public int getEyeballRow() {
        findEyeball();
        return eyeball.getRow();
    }

    // Get eyeball column position
    public int getEyeballColumn() {
        findEyeball();
        return eyeball.getColumn();
    }

    // Get eyeball direction
    public Direction getEyeballDirection() {
        findEyeball();
        return eyeball.getDirection();
    }

    // Square methods
    
    // Adds a square to current level at given position
    public void addSquare(Square square, int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        currentLevel.addSquare(square, row, column);
    }
    
    // Return square object at given position in the current level
    public Square getSquareAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.getSquare(row, column);
    }

    public int getSquareAtIndex(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.getSquareAt(row, column);
    }

    // Return color of square at the given position in the current level
    public Color getColorAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.getSquare(row, column).getColor();
    }

    // Return shape of square at the given position in the current level
    public Shape getShapeAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.getSquare(row, column).getShape();
    }
    
    // Goal methods
    
    // Add goal to current level at given position
    public void addGoal(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        currentLevel.addGoal(row, column);
    }

    // Return total number of goals in the current level
    public int getGoalCount() {
        isCurrentLevel();
        return currentLevel.getGoalCount();
    }

    // Check if there is a goal at given position in current level
    public boolean hasGoalAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.hasGoalAt(row, column);
    }

    // Return number of completed goals in current level
    public int getCompletedGoalCount() {
        isCurrentLevel();
        return currentLevel.getCompletedGoalCount();
    }
    
    // Direction/Movement methods
    
    // Check if moving the eyeball to the specified position is valid based on direction
    public boolean isDirectionOK(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.isDirectionOK(row, column, eyeball);
    }

    // Return message if the move to given position is/or not valid based on direction
    public Message checkDirectionMessage(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.checkDirectionMessage(row, column, eyeball);
    }

    // Check for a blank-free path from the eyeball's current position to the specified position
    public boolean hasBlankFreePathTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.hasBlankFreePathTo(row, column, eyeball);
    }

    // Return a message indicating if a blank square is on the path to the specified position
    public Message checkMessageForBlankOnPathTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.checkMessageForBlankOnPathTo(row, column, eyeball);
    }

    // Move eyeball to the given position if the move is valid 
    public void moveTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        if (currentLevel.canMoveTo(row, column, eyeball, this)) {
            currentLevel.moveTo(row, column, eyeball);
            eyeball.setPosition(row, column);
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }
    
    // Check if eyeball can move to the specified position
    public boolean canMoveTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.canMoveTo(row, column, eyeball, this);
    }

    // Return a message indicating if the move to the specified position is/or not valid
    public Message MessageIfMovingTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.MessageIfMovingTo(row, column, eyeball, this);
    }

    // Validation/Checker methods
    
    // if no eyeball has been added to the game throw exception
    private void findEyeball() {
        if (eyeball == null) {
            throw new IllegalStateException("No eyeball added");
        }
    }

    // Check if the specified position is within the boundaries of the current level for adding/moving eyeball
    private void checkEyeballPosition(int row, int column) {
        if (row < 0 || row >= currentLevel.getHeight() || column < 0 || column >= currentLevel.getWidth()) {
            throw new IllegalArgumentException("Eyeball position is outside the level boundaries");
        }
    }

    // Check if the specified position is within the boundaries of the current level for adding/getting squares
    private void checkSquarePosition(int row, int column) {
        if (row < 0 || row >= currentLevel.getHeight() || column < 0 || column >= currentLevel.getWidth()) {
            throw new IllegalArgumentException("Square position is outside the level boundaries");
        }
    }

    // if no level has been added to the game throw exception
    private void isCurrentLevel() {
        if (currentLevel == null) {
            throw new IllegalStateException("No levels added");
        }
    }
}
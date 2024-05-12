package nz.ac.ara.adrianlim.eyeballmaze.models;

import java.util.ArrayList;
import java.util.List;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Color;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Shape;

public class Game {
    private List<Level> levels = new ArrayList<>();
    private Level currentLevel;
    private Eyeball eyeball;

    // created in portfolio.
    private int previousEyeballRow;
    private int previousEyeballColumn;

    // Level methods
    // Created in portoflio version, similar to addLevel but now using String level name & int 2d array for the params
    public void addLevel(String levelName, int[][] levelLayout) {
        currentLevel = new Level(levelName, levelLayout);
        levels.add(currentLevel);
    }

    public String getCurrentLevelName() {
        return currentLevel.getLevelName();
    }

    public int getLevelHeight() {
        isCurrentLevel();
        return currentLevel.getHeight();
    }

    public int getLevelWidth() {
        isCurrentLevel();
        return currentLevel.getWidth();
    }

    public int getLevelCount() {
        return levels.size();
    }

    public void setLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            throw new IllegalArgumentException("Invalid level index");
        }
        currentLevel = levels.get(index);
    }
    
    // Eyeball methods
    
    public void addEyeball(int row, int column, Direction direction) {
        isCurrentLevel();
        checkEyeballPosition(row, column);
        eyeball = new Eyeball(row, column, direction);
    }

    public int getEyeballRow() {
        findEyeball();
        return eyeball.getRow();
    }

    public int getEyeballColumn() {
        findEyeball();
        return eyeball.getColumn();
    }

    public Direction getEyeballDirection() {
        findEyeball();
        return eyeball.getDirection();
    }

    // Square methods
    
    public void addSquare(Square square, int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        currentLevel.addSquare(square, row, column);
    }
    
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

    public Color getColorAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.getSquare(row, column).getColor();
    }

    public Shape getShapeAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.getSquare(row, column).getShape();
    }
    
    // Goal methods
    
    public void addGoal(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        currentLevel.addGoal(row, column);
    }

    public int getGoalCount() {
        isCurrentLevel();
        return currentLevel.getGoalCount();
    }

    public boolean hasGoalAt(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.hasGoalAt(row, column);
    }

    public int getCompletedGoalCount() {
        isCurrentLevel();
        return currentLevel.getCompletedGoalCount();
    }
    
    // Direction/Movement methods
    
    public boolean isDirectionOK(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.isDirectionOK(row, column, eyeball);
    }

    public Message checkDirectionMessage(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.checkDirectionMessage(row, column, eyeball);
    }

    public boolean hasBlankFreePathTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.hasBlankFreePathTo(row, column, eyeball);
    }

    public Message checkMessageForBlankOnPathTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.checkMessageForBlankOnPathTo(row, column, eyeball);
    }

    public void moveTo(int row, int column) {

        isCurrentLevel();
        checkSquarePosition(row, column);
        if (currentLevel.canMoveTo(row, column, eyeball, this)) {
            // Created in portoflio version
            // Store previous eyeball position before moving
            previousEyeballRow = eyeball.getRow();
            previousEyeballColumn = eyeball.getColumn();

            currentLevel.moveTo(row, column, eyeball);
            eyeball.setPosition(row, column);
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }
    
    public boolean canMoveTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.canMoveTo(row, column, eyeball, this);
    }

    public Message MessageIfMovingTo(int row, int column) {
        isCurrentLevel();
        checkSquarePosition(row, column);
        return currentLevel.MessageIfMovingTo(row, column, eyeball, this);
    }

    // Validation/Checker methods
    
    private void findEyeball() {
        if (eyeball == null) {
            throw new IllegalStateException("No eyeball added");
        }
    }

    private void checkEyeballPosition(int row, int column) {
        if (row < 0 || row >= currentLevel.getHeight() || column < 0 || column >= currentLevel.getWidth()) {
            throw new IllegalArgumentException("Eyeball position is outside the level boundaries");
        }
    }

    private void checkSquarePosition(int row, int column) {
        if (row < 0 || row >= currentLevel.getHeight() || column < 0 || column >= currentLevel.getWidth()) {
            throw new IllegalArgumentException("Square position is outside the level boundaries");
        }
    }

    private void isCurrentLevel() {
        if (currentLevel == null) {
            throw new IllegalStateException("No levels added");
        }
    }


    // Created in portoflio version
    // checks for legal moves using the canMoveTo method from Ass2
    public boolean hasLegalMoves() {
        int currentRow = eyeball.getRow();
        int currentColumn = eyeball.getColumn();

        // Check all squares in level range
        for (int row = 0; row < getLevelHeight(); row++) {
            for (int col = 0; col < getLevelWidth(); col++) {
                // Skip checking the eyeball's current position as a potential legal move
                if (row == currentRow && col == currentColumn) {
                    continue;
                }
                // Check if move is legal
                if (canMoveTo(row, col)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void undoLastMove() {
        if (previousEyeballRow != -1 && previousEyeballColumn != -1) {
            // Revert the eyeball's position to the previous position
            eyeball.setPosition(previousEyeballRow, previousEyeballColumn);

            // Revert the level's state to the previous state
            currentLevel.revertMove(previousEyeballRow, previousEyeballColumn, eyeball);

            // Reset the previous eyeball position
            previousEyeballRow = -1;
            previousEyeballColumn = -1;
        }
    }

}
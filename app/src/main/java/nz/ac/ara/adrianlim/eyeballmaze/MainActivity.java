package nz.ac.ara.adrianlim.eyeballmaze;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;
import nz.ac.ara.adrianlim.eyeballmaze.models.Game;
import nz.ac.ara.adrianlim.eyeballmaze.models.Level;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private Level level;
    private TextView levelNameTextView;
    private TextView dialogTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogTextView = findViewById(R.id.text_dialog);
        dialogTextView.setText("Select a tile to make a move");

        levelNameTextView = findViewById(R.id.text_maze_level);

        int[][] levelLayout = {
                {0, 0, 11, 0},
                {1, 12, 8, 2},
                {10, 15, 14, 8},
                {11, 9, 15, 10},
                {13, 7, 9, 5},
                {0, 5, 0, 0}
        };

        game = new Game();
        game.addLevel("Level 1",levelLayout);
        game.addGoal(0, 2);
        game.addEyeball(5, 1, Direction.UP);

        GameGridAdapter gameGridAdapter = new GameGridAdapter(this, game);
        GridView gridView = findViewById(R.id.grid_game_level);
        gridView.setAdapter(gameGridAdapter);

        updateLevelName();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int tappedRow = position / game.getLevelWidth();
                int tappedCol = position % game.getLevelWidth();

                if (game.canMoveTo(tappedRow, tappedCol)) {
                    game.moveTo(tappedRow, tappedCol);

                    // notifyDataSetChanged ()
                    // Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself.
                    // Refresh the grid
                    gameGridAdapter.notifyDataSetChanged();

                    Log.d("EyeballMaze", "Game Goal count: " + game.getGoalCount());

                    Log.d("EyeballMaze", "Completed Goal Count: " + game.getCompletedGoalCount());

                    if (game.getGoalCount() == 0) {
                        showGameOverDialog(true);
                    }
//                    else {
//                        checkLegalMoves();
//                    }
                } else {
                    Message message = game.MessageIfMovingTo(tappedRow, tappedCol);
                    showInvalidMoveMessage(message);
                }
            }
        });
    }

    // Game related checks
//    private void checkLegalMoves() {
//        boolean hasValidMove = false;
//        int currentRow = game.getEyeballRow();
//        int currentColumn = game.getEyeballColumn();
//
//        // Check all cells in the grid
//        for (int row = 0; row < game.getLevelHeight(); row++) {
//            for (int column = 0; column < game.getLevelWidth(); column++) {
////                // Skip current position of eyeball
////                if (row == currentRow && column == currentColumn) {
////                    continue;
////                }
//
//                // Check if the move is valid according to the game rules
//                if (game.getCurrentLevel().isDirectionOK(row, column, game.getEyeball()) &&
//                        game.getCurrentLevel().hasBlankFreePathTo(row, column, game.getEyeball()) &&
//                        game.getCurrentLevel().canMoveTo(row, column, game.getEyeball(), game)) {
//                    hasValidMove = true;
//                    break;
//                }
//            }
//            if (hasValidMove) {
//                break;
//            }
//        }
//
//        if (!hasValidMove) {
//            showGameOverDialog(false);
//        }
//    }

    private void updateLevelName() {
        String levelName = game.getCurrentLevelName();
        levelNameTextView.setText(levelName);
    }

    private void showGameOverDialog(boolean isWin) {
        String title = isWin ? "Congratulations!" : "Game Over";
        String message = isWin ? "You have completed the level!" : "You lost as there are no legal moves to make.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ok
                    }
                })
                .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // restart game
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showInvalidMoveMessage(Message message) {
        String messageText = "";
        switch (message) {
            case MOVING_DIAGONALLY:
                messageText = "Cannot move diagonally";
                break;
            case BACKWARDS_MOVE:
                messageText = "Cannot move backwards";
                break;
            case MOVING_OVER_BLANK:
                messageText = "Cannot move over blank squares";
                break;
            case DIFFERENT_SHAPE_OR_COLOR:
                messageText = "Can only move to a square with the same color or shape";
                break;
        }
        dialogTextView.setText(messageText);
    }

}
package nz.ac.ara.adrianlim.eyeballmaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;
import nz.ac.ara.adrianlim.eyeballmaze.models.Game;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private TextView levelNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                    if (game.getCompletedGoalCount() == game.getGoalCount()) {
                        showGameWonMessage();
                    }
                } else {
                    Message message = game.MessageIfMovingTo(tappedRow, tappedCol);
                    Toast.makeText(MainActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateLevelName() {
        String levelName = game.getCurrentLevelName();
        levelNameTextView.setText(levelName);
    }

    private void showGameWonMessage() {
        // Display a congratulatory message or handle game completion
        // ...
    }

}
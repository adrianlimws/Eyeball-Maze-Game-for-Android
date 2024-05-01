package nz.ac.ara.adrianlim.eyeballmaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

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
        game.loadLevel("Level 1",levelLayout);

        GameGridAdapter gameGridAdapter = new GameGridAdapter(this, game);
        GridView gridView = findViewById(R.id.grid_game_level);
        gridView.setAdapter(gameGridAdapter);

        updateLevelName();
    }

    private void updateLevelName() {
        String levelName = game.getCurrentLevelName();
        levelNameTextView.setText(levelName);
    }

}
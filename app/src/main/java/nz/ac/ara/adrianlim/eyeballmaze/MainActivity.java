package nz.ac.ara.adrianlim.eyeballmaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import nz.ac.ara.adrianlim.eyeballmaze.models.Game;

public class MainActivity extends AppCompatActivity {
    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[][] levelLayout = {
                {0, 0, 11, 0},
                {1, 12, 8, 2},
                {10, 15, 14, 8},
                {11, 9, 15, 10},
                {13, 7, 9, 5},
                {0, 5, 0, 0}
        };

        game = new Game();
        game.loadLevel(levelLayout);

        GameGridAdapter gameGridAdapter = new GameGridAdapter(this, game);
        GridView gridView = findViewById(R.id.grid_game_level);
        gridView.setAdapter(gameGridAdapter);
    }

}
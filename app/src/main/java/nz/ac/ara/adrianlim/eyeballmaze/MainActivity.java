package nz.ac.ara.adrianlim.eyeballmaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.enums.Message;
import nz.ac.ara.adrianlim.eyeballmaze.models.Game;

public class MainActivity extends AppCompatActivity {
    private static final String VIDEO_PATH = "android.resource://%s/" + R.raw.rules_video;
    private Game game;
    private GridView gridView;
    private TextView levelNameTextView;
    private TextView dialogTextView;
    private TextView moveCountTextView;
    private TextView goalCountTextView;
    private int moveCount = 0;
    private int initialGoalCount;
    private MediaPlayer legalMoveSound;
    private MediaPlayer illegalMoveSound;
    private MediaPlayer goalReachedSound;
    private MediaPlayer gameOverSound;
    private boolean isSoundOn = true;
    private boolean isUndoUsed = false;
    private long startTime;
    private TextView elapsedTimeTextView;
    private String finalElapsedTime;
    private boolean isGameOver = false;
    private boolean isPaused = false;
    private long pauseTime = 0;
    private Handler handler = new Handler();
    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateElapsedTime();
            handler.postDelayed(this, 1000); // Update every second
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createViews();
        createSoundEffects();
        createGame();
        setupGridViewListener();
        setupBottomNavigation();
    }

    private void createViews() {
        gridView = findViewById(R.id.grid_game_level);
        levelNameTextView = findViewById(R.id.text_maze_level);
        elapsedTimeTextView = findViewById(R.id.text_elapsed_time);
        moveCountTextView = findViewById(R.id.text_move_count);
        goalCountTextView = findViewById(R.id.text_goal_count);
        dialogTextView = findViewById(R.id.text_rule_dialog);
        dialogTextView.setText(getString(R.string.select_a_tile_to_make_a_move));
    }

    private void createSoundEffects() {
        legalMoveSound = MediaPlayer.create(this, R.raw.legal_move_sound);
        illegalMoveSound = MediaPlayer.create(this, R.raw.illegal_move_sound);
        goalReachedSound = MediaPlayer.create(this, R.raw.goal_reached_sound);
        gameOverSound = MediaPlayer.create(this, R.raw.game_over_sound);
    }

    private void createGame() {
        int[][] gameLevelLayout = {
                {0, 0, 11, 0},
                {1, 12, 8, 2},
                {10, 15, 14, 8},
                {11, 9, 15, 10},
                {13, 7, 9, 5},
                {0, 5, 0, 6}
        };

        game = new Game();
        game.addLevel("Level 1", gameLevelLayout);
        game.addGoal(0, 2);
        game.addEyeball(5, 1, Direction.UP);

        initialGoalCount = game.getGoalCount();
        goalCountTextView.setText(getString(R.string.goal_0, initialGoalCount));

        GameGridAdapter gameGridAdapter = new GameGridAdapter(this, game);
        gridView.setAdapter(gameGridAdapter);

        updateLevelName();
        startTime = System.currentTimeMillis();
    }

    private void setupGridViewListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleGridItemClick(position);
            }
        });
    }

    private void handleGridItemClick(int position) {
        int tappedRow = position / game.getLevelWidth();
        int tappedCol = position % game.getLevelWidth();

        if (tappedRow == game.getEyeballRow() && tappedCol == game.getEyeballColumn()) {
            dialogTextView.setText(R.string.you_are_already_here);
            playSoundEffect(illegalMoveSound);
            return;
        }

        if (game.canMoveTo(tappedRow, tappedCol)) {
            game.moveTo(tappedRow, tappedCol);
            playSoundEffect(legalMoveSound);
            moveCount++;
            moveCountTextView.setText(getString(R.string.moves, moveCount));

            GameGridAdapter gameGridAdapter = (GameGridAdapter) gridView.getAdapter();
            gameGridAdapter.notifyDataSetChanged();

            if (game.getGoalCount() == 0) {
                showGameOverDialog(true);
                playSoundEffect(goalReachedSound);
            } else if (!game.hasLegalMoves()) {
                showGameOverDialog(false);
                playSoundEffect(gameOverSound);
            }

            goalCountTextView.setText(String.format(Locale.US, "%s%d/%d", getString(R.string.goal), game.getCompletedGoalCount(), initialGoalCount));
        } else {
            playSoundEffect(illegalMoveSound);
            Message message = game.MessageIfMovingTo(tappedRow, tappedCol);
            showInvalidMoveMessage(message);
        }
    }

    private void playSoundEffect(MediaPlayer soundEffect) {
        if (isSoundOn) {
            soundEffect.start();
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_sound) {
                    toggleSound(item);
                    return true;
                } else if (itemId == R.id.action_undo) {
                    handleUndo();
                    return true;
                } else if (itemId == R.id.action_pause) {
                    handlePause(item);
                    return true;
                } else if (itemId == R.id.action_rules) {
                    showRulesVideoDialog();
                    return true;
                }
                return false;
            }
        });
    }

    private void toggleSound(MenuItem item) {
        isSoundOn = !isSoundOn;

        if (isSoundOn) {
            item.setIcon(R.drawable.icon_sound_on);
        } else {
            item.setIcon(R.drawable.icon_sound_off);
        }
    }

    private void handleUndo() {
        if (!isUndoUsed && moveCount > 0) {
            game.undoLastMove();
            GameGridAdapter gameGridAdapter = (GameGridAdapter) gridView.getAdapter();
            gameGridAdapter.notifyDataSetChanged();
            isUndoUsed = true;
            moveCount--;
            moveCountTextView.setText(getString(R.string.moves, moveCount));

        } else if (moveCount == 0) {
            showNoMoveDialog();
        } else {
            showUndoUsedDialog();
        }
    }

    private void showNoMoveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("No Move Made")
                .setMessage("You haven't made any moves yet.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showUndoUsedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Undo Used")
                .setMessage("Undo has already been used for this level. You cannot use it again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handlePause(MenuItem item) {
            if (isPaused) {
                isPaused = false;
                gridView.setVisibility(View.VISIBLE);
                startTime += System.currentTimeMillis() - pauseTime;
                handler.postDelayed(updateTimeRunnable, 1000);
                item.setIcon(R.drawable.icon_pause);
            } else {
                isPaused = true;
                gridView.setVisibility(View.INVISIBLE);
                pauseTime = System.currentTimeMillis();
                handler.removeCallbacks(updateTimeRunnable);
                item.setIcon(R.drawable.icon_pause);
                showPauseDialog();
            }
    }


    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(updateTimeRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTimeRunnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        legalMoveSound.release();
        illegalMoveSound.release();
        goalReachedSound.release();
        gameOverSound.release();
    }

    private void updateLevelName() {
        String levelName = game.getCurrentLevelName();
        levelNameTextView.setText(levelName);
    }

    private void showRulesVideoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_rules_video, null);
        builder.setView(view);

        VideoView videoView = view.findViewById(R.id.videoView);
        ImageButton closeButton = view.findViewById(R.id.closeButton);

        String videoPath = String.format(VIDEO_PATH, getPackageName());
        videoView.setVideoPath(videoPath);

        final AlertDialog dialog = builder.create();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                videoView.stopPlayback();
            }
        });

        dialog.show();
        videoView.start();
    }

    private void showGameOverDialog(boolean isWin) {
        isGameOver = true;
        handler.removeCallbacks(updateTimeRunnable);

        String title = isWin ? "Congratulations!" : "Game Over";
        String message;

        if (isWin) {
            message = "You have completed the level in " + finalElapsedTime + "!";
        } else {
            message = "You lost as there are no legal moves to make.";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Quit Game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showConfirmQuitDialog();
                    }
                })
                .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartLevel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restartLevel() {
        isUndoUsed = false;
        isGameOver = false;
        recreate();
    }

    private void showConfirmQuitDialog() {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(MainActivity.this);
        confirmBuilder.setTitle("Confirm Quit")
                .setMessage("Are you sure you want to quit the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface confirmDialog, int confirmId) {
                        finish();
                    }
                })
                .setNegativeButton("Restart Level", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface confirmDialog, int confirmId) {
                        isUndoUsed = false;
                        isGameOver = false;
                        recreate();
                    }
                })
                .show();
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
                messageText = "Can only move to same color or shape";
                break;
        }
        dialogTextView.setText(messageText);
    }

    private void updateElapsedTime() {
        if (!isGameOver) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime;

            if (isPaused) {
                elapsedTime = pauseTime - startTime;
            } else {
                elapsedTime = currentTime - startTime;
            }

            String formattedTime = String.format(Locale.US, "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                    TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60);

            finalElapsedTime = formattedTime;

            elapsedTimeTextView.setText(getString(R.string.elapsed_time_text, formattedTime));
        }
    }

    private void showPauseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Paused")
                .setMessage("Do you want to continue the game?")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isPaused = false;
                        gridView.setVisibility(View.VISIBLE);
                        startTime += System.currentTimeMillis() - pauseTime;
                        handler.postDelayed(updateTimeRunnable, 1000);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
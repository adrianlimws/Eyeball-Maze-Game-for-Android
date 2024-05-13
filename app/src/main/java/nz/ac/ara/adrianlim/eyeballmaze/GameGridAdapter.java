package nz.ac.ara.adrianlim.eyeballmaze;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import nz.ac.ara.adrianlim.eyeballmaze.enums.Direction;
import nz.ac.ara.adrianlim.eyeballmaze.models.Game;

public class GameGridAdapter extends BaseAdapter {

    private Context context;
    private Game game;
    private int cellWidth;
    private int cellHeight;

    // Constructor takes context and game instance
    public GameGridAdapter(Context context, Game game) {
        this.context = context;
        this.game = game;

        // Get cell dimensions from resources
        Resources resources = context.getResources();
        if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            cellWidth = resources.getDimensionPixelSize(R.dimen.cell_width_landscape);
            cellHeight = resources.getDimensionPixelSize(R.dimen.cell_height_landscape);
        } else {
            cellWidth = resources.getDimensionPixelSize(R.dimen.cell_width);
            cellHeight = resources.getDimensionPixelSize(R.dimen.cell_height);
        }
    }

    // Returns total number of cells in the grid
    @Override
    public int getCount() {
        return game.getLevelHeight() * game.getLevelWidth();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    // Returns the position as item ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Creates and return the view of individual cell in the grid
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout frameLayout;
        if (convertView == null) {
            // If convertView is null, create a new FrameLayout and set layout parameters
            frameLayout = new FrameLayout(context);
            frameLayout.setLayoutParams(new GridView.LayoutParams(cellWidth, cellHeight));
        } else {
            // If convertView is not null, reuse and remove existing views
            frameLayout = (FrameLayout) convertView;
            frameLayout.removeAllViews();
        }

        // Create an ImageView for the shape
        ImageView shapeImageView = new ImageView(context);
        shapeImageView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        shapeImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        // Calculate the row and column indices based on the position
        int row = position / game.getLevelWidth();
        int col = position % game.getLevelWidth();
        int squareValue = game.getSquareAtIndex(row, col);

        // Set the appropriate image resource for the shape based on each square value
        switch (squareValue) {
            case 1:
                shapeImageView .setImageResource(R.drawable.cross_blue);
                break;
            case 2:
                shapeImageView .setImageResource(R.drawable.cross_green);
                break;
            case 3:
                shapeImageView .setImageResource(R.drawable.cross_red);
                break;
            case 4:
                shapeImageView.setImageResource(R.drawable.cross_yellow);
                break;
            case 5:
                shapeImageView.setImageResource(R.drawable.diamond_blue);
                break;
            case 6:
                shapeImageView.setImageResource(R.drawable.diamond_green);
                break;
            case 7:
                shapeImageView.setImageResource(R.drawable.diamond_red);
                break;
            case 8:
                shapeImageView.setImageResource(R.drawable.diamond_yellow);
                break;
            case 9:
                shapeImageView.setImageResource(R.drawable.flower_blue);
                break;
            case 10:
                shapeImageView.setImageResource(R.drawable.flower_green);
                break;
            case 11:
                shapeImageView.setImageResource(R.drawable.flower_red);
                break;
            case 12:
                shapeImageView.setImageResource(R.drawable.flower_yellow);
                break;
            case 13:
                shapeImageView.setImageResource(R.drawable.star_blue);
                break;
            case 14:
                shapeImageView.setImageResource(R.drawable.star_green);
                break;
            case 15:
                shapeImageView.setImageResource(R.drawable.star_red);
                break;
            case 16:
                shapeImageView.setImageResource(R.drawable.star_yellow);
                break;
            default:
                shapeImageView.setImageResource(android.R.color.transparent);
                break;
        }
        frameLayout.addView(shapeImageView);

        // If cell has goal, add a goal ImageView to the FrameLayout
        if (game.hasGoalAt(row, col)) {
            ImageView goalImageView = new ImageView(context);
            goalImageView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            goalImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            goalImageView.setImageResource(R.drawable.goal);
            frameLayout.addView(goalImageView);
        }

        // If cell has eyeball, add an eyeball ImageView to the FrameLayout
        if (row == game.getEyeballRow() && col == game.getEyeballColumn()) {
            ImageView eyeballImageView = new ImageView(context);
            eyeballImageView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            eyeballImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            // Set eyeball image based on the direction
            Direction eyeballDirection = game.getEyeballDirection();
            switch (eyeballDirection) {
                case UP:
                    eyeballImageView.setImageResource(R.drawable.player_eyes_up);
                    break;
                case DOWN:
                    eyeballImageView.setImageResource(R.drawable.player_eyes_down);
                    break;
                case LEFT:
                    eyeballImageView.setImageResource(R.drawable.player_eyes_left);
                    break;
                case RIGHT:
                    eyeballImageView.setImageResource(R.drawable.player_eyes_right);
                    break;
            }

            frameLayout.addView(eyeballImageView);
        }

        return frameLayout;
    }
}

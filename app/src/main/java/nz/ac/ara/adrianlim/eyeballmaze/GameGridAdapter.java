package nz.ac.ara.adrianlim.eyeballmaze;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import nz.ac.ara.adrianlim.eyeballmaze.models.Game;

public class GameGridAdapter extends BaseAdapter {

    private Context context;
    private Game game;
    private int cellWidth;
    private int cellHeight;

    public GameGridAdapter(Context context, Game game) {
        this.context = context;
        this.game = game;

        // Calculate cell dimensions based on screen width and number of columns
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int numColumns = game.getLevelWidth();
        cellWidth = screenWidth / numColumns;
        cellHeight = cellWidth; // treated as square cells
    }

    @Override
    public int getCount() {
        return game.getLevelHeight() * game.getLevelWidth();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout frameLayout;
        if (convertView == null) {
            frameLayout = new FrameLayout(context);
            frameLayout.setLayoutParams(new GridView.LayoutParams(cellWidth, cellHeight));
        } else {
            frameLayout = (FrameLayout) convertView;
        }

        ImageView shapeImageView = new ImageView(context);
        shapeImageView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        shapeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        int row = position / game.getLevelWidth();
        int col = position % game.getLevelWidth();
        int squareValue = game.getSquareAt(row, col);


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
            case 0:
                shapeImageView.setImageResource(android.R.color.transparent);
            default:
                shapeImageView.setImageResource(android.R.color.transparent);
                break;
        }
        frameLayout.addView(shapeImageView);

        if (game.hasGoalAt(row, col)) {
            ImageView goalImageView = new ImageView(context);
            goalImageView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            goalImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            goalImageView.setImageResource(R.drawable.goal);
            frameLayout.addView(goalImageView);
        }

        return frameLayout;
    }
}

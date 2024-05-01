package nz.ac.ara.adrianlim.eyeballmaze;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GameGridAdapter extends BaseAdapter {

    private Context context;
    private int[][] levelLayout;
    private int cellWidth;
    private int cellHeight;

    public GameGridAdapter(Context context, int[][] levelLayout) {
        this.context = context;
        this.levelLayout = levelLayout;

        // Calculate cell dimensions based on screen width and number of columns
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int numColumns = levelLayout[0].length;
        cellWidth = screenWidth / numColumns;
        cellHeight = cellWidth; // Assuming square cells
    }

    @Override
    public int getCount() {
        return levelLayout.length * levelLayout[0].length;
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
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        int row = position / levelLayout[0].length;
        int col = position % levelLayout[0].length;
        int shapeId = levelLayout[row][col];

        switch (shapeId) {
            case 1:
                imageView.setImageResource(R.drawable.cross_blue);
                break;
            case 2:
                imageView.setImageResource(R.drawable.cross_green);
                break;
            case 3:
                imageView.setImageResource(R.drawable.cross_red);
                break;
            case 4:
                imageView.setImageResource(R.drawable.cross_yellow);
                break;
            case 5:
                imageView.setImageResource(R.drawable.diamond_blue);
                break;
            case 6:
                imageView.setImageResource(R.drawable.diamond_green);
                break;
            case 7:
                imageView.setImageResource(R.drawable.diamond_red);
                break;
            case 8:
                imageView.setImageResource(R.drawable.diamond_yellow);
                break;
            case 9:
                imageView.setImageResource(R.drawable.flower_blue);
                break;
            case 10:
                imageView.setImageResource(R.drawable.flower_green);
                break;
            case 11:
                imageView.setImageResource(R.drawable.flower_red);
                break;
            case 12:
                imageView.setImageResource(R.drawable.flower_yellow);
                break;
            case 13:
                imageView.setImageResource(R.drawable.star_blue);
                break;
            case 14:
                imageView.setImageResource(R.drawable.star_green);
                break;
            case 15:
                imageView.setImageResource(R.drawable.star_red);
                break;
            case 16:
                imageView.setImageResource(R.drawable.star_yellow);
                break;
            case 0:
                imageView.setImageResource(android.R.color.transparent);
            default:
                imageView.setImageResource(android.R.color.transparent);
                break;
        }

        return imageView;
    }
}

package yue.kamcordproject;

/**
 * Created by yue on 3/7/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.graphics.Bitmap;

public class WatchArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> titles;
    private final ArrayList<Bitmap> videos;

    public WatchArrayAdapter(Context context, ArrayList<String> titles, ArrayList<Bitmap> videos) {
        super(context, R.layout.watch_view, titles);
        this.context = context;
        this.titles = titles;
        this.videos = videos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.watch_view, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.Text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.Image);
        textView.setText(titles.get(position));
        imageView.setImageBitmap(videos.get(position));
        return rowView;
    }
}

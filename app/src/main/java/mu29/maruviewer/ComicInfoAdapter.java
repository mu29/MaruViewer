package mu29.maruviewer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by InJung on 2015. 7. 9..
 */
public class ComicInfoAdapter extends ArrayAdapter<ComicInfo> {

    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList<ComicInfo> mComicData;

    public ComicInfoAdapter(Context context, int layoutResourceId, ArrayList<ComicInfo> comicData) {
        super(context, layoutResourceId, comicData);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mComicData = comicData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        TextView title = (TextView) row.findViewById(R.id.comic_info_title);
        TextView date = (TextView) row.findViewById(R.id.comic_info_date);

        title.setText(mComicData.get(position).getTitle());
        date.setText(mComicData.get(position).getDate());

        ImageView image = (ImageView) row.findViewById(R.id.comic_info_image);

        return row;
    }
}

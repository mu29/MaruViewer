package mu29.maruviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        switch (mLayoutResourceId) {
            case R.layout.comic_info:
                TextView comic_title = (TextView) row.findViewById(R.id.comic_info_title);
                comic_title.setText(mComicData.get(position).getTitle());

                ImageView image = (ImageView) row.findViewById(R.id.comic_info_image);
                String imagePath = mContext.getFilesDir().getPath() + "/" + mComicData.get(position).getTitle();
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                    image.setImageBitmap(bitmap);
                } else {
                    FileDownloader downloader = new FileDownloader(mContext, this);
                    downloader.startDownload(mComicData.get(position).getImageSrc(), mComicData.get(position).getTitle());
                }
                break;
            case R.layout.update_info:
                TextView update_title = (TextView) row.findViewById(R.id.update_info_title);
                TextView update_date = (TextView) row.findViewById(R.id.update_info_date);
                TextView update_latest = (TextView) row.findViewById(R.id.update_info_latest);

                update_title.setText(mComicData.get(position).getTitle());
                update_date.setText(mComicData.get(position).getDate());
                update_latest.setText(mComicData.get(position).getLatest());
                break;
        }

        return row;
    }


}

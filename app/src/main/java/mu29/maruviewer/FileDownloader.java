package mu29.maruviewer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;

/**
 * Created by InJung on 2015. 7. 9..
 */
public class FileDownloader {
    private Context context;
    private ComicInfoAdapter adapter;

    public FileDownloader(Context context) {
        this.context = context;
    }

    public FileDownloader(Context context, ComicInfoAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();
    public void startDownload(String fileUrl, String fileName) {
        final File filePath  = new File(context.getFilesDir().getPath() + "/" + fileName);

        if (!filePath.exists()) {
            client.get(fileUrl, new FileAsyncHttpResponseHandler(context) {
                @Override
                public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                    Log.e("ee", i + "");
                }

                @Override
                public void onSuccess(int i, Header[] headers, File file) {
                    file.renameTo(filePath);
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                }
            });
        }
    }
}

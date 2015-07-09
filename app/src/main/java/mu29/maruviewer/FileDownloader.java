package mu29.maruviewer;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;

/**
 * Created by InJung on 2015. 7. 9..
 */
public class FileDownloader {
    private Context mContext;

    public FileDownloader(Context context) {
        mContext = context;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();
    public void startDownload(String fileUrl, String fileName) {
        final File filePath  = new File(mContext.getFilesDir().getPath() + "/" + fileName);

        if (!filePath.exists()) {
            client.get(fileUrl, new FileAsyncHttpResponseHandler(mContext) {
                @Override
                public void onFailure(int i, Header[] headers, Throwable throwable, File file) {

                }

                @Override
                public void onSuccess(int i, Header[] headers, File file) {
                    file.renameTo(filePath);
                }
            });
        }
    }
}

package mu29.maruviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class UpdateFragment extends Fragment {
    private Context context;
    private ArrayList<ComicInfo> comicInfoList = new ArrayList<>();
    private View rootView;
    private ProgressDialog progressDialog;

    public static UpdateFragment newInstance(Context context) {
        UpdateFragment fragment = new UpdateFragment();
        fragment.context = context;
        return fragment;
    }

    public UpdateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update, container, false);
        return rootView;
    }

    public void refresh() {
        final AsyncTask getUpdates = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Crawler crawler = new Crawler(context);
                comicInfoList.clear();
                comicInfoList = crawler.getUpdateList(1);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(context, "", "로딩 중입니다..", true);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ListView listView = (ListView) rootView.findViewById(R.id.main_update_list);
                ComicInfoAdapter adapter = new ComicInfoAdapter(context, R.layout.update_info, comicInfoList);
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        };
        getUpdates.execute();
    }
}

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

public class MainFragment extends Fragment {
    private Context context;
    private ArrayList<ComicInfo> comicInfoList = new ArrayList<>();
    private View rootView;
    private ProgressDialog progressDialog;

    public static MainFragment newInstance(Context context) {
        MainFragment fragment = new MainFragment();
        fragment.context = context;
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        refresh();

        return rootView;
    }

    private void refresh() {
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
                ComicInfoAdapter adapter = new ComicInfoAdapter(context, R.layout.comic_info, comicInfoList);
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        };

        AsyncTask makeDataBase = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Crawler crawler = new Crawler(context, progressDialog);
                crawler.makeDataBase();
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("데이터베이스 생성 중입니다..");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                progressDialog.dismiss();
                getUpdates.execute();
            }
        };
        makeDataBase.execute();
    }
}

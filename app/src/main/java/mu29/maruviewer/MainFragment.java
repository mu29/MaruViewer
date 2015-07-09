package mu29.maruviewer;

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
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        AsyncTask getUpdates = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Crawler crawler = new Crawler();
                comicInfoList.clear();
                comicInfoList = crawler.getUpdateList(1);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ListView listView = (ListView) rootView.findViewById(R.id.main_update_list);
                ComicInfoAdapter adapter = new ComicInfoAdapter(context, R.layout.comic_info, comicInfoList);
                listView.setAdapter(adapter);
            }
        };
        getUpdates.execute();

        return rootView;
    }
}

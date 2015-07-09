package mu29.maruviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ArrayList<ComicInfo> comicInfoList = new ArrayList<>();
    private ComicInfoAdapter comicInfoAdapter;
    private int currentPage = 1;
    private View rootView;
    private ProgressDialog progressDialog;

    public static ListFragment newInstance(Context context) {
        ListFragment fragment = new ListFragment();
        fragment.context = context;
        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);

        Button btnPrevious = (Button) rootView.findViewById(R.id.list_button_previous);
        Button btnNext = (Button) rootView.findViewById(R.id.list_button_next);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        refresh();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.list_button_previous:
                if (currentPage > 1) {
                    currentPage -= 1;
                    progressDialog = ProgressDialog.show(context, "", "로딩 중입니다..", true);
                    refresh();
                } else {
                    Toast.makeText(context, R.string.error_first_page, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.list_button_next:
                if (currentPage < 45) {
                    currentPage += 1;
                    progressDialog = ProgressDialog.show(context, "", "로딩 중입니다..", true);
                    refresh();
                } else {
                    Toast.makeText(context, R.string.error_last_page, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void refresh() {
        AsyncTask getLists = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Crawler crawler = new Crawler(context);
                comicInfoList.clear();
                comicInfoList = crawler.getComicList(currentPage);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ListView listView = (ListView) rootView.findViewById(R.id.main_update_list);
                comicInfoAdapter = new ComicInfoAdapter(context, R.layout.comic_info, comicInfoList);
                listView.setAdapter(comicInfoAdapter);

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        };
        getLists.execute();
    }
}

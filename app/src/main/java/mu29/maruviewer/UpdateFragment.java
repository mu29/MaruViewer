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

public class UpdateFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ArrayList<ComicInfo> comicInfoList = new ArrayList<>();
    private int currentPage = 1;
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

        Button btnPrevious = (Button) rootView.findViewById(R.id.update_button_previous);
        Button btnNext = (Button) rootView.findViewById(R.id.update_button_next);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.update_button_previous:
                if (currentPage > 1) {
                    currentPage -= 1;
                    refresh();
                } else {
                    Toast.makeText(context, R.string.error_first_page, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_button_next:
                if (currentPage < 500) {
                    currentPage += 1;
                    refresh();
                } else {
                    Toast.makeText(context, R.string.error_last_page, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void refresh() {
        final AsyncTask getUpdates = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Crawler crawler = new Crawler(context);
                comicInfoList.clear();
                comicInfoList = crawler.getUpdateList(currentPage);
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

package mu29.maruviewer;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class ComicListActivity extends ActionBarActivity {
    private String title;
    private ArrayList<ComicInfo> comicItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        title = getIntent().getExtras().getString("title", "목록");
        getSupportActionBar().setTitle(title);

        AsyncTask t = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Crawler crawler = new Crawler(ComicListActivity.this);
                comicItems = crawler.getItems(title);

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ListView listView = (ListView) ComicListActivity.this.findViewById(R.id.comic_item_list);
                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < comicItems.size(); i++)
                    items.add(comicItems.get(i).getTitle());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComicListActivity.this,
                                android.R.layout.simple_list_item_1, items);
                listView.setAdapter(adapter);
            }
        };
        t.execute();
    }

}

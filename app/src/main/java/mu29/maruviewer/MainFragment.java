package mu29.maruviewer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;

import org.jsoup.Jsoup; //import Jsoup
import org.jsoup.nodes.Document; //import Jsoup
import org.jsoup.select.Elements; //import Jsoup

public class MainFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        AsyncTask t = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                try {
                    Document doc = Jsoup.connect("http://marumaru.in/c/1/40").get(); //웹에서 내용을 가져온다.
                    Elements contents = doc.select("div.sbj"); //내용 중에서 원하는 부분을 가져온다.
                    String text = contents.text(); //원하는 부분은 Elements형태로 되어 있으므로 이를 String 형태로 바꾸어 준다.
                } catch (Exception e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
                    e.printStackTrace();
                }
                return null;
            }
        };
        t.execute();

        return rootView;
    }
}

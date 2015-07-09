package mu29.maruviewer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Crawler {

    private static Crawler instance;

    public static Crawler getInstance() {
        if (instance == null)
            instance = new Crawler();

        return instance;
    }

    public Crawler() {
    }

    public ArrayList<String> getUpdateList(int page) {
        ArrayList<String> titleList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(Statics.Url.UPDATE + page).get();
            Elements contents = doc.select("div.sbj");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return titleList;
    }

}

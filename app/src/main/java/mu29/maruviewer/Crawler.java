package mu29.maruviewer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Crawler {

    public Crawler() {
    }

    public ArrayList<ComicInfo> getUpdateList(int page) {
        ArrayList<ComicInfo> comicInfos = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(Statics.Url.UPDATE + page).get();
            Elements titles = doc.select("span.subject");
            Elements dates = doc.select("div.info");

            for (int i = 0; i < titles.size(); i++) {
                ComicInfo info = new ComicInfo(titles.get(i).text(), dates.get(i).text(), "");
                comicInfos.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comicInfos;
    }

}

package mu29.maruviewer;

import java.util.ArrayList;

/**
 * Created by InJung on 2015. 7. 8..
 */
public class Data {
    public static ArrayList<ComicInfo> comics = new ArrayList<>();
    public static int downloadStatus = 0;

    public static ComicInfo findComic(String title) {
        for (ComicInfo info : comics) {
            if (info.getTitle().equals(title))
                return info;
        }

        return null;
    }

    public static class Url {
        public static final String BASE = "http://marumaru.in";
        public static final String UPDATE = "http://marumaru.in/?c=26&p=";
        public static final String LIST = "http://marumaru.in/?c=40&p=";
    }
}

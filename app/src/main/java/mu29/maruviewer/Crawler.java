package mu29.maruviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class Crawler {
    private Context context;
    private ProgressDialog dialog;

    public Crawler(Context context) {
        this.context = context;
    }

    public Crawler(Context context, ProgressDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    public HashMap<String, String> getItems(String title) {
        HashMap<String, String> comicList = new HashMap<>();

        try {
            ComicInfo info = Data.findComic(title);
            if (info == null)
                return null;

            Document doc = Jsoup.connect(info.getLink()).get();
            Elements items = doc.select("a[target]");

            for (Element e : items) {
                String item = e.text();
                String link = e.attr("href");
                comicList.put(item, link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comicList;
    }

    public ArrayList<ComicInfo> getUpdateList(int page) {
        ArrayList<ComicInfo> comicInfos = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(Data.Url.UPDATE + page).get();
            Elements titles = doc.select("span.subject");
            Elements dates = doc.select("div.info");

            for (int i = 0; i < titles.size(); i++) {
                String fullTitle = titles.get(i + 1).text();
                String title = fullTitle.replaceAll(" [0-9~, ]+화.*", "");
                String latest = fullTitle.replace(title, "").replace(" ", "");

                ComicInfo info = Data.findComic(title);

                if (info == null)
                    continue;

                info.setDate(dates.get(i + 1).text());
                info.setLatest(latest);
                comicInfos.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comicInfos;
    }

    public ArrayList<ComicInfo> getComicList(int page) {
        ArrayList<ComicInfo> comicInfos = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(Data.Url.LIST + page).get();
            Elements titles = doc.select("span.subject");

            for (int i = 0; i < titles.size(); i++) {
                String title = titles.get(i).text();

                ComicInfo info = Data.findComic(title);
                if (info == null)
                    continue;
                comicInfos.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comicInfos;
    }

    public void makeDataBase() {
        try {
            SharedPreferences prefs = context.getSharedPreferences("comic", Context.MODE_PRIVATE);
            String titleData = prefs.getString("title", "");
            String imageData = prefs.getString("image", "");
            String linkData = prefs.getString("link", "");

            if (titleData.equals("")) {
                titleData = "";
                imageData = "";
                linkData = "";

                for (int page = 1; page <= 45; page++) {
                    Document doc = Jsoup.connect(Data.Url.LIST + page).get();
                    Elements titles = doc.select("span.subject");
                    Elements images = doc.select("div.image-thumb");
                    Elements links = doc.select("div.list");

                    for (int i = 0; i < titles.size(); i++) {
                        String title = titles.get(i).text();
                        String imageAttr = images.get(i).attr("style");
                        String image = imageAttr.contains("https") ? "https:" : "http:";
                        image += imageAttr.substring(imageAttr.indexOf("//"), imageAttr.indexOf(")"));
                        String linkAttr = links.get(i).attr("onclick");
                        String link = Data.Url.BASE + linkAttr.substring(linkAttr.indexOf("/?c="), linkAttr.indexOf("')"));

                        Data.downloadStatus++;
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setProgress(Data.downloadStatus * 100 / 1325);
                            }
                        });

                        ComicInfo info = new ComicInfo(title, image, link, "");
                        Data.comics.add(info);
                        titleData += title + "|";
                        imageData += image + "|";
                        linkData += link + "|";
                    }
                }

                SharedPreferences pref = context.getSharedPreferences("comic", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("title", titleData);
                editor.putString("image", imageData);
                editor.putString("link", linkData);
                editor.apply();
            } else {
                String[] titleList = titleData.split("\\|");
                String[] imageList = imageData.split("\\|");
                String[] linkList = linkData.split("\\|");

                for (int i = 0; i < titleList.length; i++) {
                    if (titleList[i].equals(""))
                        continue;

                    ComicInfo info = new ComicInfo(titleList[i], imageList[i], linkList[i], "");
                    Data.comics.add(info);

                    Data.downloadStatus++;
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setProgress(Data.downloadStatus * 100 / 1325);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

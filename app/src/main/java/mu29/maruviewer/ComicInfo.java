package mu29.maruviewer;

/**
 * Created by InJung on 2015. 7. 9..
 */
public class ComicInfo {
    private String title;
    private String date;
    private String imageSrc;
    private String link;

    public ComicInfo(String title, String date, String imageSrc, String link) {
        this.title = title;
        this.date = date;
        this.imageSrc = imageSrc;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getLink() {
        return link;
    }
}

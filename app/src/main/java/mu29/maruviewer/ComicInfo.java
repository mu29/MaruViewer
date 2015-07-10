package mu29.maruviewer;

/**
 * Created by InJung on 2015. 7. 9..
 */
public class ComicInfo {
    private String title;
    private String imageSrc;
    private String link;
    private String date;
    private String latest;

    public ComicInfo(String title, String imageSrc, String link, String date) {
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }
}

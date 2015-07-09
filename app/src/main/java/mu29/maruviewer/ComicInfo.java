package mu29.maruviewer;

/**
 * Created by InJung on 2015. 7. 9..
 */
public class ComicInfo {
    private String mTitle;
    private String mDate;
    private String mImageSrc;

    public ComicInfo(String title, String date, String imageSrc) {
        mTitle = title;
        mDate = date;
        mImageSrc = imageSrc;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getImageSrc() {
        return mImageSrc;
    }
}

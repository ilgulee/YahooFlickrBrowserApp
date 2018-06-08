package ilgulee.com.yahooflickrbrowserapp;

class Photo {
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mSmallImageURL;
    private String mTags;
    private String mLargeImageURL;
//    private String mDescription;
//    private String mDateTaken;
//    private String mDatePublished;


    public Photo(String title, String author, String authorId, String smallImageURL, String tags, String largeImageURL) {
        mTitle = title;
        mAuthor = author;
        mAuthorId = authorId;
        mSmallImageURL = smallImageURL;
        mTags = tags;
        mLargeImageURL = largeImageURL;
    }

    String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    String getAuthor() {
        return mAuthor;
    }

    void setAuthor(String author) {
        mAuthor = author;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    void setAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    String getSmallImageURL() {
        return mSmallImageURL;
    }

    void setSmallImageURL(String smallImageURL) {
        mSmallImageURL = smallImageURL;
    }

    String getTags() {
        return mTags;
    }

    void setTags(String tags) {
        mTags = tags;
    }

    String getLargeImageURL() {
        return mLargeImageURL;
    }

    void setLargeImageURL(String largeImageURL) {
        mLargeImageURL = largeImageURL;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mSmallImageURL='" + mSmallImageURL + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mLargeImageURL='" + mLargeImageURL + '\'' +
                '}';
    }
}

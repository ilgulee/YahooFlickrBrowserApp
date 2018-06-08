package ilgulee.com.yahooflickrbrowserapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData implements GetRawAPIData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> mPhotoList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;
    private final OnDataAvailable mCallback;

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData(String baseURL,String language, boolean matchAll, OnDataAvailable callback) {
        Log.d(TAG, "GetFlickrJsonData: called");
        mBaseURL=baseURL;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallback = callback;
    }

    void executeOnSameThread(String searchCriteria) {
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationURI = createURI(searchCriteria, mLanguage, mMatchAll);
        GetRawAPIData getRawAPIData = new GetRawAPIData(this);
        getRawAPIData.execute(destinationURI);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    String createURI(String searchCriteria, String language, boolean matchAll) {
        Log.d(TAG, "createURI: starts");
        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", language)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: Status " + status);

        if (status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");
                    JSONObject media = jsonPhoto.getJSONObject("media");
                    String smallImageURL = media.getString("m");
                    String largeImageURL = smallImageURL.replaceFirst("_m", "_b");
                    Photo photo = new Photo(title, author, authorId, smallImageURL, tags, largeImageURL);

                    mPhotoList.add(photo);

                    Log.d(TAG, "onDownloadComplete: " + photo.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json Data " + e.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (mCallback != null) {
            mCallback.onDataAvailable(mPhotoList, status);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }
}

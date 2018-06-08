package ilgulee.com.yahooflickrbrowserapp;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends LoggingActivity implements GetFlickrJsonData.OnDataAvailable /*GetRawAPIData.OnDownloadComplete*/ {

    private static final String TAG = "MainActivity";

    public MainActivity() {
        setActivityName("MainActivity");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        GetRawAPIData getRawAPIData = new GetRawAPIData(this);
//        getRawAPIData.execute("https://api.flickr.com/services/feeds/photos_public.gne?&format=json&nojsoncallback=1");

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        GetFlickrJsonData getFlickrJsonData=new GetFlickrJsonData("https://api.flickr.com/services/feeds/photos_public.gne","en-us",true,this);
        getFlickrJsonData.executeOnSameThread("nature,bird");
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned:returned");
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onDownloadComplete(String data, DownloadStatus status){
//        if(status==DownloadStatus.OK){
//            Log.d(TAG, "onDownloadComplete: data is "+data);
//        }else{
//            Log.d(TAG, "onDownloadComplete failed with status "+status);
//        }
//    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDataAvailable: data is " + data);
        } else {
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }
    }
}

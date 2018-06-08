package ilgulee.com.yahooflickrbrowserapp;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
}

/**
 * Package private class
 * This class is responsible for downloading json raw data.
 */
class GetRawAPIData extends AsyncTask<String,Void,String>{

    private static final String TAG = "GetRawAPIData";
    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    interface OnDownloadComplete{
        void onDownloadComplete(String data, DownloadStatus status);
    }
    public GetRawAPIData(OnDownloadComplete callback) {
        mDownloadStatus = DownloadStatus.IDLE;
        mCallback=callback;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter = "+s);
        //super.onPostExecute(s);
        if(mCallback!=null){
            mCallback.onDownloadComplete(s,mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String[] strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            mDownloadStatus=DownloadStatus.PROCESSING;
            URL url =new URL(strings[0]);
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int response=connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code: "+ response);

            StringBuilder result=new StringBuilder();
            reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while(null!=(line=reader.readLine())){
                result.append(line).append("\n");
            }
            mDownloadStatus=DownloadStatus.OK;
            Log.d(TAG, "doInBackground: Download is ok");

            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Exception while reading data: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        }finally{
            if(connection!=null){
                connection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    Log.d(TAG, "doInBackground: Error closing buffered stream "+e.getMessage());
                }
            }
        }
        mDownloadStatus=DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

}

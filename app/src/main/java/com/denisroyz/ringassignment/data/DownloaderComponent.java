package com.denisroyz.ringassignment.data;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Heralt on 12.09.2017.
 */

public class DownloaderComponent {
    private static final String TAG = "DownloaderComponent";
    private static final String DOWNLOAD_DIR_NAME = "ring_assignment";
    private DownloadManager downloadManager;
    private Context context;

    public DownloaderComponent(DownloadManager downloadManager, Context context){
        this.downloadManager = downloadManager;
        this.context = context;
    }

    public void download(String fileName, String downloadUrlOfImage){
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + DOWNLOAD_DIR_NAME + "/");

        if (!direct.exists()) {
            direct.mkdir();
            Log.d(TAG, "dir created for first time");
        }

        String fileNameToUse = fileName.replace("%20", "_")+".jpg";
        Uri downloadUri = Uri.parse(downloadUrlOfImage);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(fileNameToUse)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator + DOWNLOAD_DIR_NAME + File.separator + fileNameToUse);

        downloadManager.enqueue(request);
    }
    public void registerDownloaderComponentListener(DownloaderComponentListener listener){

        listeners.add(listener);
        if (!listening){
            context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            listening = true;
        }

    }

    public void unRegisterDownloaderComponentListener(DownloaderComponentListener listener){
        listeners.remove(listener);
        if (listeners.size()==0){
            context.unregisterReceiver(onDownloadComplete);
            listening = false;
        }
    }

    private boolean listening = false;
    private Set<DownloaderComponentListener> listeners = new HashSet<>();

    BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            notifyListeners(intent);
        }
    };

    private void notifyListeners(Intent intent){
        Bundle extras = intent.getExtras();
        DownloadManager.Query q = new DownloadManager.Query();
        q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
        Cursor c = downloadManager.query(q);
        String title = null;
        String uri = null;
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                // process download
                uri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                // get other required data by changing the constant passed to getColumnIndex
            }
        }
        for (DownloaderComponentListener listener: listeners){
            listener.onDownloadComplete(title, uri);
        }

    }

}

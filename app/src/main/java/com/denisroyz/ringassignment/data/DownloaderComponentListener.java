package com.denisroyz.ringassignment.data;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Heralt on 12.09.2017.
 */

public interface DownloaderComponentListener {

    void onDownloadComplete(String title, String uri, String type);
}

package com.denisroyz.ringassignment.ui.redditBrowser;

/**
 * Created by Heralt on 12.09.2017.
 */

public interface RedditActivityContract {

    void showInGallery(String url, String type);

    boolean haveWriteExternalStoragePermission();

    void requestPermissionWriteExternalStorage();
}

package com.denisroyz.ringassignment.ui.redditBrowser;

import com.denisroyz.ringassignment.model.Child;

import java.util.List;

/**
 * Created by Heralt on 10.09.2017.
 */

public interface RedditBrowserViewContract {

    void showLoadingState();

    void showNoInternetState();

    void showNoContentState();

    void showContentState();

    void setPresenter(RedditBrowserPresenterContract redditBrowserPresenter);

    void displayRedditContent(List<Child> items);

    void stopPullToRefresh();

    void setLoadingEnabled(boolean enabled);

    void displayMoreRedditContentToTheBottom(List<Child> items);

    void notifyCanNotLoadFullSizeImage();

    void notifyDownloadComplete(String title, String uri, String type);

    void requestWriteExternalStoragePermission();
}

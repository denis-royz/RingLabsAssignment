package com.denisroyz.ringassignment.ui.redditBrowser;

import com.denisroyz.ringassignment.model.Child;

/**
 * Created by Heralt on 10.09.2017.
 */

public interface RedditBrowserPresenterContract {

    void subscribe();
    void unSubscribe();

    void setView(RedditBrowserViewContract redditBrowserView);

    void setRedditActivityContract(RedditActivityContract redditActivityContract);

    void loadInitialContent();

    void loadItemsToTail();

    void showInGallery(String uri);

    void downloadFullSize(Child child);

    void notifyDownloadComplete();
}

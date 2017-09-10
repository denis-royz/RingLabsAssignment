package com.denisroyz.ringassignment.ui.redditBrowser;

/**
 * Created by Heralt on 10.09.2017.
 */

public interface RedditBrowserPresenterContract {

    void subscribe();
    void unSubscribe();

    void setView(RedditBrowserViewContract redditBrowserView);

    void loadInitialContent();

    void loadItemsToTail();
}

package com.denisroyz.ringassignment.ui.redditDetails;

import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserPresenterContract;

/**
 * Created by Heralt on 12.09.2017.
 */

public interface RedditDetailsViewContract {
    void showLoadingState();

    void showNoInternetState();

    void showNoContentState();

    void showContentState();

    void setPresenter(RedditDetailsPresenterContract presenter);

    void displayImageFromUrl(String downloadUrlOfImage);
}

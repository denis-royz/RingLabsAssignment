package com.denisroyz.ringassignment.ui.redditDetails;

/**
 * Created by Heralt on 12.09.2017.
 */

public interface RedditDetailsPresenterContract {
    void setView(RedditDetailsViewContract redditDetailsView);

    void setContent(String title, String url);
}

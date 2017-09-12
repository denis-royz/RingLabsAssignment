package com.denisroyz.ringassignment.di;

import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserActivity;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserPresenter;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserView;
import com.denisroyz.ringassignment.ui.redditDetails.RedditDetailsActivity;
import com.denisroyz.ringassignment.ui.redditDetails.RedditDetailsPresenter;
import com.denisroyz.ringassignment.ui.redditDetails.RedditDetailsView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Heralt on 10.09.2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(RedditBrowserPresenter redditBrowserPresenter);
    void inject(RedditDetailsPresenter redditDetailsPresenter);

    void inject(RedditDetailsView redditDetailsView);
    void inject(RedditBrowserView redditBrowserView);

    void inject(RedditBrowserActivity redditBrowserActivity);

    void inject(RedditDomain redditDomain);
    void inject(RedditDetailsActivity redditDetailsActivity);
}

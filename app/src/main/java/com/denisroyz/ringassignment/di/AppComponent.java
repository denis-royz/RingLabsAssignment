package com.denisroyz.ringassignment.di;

import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserActivity;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Heralt on 10.09.2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(RedditBrowserView redditBrowserView);
    void inject(RedditBrowserActivity redditBrowserActivity);
    void inject(RedditDomain redditDomain);
}

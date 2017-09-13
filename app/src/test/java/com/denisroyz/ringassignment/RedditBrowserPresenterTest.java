package com.denisroyz.ringassignment;

import com.denisroyz.ringassignment.data.DownloaderComponent;
import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserPresenter;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RedditBrowserPresenterTest {

    @Before
    public void setup(){
        initMocks(this);
    }

    @Mock
    DownloaderComponent downloaderComponent;
    @Mock
    RedditDomain redditDomain;
    @Mock
    RedditBrowserView redditBrowserView;

    @Test
    public void verifyLoadInitialContentCallsLoadPosts() throws InterruptedException {
        RedditBrowserPresenter redditBrowserPresenter = new RedditBrowserPresenter(downloaderComponent, redditDomain);
        redditBrowserPresenter.setView(redditBrowserView);
        redditBrowserPresenter.loadInitialContent();
        verify(redditDomain).loadPosts(anyInt(),isNull(),isNull(),any(),any());
    }


    @Test
    public void verifySubscribeUnSubscribeContextDependedComponents() throws InterruptedException {
        RedditBrowserPresenter redditBrowserPresenter = new RedditBrowserPresenter(downloaderComponent, redditDomain);
        redditBrowserPresenter.setView(redditBrowserView);
        redditBrowserPresenter.subscribe();
        verify(downloaderComponent).registerDownloaderComponentListener(any());
        redditBrowserPresenter.unSubscribe();
        verify(downloaderComponent).unRegisterDownloaderComponentListener(any());
    }
}
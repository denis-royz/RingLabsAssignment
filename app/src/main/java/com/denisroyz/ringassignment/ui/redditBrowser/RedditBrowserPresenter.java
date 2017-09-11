package com.denisroyz.ringassignment.ui.redditBrowser;

import android.util.Log;

import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.di.AppComponent;
import com.denisroyz.ringassignment.model.Child;
import com.denisroyz.ringassignment.model.Data;

import java.util.List;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditBrowserPresenter implements RedditBrowserPresenterContract{

    private static final String TAG = "RedditBrowserPresenter";

    private static final int POSTS_IN_PAGE          = 10;
    private static final int MAX_POSTS_TO_DISPLAY   = 50;

    private RedditBrowserViewContract view;

    private RedditDomain redditDomain;

    public RedditBrowserPresenter(AppComponent appComponent){
        redditDomain = new RedditDomain(appComponent);
    }

    private String after;
    private int loadedCount;
    private boolean initialized = false;
    private boolean loadMoreAfterInitialization = false;

    public void loadInitialContent() {
        Log.i(TAG, "loadInitialContent");
        loadMoreAfterInitialization = initialized;
        initialized = false;
        loadedCount = 0;
        view.setLoadingEnabled(false);
        redditDomain.loadPosts(POSTS_IN_PAGE, null, null,
                response -> {
                    Data data = response.getData();
                    if (data!=null&&data.getChildren()!=null&&data.getChildren().size()>0){
                        List<Child> items = data.getChildren();
                        after = data.getAfter();
                        loadedCount=items.size();
                        view.displayRedditContent(items);
                        view.showContentState();
                        view.setLoadingEnabled(true);
                        view.stopPullToRefresh();
                        initialized = true;
                        if (loadMoreAfterInitialization){
                            loadItemsToTail();
                        }
                    } else {
                        view.showNoContentState();
                    }
                },
                () -> view.showNoInternetState()
        );
    }



    @Override
    public void loadItemsToTail() {
        Log.i(TAG, "loadItemsToTail");
        if (!initialized){
            Log.i(TAG, "loadItemsToTail.Aborted. Reason: Not initialized. " +
                    "Set state LoadMoreAfterInitialization");
            loadMoreAfterInitialization = true;
            return;
        }
        int postsToLoad = Math.min(POSTS_IN_PAGE, MAX_POSTS_TO_DISPLAY - loadedCount);
        if (postsToLoad==0){
            Log.i(TAG, "loadItemsToTail.Aborted.Max posts to display count already reached");
            return;
        }
        Log.i(TAG, "loadItemsToTail.Started");
        redditDomain.loadPosts(postsToLoad, null, after,
                response -> {
                    Data data = response.getData();
                    if (data!=null&&data.getChildren()!=null&&data.getChildren().size()>0){
                        Log.i(TAG, "loadItemsToTail.Success");
                        List<Child> items = data.getChildren();
                        loadedCount+=items.size();
                        after = data.getAfter();
                        view.displayMoreRedditContentToTheBottom(items);
                        view.stopPullToRefresh();
                        if (loadedCount>=MAX_POSTS_TO_DISPLAY){
                            Log.i(TAG, "loadItemsToTail.Max posts to display count  reached");
                            view.setLoadingEnabled(false);
                        }
                    } else {
                        Log.i(TAG, "loadItemsToTail.No result");
                        view.stopPullToRefresh();
                        view.setLoadingEnabled(false);
                    }
                },
                () -> {
                    Log.i(TAG, "loadItemsToTail.No result");
                    view.stopPullToRefresh();
                    view.setLoadingEnabled(false);
                }
        );
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void setView(RedditBrowserViewContract view) {
        this.view = view;

    }
}

package com.denisroyz.ringassignment.ui.redditBrowser;

import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.di.AppComponent;
import com.denisroyz.ringassignment.model.Child;
import com.denisroyz.ringassignment.model.Data;

import java.util.List;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditBrowserPresenter implements RedditBrowserPresenterContract{
    private RedditBrowserViewContract view;

    private RedditDomain redditDomain;

    public RedditBrowserPresenter(AppComponent appComponent){
        redditDomain = new RedditDomain(appComponent);
    }

    private String after;
    private String before;

    public void loadInitialContent() {
        view.showLoadingState();
        redditDomain.loadPosts(10, null, null,
                response -> {
                    Data data = response.getData();
                    if (data!=null&&data.getChildren()!=null&&data.getChildren().size()>0){
                        List<Child> items = data.getChildren();
                        after = data.getAfter();
                        before = data.getBefore();
                        cache(items);
                        view.displayRedditContent(items);
                        view.showContentState();
                    } else {
                        view.showNoContentState();
                    }
                },
                () -> view.showNoInternetState()
        );
    }

    @Override
    public void loadItemsToTail() {
        redditDomain.loadPosts(10, null, after,
                response -> {
                    Data data = response.getData();
                    if (data!=null&&data.getChildren()!=null&&data.getChildren().size()>0){
                        List<Child> items = data.getChildren();
                        after = data.getAfter();
                        cache(items);
                        view.displayMoreRedditContentToTheBottom(items);
                        view.disableLoading();
                    } else {
                        view.disableLoading();
                        view.setOverScrollEnabled(false);
                    }
                },
                () -> {
                    view.disableLoading();
                    view.setOverScrollEnabled(false);
                }
        );
    }

    private void cache(List<Child> itemsToCache){

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

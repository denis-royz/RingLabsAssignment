package com.denisroyz.ringassignment.ui.redditBrowser;

import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.model.Child;
import com.denisroyz.ringassignment.model.Data;
import com.denisroyz.ringassignment.model.RedditResponse;
import com.denisroyz.ringassignment.model.listeners.FailureListener;
import com.denisroyz.ringassignment.model.listeners.SuccessListener;

import java.util.List;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditBrowserPresenter implements RedditBrowserPresenterContract{
    private RedditBrowserViewContract view;

    private RedditDomain redditDomain;

    public RedditBrowserPresenter(){
        redditDomain = new RedditDomain();
    }

    public void loadInitialContent() {
        view.showLoadingState();
        redditDomain.loadPosts(
                response -> {
                    Data data = response.getData();
                    if (data!=null&&data.getChildren()!=null&&data.getChildren().size()>0){
                        List<Child> items = data.getChildren();
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

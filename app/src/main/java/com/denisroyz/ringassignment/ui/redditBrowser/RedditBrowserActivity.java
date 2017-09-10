package com.denisroyz.ringassignment.ui.redditBrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.di.AppComponent;

public class RedditBrowserActivity extends AppCompatActivity {


    private static final String TAG = "RedditBrowserActivity";
    RedditBrowserPresenterContract redditBrowserPresenter;
    RedditBrowserViewContract redditBrowserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit_browser);
        AppComponent appComponent = ((RingAssignmentApplication)getApplication()).getAppComponent();
        appComponent.inject(this);
        redditBrowserView = new RedditBrowserView(this);
        redditBrowserPresenter = new RedditBrowserPresenter(appComponent);
        init();
    }

    private void init(){
        redditBrowserPresenter.setView(redditBrowserView);
        redditBrowserView.setPresenter(redditBrowserPresenter);
        redditBrowserPresenter.loadInitialContent();
    }

    @Override
    protected void onResume(){
        super.onResume();
        redditBrowserPresenter.subscribe();
    }

    @Override
    protected void onPause(){
        super.onPause();
        redditBrowserPresenter.unSubscribe();
    }

}

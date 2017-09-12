package com.denisroyz.ringassignment.ui.redditBrowser;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.di.AppComponent;
import com.denisroyz.ringassignment.ui.redditDetails.RedditDetailsActivity;

public class RedditBrowserActivity extends AppCompatActivity implements RedditActivityContract{

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
        redditBrowserPresenter.setRedditActivityContract(this);
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

    // TODO  fix for api level 24+
    @Override
    public void showInGallery(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}


package com.denisroyz.ringassignment.ui.redditDetails;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.denisroyz.ringassignment.data.DownloaderComponent;
import com.denisroyz.ringassignment.di.AppComponent;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by Heralt on 12.09.2017.
 */

public class RedditDetailsPresenter implements RedditDetailsPresenterContract{


    private RedditDetailsViewContract viewContract;

    private String mTitle;
    private String mUrl;

    @Inject
    protected DownloaderComponent downloaderComponent;

    public RedditDetailsPresenter(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    public void setView(RedditDetailsViewContract redditDetailsView) {
        this.viewContract = redditDetailsView;
    }

    @Override
    public void setContent(String title, String url) {
        mTitle = title;
        mUrl = url;
        viewContract.displayImageFromUrl(url);
        viewContract.showContentState();
        downloaderComponent.download(mTitle+".jpg", url);
    }

}

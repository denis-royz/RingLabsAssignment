package com.denisroyz.ringassignment.ui.redditBrowser;

import android.util.Log;

import com.denisroyz.ringassignment.data.DownloaderComponent;
import com.denisroyz.ringassignment.data.DownloaderComponentListener;
import com.denisroyz.ringassignment.data.RedditDomain;
import com.denisroyz.ringassignment.di.AppComponent;
import com.denisroyz.ringassignment.model.Child;
import com.denisroyz.ringassignment.model.Data;
import com.denisroyz.ringassignment.model.Image;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditBrowserPresenter implements RedditBrowserPresenterContract, DownloaderComponentListener {

    private static final String TAG = "RedditBrowserPresenter";

    private static final int POSTS_IN_PAGE          = 10;
    private static final int MAX_POSTS_TO_DISPLAY   = 50;

    private RedditBrowserViewContract view;
    private RedditActivityContract redditActivityContract;
    private RedditDomain redditDomain;

    private boolean stateAttached = false;

    public RedditBrowserPresenter(AppComponent appComponent){
        appComponent.inject(this);
        redditDomain = new RedditDomain(appComponent);
    }

    private String after;
    private int loadedCount;
    private boolean initialized = false;
    private boolean loadMoreAfterInitialization = false;

    @Inject
    protected DownloaderComponent downloaderComponent;

    public void loadInitialContent() {
        Log.i(TAG, "loadInitialContent");
        loadMoreAfterInitialization = initialized;
        initialized = false;
        loadedCount = 0;
        view.setLoadingEnabled(false);
        redditDomain.loadPosts(POSTS_IN_PAGE, null, null,
                response -> {
                    if (!isAttached()){
                        Log.i(TAG, "loadItemsToTail.IgnoreResult.Reason: State detached");
                        return;
                    }
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
                    if (!isAttached()){
                        Log.i(TAG, "loadItemsToTail.IgnoreResult.Reason: State detached");
                        return;
                    }
                    Data data = response.getData();
                    if (data!=null&&data.getChildren()!=null&&data.getChildren().size()>0){
                        Log.i(TAG, "loadItemsToTail.Success");
                        List<Child> items = data.getChildren();
                        loadedCount+=items.size();
                        this.after = data.getAfter();
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
    public void showInGallery(String uri, String type) {
        if (!isAttached()){
            Log.i(TAG, "showInGallery.Rejected.Reason: State detached");
            return;
        }
        redditActivityContract.showInGallery(uri, type);
    }

    @Override
    public void downloadFullSize(Child child) {
        if (!redditActivityContract.haveWriteExternalStoragePermission()){
            view.requestWriteExternalStoragePermission();
            return;
        }
        List<Image> images = child.getData().getPreview().getImages();
        String url = null;
        if (images.size()>0){
            url = images.get(0).getSource().getUrl();
        }
        if (url!=null){
            downloaderComponent.download(child.getData().getTitle(), url);
        } else {
            view.notifyCanNotLoadFullSizeImage();
        }
    }



    @Override
    public void onDownloadComplete(String title, String uri, String type) {
        view.notifyDownloadComplete(title, uri, type);

    }
    @Override
    public void notifyDownloadComplete() {
    }

    @Override
    public void requestPermissionWriteExternalStorage() {
        redditActivityContract.requestPermissionWriteExternalStorage();
    }

    @Override
    public void onPermissionWriteExternalStorageGranted() {

    }

    private boolean isAttached(){
        return stateAttached;
    }

    @Override
    public void subscribe() {
        this.stateAttached = true;
        downloaderComponent.registerDownloaderComponentListener(this);

    }

    @Override
    public void unSubscribe() {
        this.stateAttached = false;
        downloaderComponent.unRegisterDownloaderComponentListener(this);
    }

    @Override
    public void setView(RedditBrowserViewContract view) {
        this.view = view;

    }

    @Override
    public void setRedditActivityContract(RedditActivityContract redditActivityContract) {
        this.redditActivityContract = redditActivityContract;
    }

}

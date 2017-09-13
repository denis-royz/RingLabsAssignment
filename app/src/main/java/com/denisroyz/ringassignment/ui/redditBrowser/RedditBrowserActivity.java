package com.denisroyz.ringassignment.ui.redditBrowser;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denisroyz.ringassignment.BuildConfig;
import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.data.PermissionManager;
import com.denisroyz.ringassignment.di.AppComponent;

import java.io.File;

import javax.inject.Inject;

public class RedditBrowserActivity extends AppCompatActivity implements RedditActivityContract{

    private static final String TAG = "RedditBrowserActivity";
    RedditBrowserPresenterContract redditBrowserPresenter;
    RedditBrowserViewContract redditBrowserView;

    @Inject
    PermissionManager permissionManager;

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
    public void showInGallery(String url, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        File imageFile = new File(uri.getPath());
        Uri photoURI = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider",
                imageFile);
        intent.setDataAndType(photoURI, type);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    @Override
    public boolean haveWriteExternalStoragePermission() {
        return permissionManager.checkPermissions(this);
    }

    @Override
    public void requestPermissionWriteExternalStorage() {
        permissionManager.requestPermissions(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean gotPermissions = permissionManager.validatePermissionResult(requestCode, grantResults);
        redditBrowserPresenter.onPermissionWriteExternalStorageGranted();
    }
}


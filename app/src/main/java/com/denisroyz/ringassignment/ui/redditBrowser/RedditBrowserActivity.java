package com.denisroyz.ringassignment.ui.redditBrowser;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.denisroyz.ringassignment.BuildConfig;
import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.data.PermissionManager;
import com.denisroyz.ringassignment.di.AppComponent;

import java.io.File;

import javax.inject.Inject;

public class RedditBrowserActivity extends AppCompatActivity implements RedditActivityContract{

    private static final String TAG = "RedditBrowserActivity";
    RedditBrowserViewContract redditBrowserView;

    @Inject
    RedditBrowserPresenterContract redditBrowserPresenter;
    @Inject
    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_reddit_browser);
        AppComponent appComponent = ((RingAssignmentApplication)getApplication()).getAppComponent();
        appComponent.inject(this);
        redditBrowserView = new RedditBrowserView(this);
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
        Log.i(TAG, "onResume");
        redditBrowserPresenter.subscribe();
        redditBrowserPresenter.restoreCache();
        redditBrowserView.restoreStateAfterResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause");
        redditBrowserPresenter.unSubscribe();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "onStart");
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
        if (gotPermissions) redditBrowserPresenter.onPermissionWriteExternalStorageGranted();
    }

    private static final String LIST_STATE_KEY = "LIST_STATE_KEY";

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Log.i(TAG, "onSaveInstanceState");
        state.putParcelable(LIST_STATE_KEY,  redditBrowserView.getInstanceScrollPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.i(TAG, "onRestoreInstanceState");
        if(state != null)
            redditBrowserView.setInstanceScrollPosition(state.getParcelable(LIST_STATE_KEY));
    }
}


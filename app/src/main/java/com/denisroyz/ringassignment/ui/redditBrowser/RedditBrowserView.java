package com.denisroyz.ringassignment.ui.redditBrowser;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.model.Child;
import com.denisroyz.ringassignment.ui.base.EndlessRecyclerViewScrollListener;
import com.denisroyz.ringassignment.ui.redditBrowser.recycler.RedditRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditBrowserView implements RedditBrowserViewContract{

    private static final String TAG = "RedditBrowserView";

    @BindView(R.id.lay_no_result)
    View viewNoResult;
    @BindView(R.id.lay_no_connection)
    View viewNoConnection;
    @BindView(R.id.lay_loading)
    View viewLoadingInProgress;
    @BindView(R.id.lay_reddit_top)
    View viewRedditRecyclerContainer;

    @BindView(R.id.swipe_lay_feed_list)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.reddit_browser_recycler)
    RecyclerView redditTopRecyclerView;

    @Inject
    Picasso picasso;

    private RedditRecyclerAdapter redditTopRecyclerAdapter;
    private LinearLayoutManager layoutManager;

    private RedditBrowserPresenterContract redditBrowserPresenter;
    private EndlessRecyclerViewScrollListener scrollListener;

    public RedditBrowserView(Activity activity){
        injectUsing(activity);
        redditTopRecyclerAdapter = new RedditRecyclerAdapter(picasso);
        layoutManager = new LinearLayoutManager(activity);
        redditTopRecyclerView.setLayoutManager(layoutManager);
        redditTopRecyclerView.setAdapter(redditTopRecyclerAdapter);
        swipeRefreshLayout.setOnRefreshListener(this::onSwipeRefreshLayoutSwiped);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                redditBrowserPresenter.loadItemsToTail();
            }
        };
        redditTopRecyclerView.addOnScrollListener(scrollListener);
        redditTopRecyclerAdapter.setOnItemActionListener(this::onRecyclerItemActionCalled);
    }

    private void onRecyclerItemActionCalled(Child child){
        redditBrowserPresenter.downloadFullSize(child);
    }
    private void injectUsing(Activity activity){
        ButterKnife.bind(this, activity);
        ((RingAssignmentApplication)activity.getApplication()).getAppComponent().inject(this);
    }

    private void onSwipeRefreshLayoutSwiped() {
        redditBrowserPresenter.loadInitialContent();
    }

    public void setPresenter(RedditBrowserPresenterContract redditBrowserPresenter){
        this.redditBrowserPresenter = redditBrowserPresenter;
    }

    @Override
    public void displayRedditContent(List<Child> items) {
        redditTopRecyclerAdapter.bind(items);
    }
    @Override
    public void displayMoreRedditContentToTheBottom(List<Child> items) {
        redditTopRecyclerAdapter.insertToBottom(items);

    }

    @Override
    public void notifyCanNotLoadFullSizeImage() {
        Snackbar
                .make(redditTopRecyclerView, R.string.can_not_load_fullsize_image, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void notifyDownloadComplete(String title, String uri, String type) {
        String message = String.format(redditTopRecyclerView.getContext().getText(R.string.download_complete).toString(), title);
        Snackbar snackBar = Snackbar
                .make(redditTopRecyclerView, message, Snackbar.LENGTH_SHORT);
        snackBar.setAction("Show", v -> redditBrowserPresenter.showInGallery(uri, type));
        snackBar.show();

    }

    @Override
    public void requestWriteExternalStoragePermission() {
        Snackbar snackBar = Snackbar
                .make(redditTopRecyclerView, R.string.request_permission_storage, Snackbar.LENGTH_SHORT);
        snackBar.setAction("Give permission", v -> redditBrowserPresenter.requestPermissionWriteExternalStorage());
        snackBar.show();

    }

    @Override
    public void stopPullToRefresh() {
        Log.i(TAG, "stopPullToRefresh");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLoadingEnabled(boolean enabled) {
        Log.i(TAG, String.format("setLoadingEnabled(%b)", enabled));
        redditTopRecyclerAdapter.setFooterViewEnabled(enabled);
    }



    @Override
    public void showLoadingState() {
        Log.i(TAG, "showLoadingState");
        viewLoadingInProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetState() {
        Log.i(TAG, "showNoInternetState");
        viewNoResult.setVisibility(View.GONE);
        viewNoConnection.setVisibility(View.VISIBLE);
        viewLoadingInProgress.setVisibility(View.GONE);
        viewRedditRecyclerContainer.setVisibility(View.GONE);
    }

    @Override
    public void showNoContentState() {
        Log.i(TAG, "showNoContentState");
        viewNoResult.setVisibility(View.VISIBLE);
        viewNoConnection.setVisibility(View.GONE);
        viewLoadingInProgress.setVisibility(View.GONE);
        viewRedditRecyclerContainer.setVisibility(View.GONE);
    }

    @Override
    public void showContentState() {
        Log.i(TAG, "showContentState");
        viewNoResult.setVisibility(View.GONE);
        viewNoConnection.setVisibility(View.GONE);
        viewLoadingInProgress.setVisibility(View.GONE);
        viewRedditRecyclerContainer.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.no_connection_reconnect_btn)
    public void onReconnectButtonClick(){
        redditBrowserPresenter.loadInitialContent();
    }

    @OnClick(R.id.no_result_reload_btn)
    public void onReloadButtonClick(){
        redditBrowserPresenter.loadInitialContent();
    }
}

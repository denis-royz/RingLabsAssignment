package com.denisroyz.ringassignment.ui.redditBrowser;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    }
    private void injectUsing(Activity activity){
        ButterKnife.bind(this, activity);
        ((RingAssignmentApplication)activity.getApplication()).getAppComponent().inject(this);
    }

    private void onSwipeRefreshLayoutSwiped() {

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
    public void disableLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setOverScrollEnabled(boolean enabled) {
        redditTopRecyclerAdapter.setFooterViewEnabled(enabled);
    }



    @Override
    public void showLoadingState() {
        viewNoResult.setVisibility(View.GONE);
        viewNoConnection.setVisibility(View.GONE);
        viewLoadingInProgress.setVisibility(View.VISIBLE);
        viewRedditRecyclerContainer.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetState() {
        viewNoResult.setVisibility(View.GONE);
        viewNoConnection.setVisibility(View.VISIBLE);
        viewLoadingInProgress.setVisibility(View.GONE);
        viewRedditRecyclerContainer.setVisibility(View.GONE);
    }

    @Override
    public void showNoContentState() {
        viewNoResult.setVisibility(View.VISIBLE);
        viewNoConnection.setVisibility(View.GONE);
        viewLoadingInProgress.setVisibility(View.GONE);
        viewRedditRecyclerContainer.setVisibility(View.GONE);
    }

    @Override
    public void showContentState() {
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

package com.denisroyz.ringassignment.ui.redditBrowser;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.model.Child;
import com.denisroyz.ringassignment.ui.redditBrowser.recycler.RedditRecyclerViewAdapter;

import java.util.List;

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


    private RedditRecyclerViewAdapter redditTopRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private RedditBrowserPresenterContract redditBrowserPresenter;

    public RedditBrowserView(Activity activity){
        ButterKnife.bind(this, activity);
        redditTopRecyclerAdapter = new RedditRecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(activity);
        redditTopRecyclerView.setLayoutManager(layoutManager);
        redditTopRecyclerView.setAdapter(redditTopRecyclerAdapter);
        //redditTopRecyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(this::onSwipeRefreshLayoutSwiped);
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

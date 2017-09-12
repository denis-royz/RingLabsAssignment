package com.denisroyz.ringassignment.ui.redditDetails;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Heralt on 12.09.2017.
 */

public class RedditDetailsView implements RedditDetailsViewContract {
    @BindView(R.id.lay_no_result)
    View viewNoResult;
    @BindView(R.id.lay_no_connection)
    View viewNoConnection;
    @BindView(R.id.lay_loading)
    View viewLoadingInProgress;
    @BindView(R.id.lay_full_size_image)
    ImageView viewFullSizeImage;

    @Inject
    Picasso picasso;
    public RedditDetailsView(Activity activity) {
        injectUsing(activity);
    }
    private void injectUsing(Activity activity){
        ButterKnife.bind(this, activity);
        ((RingAssignmentApplication)activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void showLoadingState() {

    }

    @Override
    public void showNoInternetState() {

    }

    @Override
    public void showNoContentState() {

    }

    @Override
    public void showContentState() {

    }

    @Override
    public void setPresenter(RedditDetailsPresenterContract presenter) {

    }

    @Override
    public void displayImageFromUrl(String downloadUrlOfImage) {
        picasso.load(downloadUrlOfImage).into(viewFullSizeImage);
    }
}

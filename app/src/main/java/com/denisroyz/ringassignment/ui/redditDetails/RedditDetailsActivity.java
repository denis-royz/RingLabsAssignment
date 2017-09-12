package com.denisroyz.ringassignment.ui.redditDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.di.AppComponent;

public class RedditDetailsActivity extends AppCompatActivity {

    public static final String INTENT_KEY_URL = "com.denisroyz.ringassignment.ui.redditDetails.intent.key.url";
    public static final String INTENT_KEY_TITLE = "com.denisroyz.ringassignment.ui.redditDetails.intent.key.title";
    RedditDetailsViewContract redditDetailsView;
    RedditDetailsPresenterContract redditDetailsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit_details);
        AppComponent appComponent = ((RingAssignmentApplication)getApplication()).getAppComponent();
        appComponent.inject(this);
        redditDetailsView = new RedditDetailsView(this);
        redditDetailsPresenter = new RedditDetailsPresenter(appComponent);
        init();
        loadFromIntent();
    }
    private void init(){
        redditDetailsPresenter.setView(redditDetailsView);
        redditDetailsView.setPresenter(redditDetailsPresenter);
    }
    private void loadFromIntent(){
        String url = getIntent().getStringExtra(INTENT_KEY_URL);
        String title = getIntent().getStringExtra(INTENT_KEY_TITLE);
        setTitle(title);
        redditDetailsPresenter.setContent(title, url);
    }
}

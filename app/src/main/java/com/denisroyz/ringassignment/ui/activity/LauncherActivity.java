package com.denisroyz.ringassignment.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.ui.redditBrowser.RedditBrowserActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Intent intent = new Intent(this, RedditBrowserActivity.class);
        startActivity(intent);
        finish();
    }
}

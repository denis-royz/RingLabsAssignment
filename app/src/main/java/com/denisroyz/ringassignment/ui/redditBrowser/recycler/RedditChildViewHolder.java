package com.denisroyz.ringassignment.ui.redditBrowser.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.denisroyz.ringassignment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditChildViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.lirc_title)
    protected TextView titleTextView;

    View root;

    public RedditChildViewHolder(View itemView) {
        super(itemView);
        root = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void setTitleText(String text){
        titleTextView.setText(text);

    }
}

package com.denisroyz.ringassignment.ui.redditBrowser.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.model.Child;

import java.util.List;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "RedditRecyclerViewAdapter";

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Child> items;
    private boolean footerViewEnabled = false;

    public void bind( List<Child> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.footer_item, parent, false);
            return new FooterViewHolder(v);
        } else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_item_reddit_child, parent, false);
            return new RedditChildViewHolder(v);
        }
        return null;
    }

    public Child getItem(int position){
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RedditChildViewHolder){
            onBindViewHolder((RedditChildViewHolder)holder, position);
        }
    }

    public void onBindViewHolder(RedditChildViewHolder holder, int position) {
        Child item = getItem(position);
        holder.setTitleText(item.getData().getTitle());

    }

    @Override
    public int getItemCount() {
        if (items==null||items.size() ==0)
            return 0;
        else
            return items.size () + (footerViewEnabled?1:0);
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == items.size ();
    }


    public void setFooterViewEnabled(boolean footerViewEnabled) {
        this.footerViewEnabled = footerViewEnabled;
    }
}

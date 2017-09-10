package com.denisroyz.ringassignment.ui.redditBrowser.recycler;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.denisroyz.ringassignment.R;
import com.denisroyz.ringassignment.model.Child;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditChildViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.lirc_title)
    protected TextView titleTextView;
    @BindView(R.id.lirc_subbmitted_by)
    protected TextView entryDateAndAuthorTextView;
    @BindView(R.id.lirc_comments_number)
    protected TextView commentsNumberTextView;
    @BindView(R.id.lirc_thumbnail)
    protected ImageView thumbnailImageView;

    private View root;

    public RedditChildViewHolder(View itemView) {
        super(itemView);
        root = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Child item, Picasso picasso) {
        titleTextView.setText(item.getData().getTitle());
        commentsNumberTextView.setText(formatCommentsNumber(item.getData().getNumComments()));
        entryDateAndAuthorTextView.setText(formatEntryDateAndAuthor(item.getData().getCreatedUtc(),item.getData().getAuthor()));
        picasso .load(item.getData().getUrl())
                .centerCrop()
                .fit()
                .into(thumbnailImageView);
    }

    private String formatEntryDateAndAuthor(Long entryDate, String author){
        long dateNow = new Date().getTime();
        CharSequence formattedDate = DateUtils.getRelativeTimeSpanString(entryDate*1000, dateNow,DateUtils.MINUTE_IN_MILLIS);
        return String.format(root.getContext().getString(R.string.entry_date_and_author_pattern), formattedDate, author);
    }


    private String formatCommentsNumber(Integer commentsNumber){
        if (commentsNumber==1) return String.format(root.getContext().getString(R.string.comment_pattern), commentsNumber);
        else return String.format(root.getContext().getString(R.string.comments_pattern), commentsNumber);
    }
}

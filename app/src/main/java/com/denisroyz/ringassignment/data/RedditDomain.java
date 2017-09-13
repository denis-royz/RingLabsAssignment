package com.denisroyz.ringassignment.data;

import com.denisroyz.ringassignment.model.RedditResponse;
import com.denisroyz.ringassignment.model.listeners.FailureListener;
import com.denisroyz.ringassignment.model.listeners.SuccessListener;

/**
 * Created by Heralt on 13.09.2017.
 */

public interface RedditDomain {
    void loadPosts(int number, String first, String last, final SuccessListener<RedditResponse> successListener, final FailureListener failureListener);
}

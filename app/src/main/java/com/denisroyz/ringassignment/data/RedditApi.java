package com.denisroyz.ringassignment.data;

import com.denisroyz.ringassignment.model.RedditResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by denisroyz on 10/17/2016.
 */

public interface RedditApi {
    @GET("/top/.json")
    Call<RedditResponse> getPosts(@Query("limit") int limit, @Query("after") String after);
}

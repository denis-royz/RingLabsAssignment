package com.denisroyz.ringassignment.data;

import android.util.Log;

import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.di.AppComponent;
import com.denisroyz.ringassignment.model.RedditResponse;
import com.denisroyz.ringassignment.model.listeners.FailureListener;
import com.denisroyz.ringassignment.model.listeners.SuccessListener;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RedditDomainImpl implements RedditDomain {

    private final static String TAG = "RedditDomainImpl";

    protected RedditApi redditApi;

    public RedditDomainImpl(RedditApi redditApi){
        this.redditApi = redditApi;
    }

    @Override
    public void loadPosts(int number, String first, String last, final SuccessListener<RedditResponse> successListener, final FailureListener failureListener){
        redditApi.getPosts(number, last).enqueue(new Callback<RedditResponse>() {
            @Override
            public void onResponse(Call<RedditResponse> call, Response<RedditResponse> response) {
                Log.i(TAG, response.message());
                successListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RedditResponse> call, Throwable t) {
                Log.i(TAG, "onFailure", t);
                failureListener.onFailure();
            }
        });
    }


}

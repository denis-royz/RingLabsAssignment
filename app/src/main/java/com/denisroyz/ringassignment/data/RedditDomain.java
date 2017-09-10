package com.denisroyz.ringassignment.data;

import android.util.Log;

import com.denisroyz.ringassignment.model.RedditResponse;
import com.denisroyz.ringassignment.model.listeners.FailureListener;
import com.denisroyz.ringassignment.model.listeners.SuccessListener;

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

public class RedditDomain {

    private final static String TAG = "RedditDomain";

    private RedditApi redditApi;

    public void loadPosts(final SuccessListener<RedditResponse> successListener, final FailureListener failureListener){
        redditApi.getPosts(10, null).enqueue(new Callback<RedditResponse>() {
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

    public RedditDomain(){
        inject();
    }

    private void inject(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(logging);
        OkHttpClient httpClient = httpClientBuilder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        redditApi = retrofit.create(RedditApi.class);
    }
}

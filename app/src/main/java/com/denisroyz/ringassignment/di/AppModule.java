package com.denisroyz.ringassignment.di;

import android.app.DownloadManager;
import android.content.Context;

import com.denisroyz.ringassignment.RingAssignmentApplication;
import com.denisroyz.ringassignment.data.DownloaderComponent;
import com.denisroyz.ringassignment.data.PermissionManager;
import com.denisroyz.ringassignment.data.RedditApi;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Heralt on 10.09.2017.
 */

@Module
public class AppModule {

    RingAssignmentApplication mApplication;


    public AppModule(RingAssignmentApplication application) {
        mApplication = application;
    }


    @Provides
    @Singleton
    Picasso providePicasso(Context context, @Named("noLogs") OkHttpClient client) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
    }


    @Provides
    @Singleton
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    @Named("noLogs")
    OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.build();
    }

    @Provides
    @Singleton
    @Named("withLogs")
    OkHttpClient provideLoggingOkHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    RedditApi provideRedditApi(@Named("withLogs") OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(RedditApi.class);
    }

    @Provides
    @Singleton
    DownloaderComponent downloaderComponent(Context context){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return new DownloaderComponent(downloadManager, context);

    }

    @Provides
    @Singleton
    PermissionManager permissionManager(){
        return new PermissionManager();
    }
}

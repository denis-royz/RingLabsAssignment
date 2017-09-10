package com.denisroyz.ringassignment;

import android.app.Application;

import com.denisroyz.ringassignment.di.AppComponent;
import com.denisroyz.ringassignment.di.AppModule;
import com.denisroyz.ringassignment.di.DaggerAppComponent;

/**
 * Created by Heralt on 10.09.2017.
 */

public class RingAssignmentApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}

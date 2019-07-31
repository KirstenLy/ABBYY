package com.abbyy.task01;

import android.app.Application;

import com.abbyy.task01.di.AppComponent;
import com.abbyy.task01.di.DaggerAppComponent;
import com.abbyy.task01.di.modules.AppModule;
import com.abbyy.task01.di.modules.NetworkModule;

import timber.log.Timber;

public class AbbyyApp extends Application {

    private static AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        injector = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(this)).
                build();
    }

    public static AppComponent getInjector() {
        return injector;
    }
}

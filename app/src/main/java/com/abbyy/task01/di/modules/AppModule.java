package com.abbyy.task01.di.modules;

import android.content.Context;

import com.abbyy.task01.AbbyyApp;
import com.abbyy.task01.Storage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private AbbyyApp appContext;
    private Storage storage;

    public AppModule(AbbyyApp context) {
        appContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }

    @Provides
    @Singleton
    Storage provideStorage(Context appContext) {
        return new Storage(appContext);
    }
}

package com.abbyy.task01.di.modules;

import com.abbyy.task01.Storage;
import com.abbyy.task01.data.network.LingvoApi;
import com.abbyy.task01.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainActivityPresenter provideMainActivityPresenter(LingvoApi api, Storage storage) {
        return new MainActivityPresenter(api,storage);
    }
}

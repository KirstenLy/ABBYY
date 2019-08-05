package com.abbyy.task01.di.modules;

import com.abbyy.task01.Storage;
import com.abbyy.task01.data.network.LingvoApi;
import com.abbyy.task01.di.ViewModelFactory;
import com.example.sdk.core.NetworkHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    ViewModelFactory provideVMFactory(LingvoApi api, Storage storage, NetworkHelper networkHelper){
        return new ViewModelFactory(api,storage,networkHelper);
    }
}

package com.abbyy.task01.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abbyy.task01.Storage;
import com.abbyy.task01.data.network.LingvoApi;
import com.abbyy.task01.view.activity.main.MainActivityViewModel;
import com.example.sdk.core.NetworkHelper;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final LingvoApi api;
    private final Storage storage;
    private final NetworkHelper networkHelper;

    public ViewModelFactory(LingvoApi api, Storage storage, NetworkHelper networkHelper) {
        this.api = api;
        this.storage = storage;
        this.networkHelper = networkHelper;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(api,storage,networkHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
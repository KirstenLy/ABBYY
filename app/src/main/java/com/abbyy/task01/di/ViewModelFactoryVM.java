package com.abbyy.task01.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abbyy.task01.Storage;
import com.abbyy.task01.data.network.LingvoApi;
import com.abbyy.task01.view.activity.main.MainActivity;
import com.abbyy.task01.view.activity.main.MainActivityViewModel;

public class ViewModelFactoryVM implements ViewModelProvider.Factory {

    private final LingvoApi api;
    private final Storage storage;

    public ViewModelFactoryVM(LingvoApi api, Storage storage) {
        this.api = api;
        this.storage = storage;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivity.class)) {
            return (T) new MainActivityViewModel(api,storage);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
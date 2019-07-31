package com.abbyy.task01.view.activity.main;

import androidx.lifecycle.ViewModel;

import com.abbyy.task01.Storage;
import com.abbyy.task01.contract.MainActivityContract;
import com.abbyy.task01.data.network.LingvoApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityViewModel extends ViewModel {

    private String LANG_SOURCE = "1033";
    private String LANG_TARGET = "1049";
    private String DICTIONARY = "LingvoUniversal (En-Ru)";

    private MainActivityContract view;
    private LingvoApi api;
    private Storage storage;

    @Inject
    public MainActivityViewModel(LingvoApi api, Storage storage) {
        this.api = api;
        this.storage = storage;
    }

    public void getBearedToken(String apiKey) {
        api.get(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> storage.setBearerToken(token), Timber::e);

    }

    public void translate(String text) {
        view.setLoadingState(true);
        api.translate(text, DICTIONARY, LANG_SOURCE, LANG_TARGET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result != null) {
                        view.setNothingFoundedState(false);
                        view.onDataReady(result);
                        storage.addWordToHistory(text.trim());
                        view.setLoadingState(false);
                    }
                }, e -> {
                    Timber.e(e);
                    view.setNothingFoundedState(true);
                    view.setLoadingState(false);
                });

    }

}

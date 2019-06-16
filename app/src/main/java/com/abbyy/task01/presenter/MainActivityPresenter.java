package com.abbyy.task01.presenter;

import com.abbyy.task01.Storage;
import com.abbyy.task01.contract.MainActivityContract;
import com.abbyy.task01.network.LingvoApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityPresenter {
    private String LANG_SOURCE = "1033";
    private String LANG_TARGET = "1049";
    private String DICTIONARY = "LingvoUniversal (En-Ru)";

    @Inject
    public MainActivityPresenter(LingvoApi api, Storage storage) {
        this.api = api;
        this.storage = storage;
    }

    private MainActivityContract view;
    private LingvoApi api;
    private Storage storage;

    public void setView(MainActivityContract view) {
        this.view = view;
    }

    public void getBearedToken() {
        api.get("Basic M2YzZTQ0YmMtN2Y0NC00MmRkLWIxM2UtMmRhZDhlMTY3OTc1OmNmMWM4NzI1ZjgxZjQ2NThhZDExOTk3YWI4ZjAzMjQ1")
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

    public void removeView() {
        this.view = null;
    }
}

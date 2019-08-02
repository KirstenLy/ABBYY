package com.abbyy.task01.view.activity.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abbyy.task01.Storage;
import com.abbyy.task01.data.network.LingvoApi;
import com.abbyy.task01.view.model.ArticleModel;
import com.example.sdk.models.AbbyyError;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityViewModel extends ViewModel {

    private String LANG_SOURCE = "1033";
    private String LANG_TARGET = "1049";
    private String DICTIONARY = "LingvoUniversal (En-Ru)";

    private LingvoApi api;
    private Storage storage;

    MutableLiveData<Boolean> loadingStateLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> nothingFoundStateLiveData = new MutableLiveData<>();
    MutableLiveData<ArticleModel> responseDataLiveData = new MutableLiveData<>();

    MutableLiveData<AbbyyError> responseErrorLiveData = new MutableLiveData<>();

    @Inject
    public MainActivityViewModel(LingvoApi api, Storage storage) {
        this.api = api;
        this.storage = storage;
    }

    void getBearedToken(String apiKey) {
        api.get(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> storage.setBearerToken(token), e -> {
                    AbbyyError error = new AbbyyError();
                    error.setMessage(e.getLocalizedMessage());
                    responseErrorLiveData.setValue(error);
//                   Timber.e("MEEEEEEE: 0" + e.getLocalizedMessage() + " 1" + e.getMessage() + " 2" + e.getCause());
                });
    }

    void translate(String text) {
        loadingStateLiveData.setValue(true);
        api.translate(text, DICTIONARY, LANG_SOURCE, LANG_TARGET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result != null) {
                        nothingFoundStateLiveData.setValue(false);
                        loadingStateLiveData.setValue(false);

                        responseDataLiveData.setValue(result);
                        storage.addWordToHistory(text.trim());
                    }
                }, e -> {
                    Timber.e(e);
                    nothingFoundStateLiveData.setValue(true);
                    loadingStateLiveData.setValue(false);
                });

    }
}

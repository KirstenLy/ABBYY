package com.abbyy.task01.contract;

import com.abbyy.task01.view.model.ArticleModel;

public interface MainActivityContract {
    void setLoadingState(boolean state);

    void setNothingFoundedState(boolean state);

    void onDataReady(ArticleModel model);
}

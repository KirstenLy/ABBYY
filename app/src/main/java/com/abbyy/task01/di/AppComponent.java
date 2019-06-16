package com.abbyy.task01.di;

import com.abbyy.task01.di.modules.AppModule;
import com.abbyy.task01.di.modules.NetworkModule;
import com.abbyy.task01.di.modules.PresenterModule;
import com.abbyy.task01.view.HistoryActivity;
import com.abbyy.task01.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class, NetworkModule.class, AppModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(HistoryActivity historyActivity);
}

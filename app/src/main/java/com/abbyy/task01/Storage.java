package com.abbyy.task01;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

public class Storage {

    private String KEY_SHARED_PREFERENCE = "prefs";
    private String KEY_BEARER_TOKEN = "BearerToken";

    private String KEY_HISTORY = "History";
    private String HISTORY_SEPARATOR = ",";

    private SharedPreferences sp;

    @Inject
    public Storage(Context context) {
        this.sp = context.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public String getBearerToken() {
        return sp.getString(KEY_BEARER_TOKEN, null);
    }

    public void setBearerToken(String token) {
        Editor editor = sp.edit();
        editor.putString(KEY_BEARER_TOKEN, token);
        editor.apply();
    }

    public void addWordToHistory(String word) {
        String history = sp.getString(KEY_HISTORY, null);
        if (history == null) {
            history = word;
        } else {
            history = history + HISTORY_SEPARATOR + word;
        }
        Editor editor = sp.edit();
        editor.putString(KEY_HISTORY, history);
        editor.apply();
    }

    public ArrayList<String> getHistory() {
        String history = sp.getString(KEY_HISTORY, null);
        if (history == null) {
            return null;
        } else {
            String[] split = history.split(HISTORY_SEPARATOR);
            return new ArrayList<>(Arrays.asList(split));
        }
    }

    public void clearHistory() {
        Editor editor = sp.edit();
        editor.putString(KEY_HISTORY, null);
        editor.apply();
    }
}
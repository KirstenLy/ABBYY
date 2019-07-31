package com.abbyy.task01.beans;

import com.google.gson.annotations.Expose;

public class Response {
    @Expose
    private ArticleModel[] models;

    public ArticleModel[] getModels() {
        return models;
    }
}

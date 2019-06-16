package com.abbyy.task01.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class ArticleModel {

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("TitleMarkup")
    @Expose
    private List<ArticleNode> titleMarkup;

    @SerializedName("Dictionary")
    @Expose
    private String dictionary;

    @SerializedName("ArticleId")
    @Expose
    private String articleID;

    @SerializedName("Body")
    @Expose
    private List<ArticleNode> body;

    public String getTitle() {
        return title;
    }

    public List<ArticleNode> getTitleMarkup() {
        return titleMarkup;
    }

    public String getDictionary() {
        return dictionary;
    }

    public String getArticleID() {
        return articleID;
    }

    public List<ArticleNode> getBody() {
        return body;
    }
}

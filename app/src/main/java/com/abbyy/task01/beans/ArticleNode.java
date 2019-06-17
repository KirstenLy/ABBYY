package com.abbyy.task01.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleNode {

    @SerializedName("Text")
    @Expose
    private String text;

    @SerializedName("IsOptional")
    @Expose
    private boolean isOptional;

    @SerializedName("Node")
    @Expose
    private String nodeType;

    @SerializedName("Markup")
    @Expose
    private List<ArticleNode> innerNodes;

    @SerializedName("Items")
    @Expose
    private List<ArticleNode> items;

    @SerializedName("Type")
    @Expose
    private int type;

    public String getText() {
        return text;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setInnerNodes(List<ArticleNode> innerNodes) {
        this.innerNodes = innerNodes;
    }

    public List<ArticleNode> getInnerNodes() {
        return innerNodes;
    }

    public List<ArticleNode> getItems() {
        return items;
    }

    public int getType() {
        return type;
    }
}

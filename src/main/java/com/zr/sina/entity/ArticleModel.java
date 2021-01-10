package com.zr.sina.entity;

import java.io.Serializable;
import java.util.List;

public class ArticleModel implements Serializable {

    private String url;
    private String title;
    private List<String> contents;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ArticleModel{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

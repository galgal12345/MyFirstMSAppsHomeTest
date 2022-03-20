package com.exmple.myfirstmsappshometest.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NewsModel {

    private final int newspaper_id;
    private final String title;
    private final String description;
    private final String url;
    private final String urlToImage;
    private final String publishedAt;
    private final String content;

    public NewsModel(int newspaper_id, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.newspaper_id = newspaper_id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public int getNewspaper_id() {
        return newspaper_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        String MyString = publishedAt;
        MyString = MyString.replace("T", " ");
        MyString = MyString.replace("Z", "");
        return MyString;
    }

    public String getContent() {
        return content;
    }


}



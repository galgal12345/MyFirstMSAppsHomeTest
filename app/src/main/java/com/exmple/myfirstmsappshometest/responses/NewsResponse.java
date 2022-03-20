package com.exmple.myfirstmsappshometest.responses;

import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsResponse {

    @SerializedName("articles")
    @Expose
    private NewsModel newsModel;


    public NewsModel getNewsModel() {
        return newsModel;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "newsModel=" + newsModel +
                '}';
    }
}

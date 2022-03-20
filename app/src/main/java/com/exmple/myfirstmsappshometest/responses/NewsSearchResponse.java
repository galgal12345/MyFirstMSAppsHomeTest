package com.exmple.myfirstmsappshometest.responses;

import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsSearchResponse {

    @SerializedName("articles")
    @Expose()
    private final List<NewsModel> newsModels;

    public NewsSearchResponse(List<NewsModel> newsModels) {
        this.newsModels = newsModels;
    }

    public List<NewsModel> getNewsModels() {
        return newsModels;
    }

    @Override
    public String toString() {
        return "NewsSearchResponse{" +
                "newsModels=" + newsModels +
                '}';
    }
}

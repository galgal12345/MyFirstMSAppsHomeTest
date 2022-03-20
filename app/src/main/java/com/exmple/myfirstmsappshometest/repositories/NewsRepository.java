package com.exmple.myfirstmsappshometest.repositories;

import androidx.lifecycle.LiveData;

import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.exmple.myfirstmsappshometest.requests.NewsApiClient;

import java.util.List;

public class NewsRepository {

    private static NewsRepository instance;
    private final NewsApiClient newsApiClient;



    public static NewsRepository getInstance(){
        if (instance == null){ instance = new NewsRepository(); }
        return instance;
    }

    public LiveData<List<NewsModel>> getNews(){
        return newsApiClient.getNews();
    }

    private NewsRepository(){
        newsApiClient = NewsApiClient.getInstance();
    }

    public void searchNewsApi(String query){
        newsApiClient.searchNewsApi(query);
    }
}
